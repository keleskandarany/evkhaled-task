package com.evkhaled.taskapp;

import com.evkhaled.taskapp.controller.PropertyController;
import com.evkhaled.taskapp.dto.PropertyDetailsDTO;
import com.evkhaled.taskapp.dto.PropertySummaryDTO;
import com.evkhaled.taskapp.entity.Property;
import com.evkhaled.taskapp.entity.PropertyView;
import com.evkhaled.taskapp.service.PropertyService;
import com.evkhaled.taskapp.service.PropertySimilarityService;
import com.evkhaled.taskapp.service.PropertyViewService;
import jakarta.el.PropertyNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PropertyController.class)
@Import(PropertyService.class)
public class PropertyControllerTest {
    @MockBean
    private PropertyService propertyService;

    @MockBean
    private PropertyViewService propertyViewService;

    @MockBean
    private PropertySimilarityService propertySimilarityService;

    @Autowired
    private PropertyController propertyController;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetAllProperties() {
        // Prepare test data
        List<PropertySummaryDTO> propertyList = Arrays.asList(
                new PropertySummaryDTO(1L, "Property 1", BigDecimal.valueOf(100000), "image1.jpg"),
                new PropertySummaryDTO(2L, "Property 2", BigDecimal.valueOf(150000), "image2.jpg")
        );

        // Mock the propertyService to return the propertyList
        when(propertyService.getAllPropertiesWithoutDetails()).thenReturn(propertyList);

        // Perform the GET request
        ResponseEntity<List<PropertySummaryDTO>> response = ResponseEntity.ok(propertyController.getAllProperties());

        // Verify the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(propertyList, response.getBody());
    }

    @Test
    public void testItReturnsPropertyDetails() throws Exception {
        Property property = new Property();
        property.setId(1L);
        property.setDetails("Property 1 Details");

        // Define the behavior of the mocked propertyService when a property is not found, since that's not what we need to test now
        given(propertyService.getPropertyDetails(property.getId())).willReturn(property.getDetails());

        // Perform the request and verify the response
        mockMvc.perform(get("/task/details/{propertyId}", property.getId())
                        .param("user", "test1@engelvoelkers.com")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.details").value(property.getDetails()));
    }

    @Test
    public void testGetPropertyDetailsReturns404() throws Exception {
        Long nonExistentPropertyId = 999L;

        given(propertyService.getPropertyDetails(nonExistentPropertyId))
                .willThrow(new PropertyNotFoundException());

        // Perform the request and verify the response
        mockMvc.perform(get("/task/details/{propertyId}", nonExistentPropertyId)
                        .param("user", "test1@engelvoelkers.com")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("Property not found"));
    }
}
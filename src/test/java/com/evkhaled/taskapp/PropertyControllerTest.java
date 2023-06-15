package com.evkhaled.taskapp;

import com.evkhaled.taskapp.controller.PropertyController;
import com.evkhaled.taskapp.dto.PropertySummaryDTO;
import com.evkhaled.taskapp.service.PropertyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class PropertyControllerTest {
    @MockBean
    private PropertyService propertyService;

    @Autowired
    private PropertyController propertyController;

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
}
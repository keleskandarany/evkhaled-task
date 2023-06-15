package com.evkhaled.taskapp;

import com.evkhaled.taskapp.dto.PropertySummaryDTO;
import com.evkhaled.taskapp.entity.Property;
import com.evkhaled.taskapp.repository.PropertyRepository;
import com.evkhaled.taskapp.service.PropertyService;
import jakarta.el.PropertyNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class PropertyServiceTest {

    @Mock
    private PropertyRepository propertyRepository;

    @InjectMocks
    private PropertyService propertyService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        propertyService = new PropertyService(propertyRepository);
    }

    @Test
    public void testItGetsAllPropertiesWithoutDetails() {
        // Prepare test data
        PropertySummaryDTO property1 = new PropertySummaryDTO(1L, "Property 1", BigDecimal.valueOf(100000), "image1.jpg");
        PropertySummaryDTO property2 = new PropertySummaryDTO(2L, "Property 2", BigDecimal.valueOf(200000), "image2.jpg");
        List<PropertySummaryDTO> propertySummaryDTOs = Arrays.asList(property1, property2);

        // Mock the behavior of the property service
        PropertyService propertyServiceMock = mock(PropertyService.class);
        when(propertyServiceMock.getAllPropertiesWithoutDetails()).thenReturn(propertySummaryDTOs);

        // Perform the service method call
        List<PropertySummaryDTO> result = propertyServiceMock.getAllPropertiesWithoutDetails();

        // Verify the result
        assertEquals(propertySummaryDTOs.size(), result.size());
        assertEquals(property1.getId(), result.get(0).getId());
        assertEquals(property1.getName(), result.get(0).getName());
        assertEquals(property1.getPrice(), result.get(0).getPrice());
        assertEquals(property1.getImage(), result.get(0).getImage());
        assertEquals(property2.getId(), result.get(1).getId());
        assertEquals(property2.getName(), result.get(1).getName());
        assertEquals(property2.getPrice(), result.get(1).getPrice());
        assertEquals(property2.getImage(), result.get(1).getImage());
    }

    @Test
    public void testItReturnsPropertyDetailsById() {
        // Prepare test data
        Long propertyId = 1L;
        String propertyDetails = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.";

        // Create a Property object with the given propertyId and propertyDetails
        Property property = new Property();
        property.setId(propertyId);
        property.setDetails(propertyDetails);

        when(propertyRepository.findById(propertyId)).thenReturn(Optional.of(property));

        // Perform the service method
        String result = propertyService.getPropertyDetails(property.getId());

        // Verify the result
        assertNotNull(result);
        assertEquals(propertyDetails, result);
    }

    @Test
    public void testPropertyDetailsThrowsNotFoundException() {
        // Prepare test data
        Long nonExistingPropertyId = 123321567L;

        // Set up the mock repository to return an empty Optional
        when(propertyRepository.findById(nonExistingPropertyId)).thenReturn(Optional.empty());

        // Perform the service method and assert the exception
        assertThrows(PropertyNotFoundException.class, () -> propertyService.getPropertyDetails(nonExistingPropertyId));
    }
}

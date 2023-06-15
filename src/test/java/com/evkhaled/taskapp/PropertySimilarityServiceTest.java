package com.evkhaled.taskapp;

import com.evkhaled.taskapp.entity.PropertyView;
import com.evkhaled.taskapp.service.PropertySimilarityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PropertySimilarityServiceTest {
    @InjectMocks
    private PropertySimilarityService propertySimilarityService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCalculateSimilarityByViews() {
        // Create sample PropertyView lists for p1 and p2
        List<PropertyView> propertyViews1 = createPropertyViewsList(1L, "u1", "u3");
        List<PropertyView> propertyViews2 = createPropertyViewsList(2L, "u1", "u2", "u3");

        double similarity = propertySimilarityService.calculateSimilarityByViews(propertyViews1, propertyViews2);

        // Assert the similarity value based on the mocked behavior, also with rounding
        assertEquals(0.82, Math.round(similarity * 100.0) / 100.0);
    }


    private List<PropertyView> createPropertyViewsList(Long propertyId, String... users) {
        List<PropertyView> propertyViews = new ArrayList<>();
        for (String user : users) {
            propertyViews.add(new PropertyView(propertyId, user, 1));
        }
        return propertyViews;
    }
}
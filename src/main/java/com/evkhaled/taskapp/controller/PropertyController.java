package com.evkhaled.taskapp.controller;


import com.evkhaled.taskapp.dto.ApiResponse;
import com.evkhaled.taskapp.dto.PropertyDetailsDTO;
import com.evkhaled.taskapp.dto.PropertySummaryDTO;
import com.evkhaled.taskapp.entity.PropertyView;
import com.evkhaled.taskapp.service.PropertySimilarityService;
import com.evkhaled.taskapp.service.PropertyViewService;
import jakarta.el.PropertyNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.evkhaled.taskapp.service.PropertyService;

import java.util.*;

@RestController
public class PropertyController extends BaseController {

    private final PropertyService propertyService;
    private final PropertyViewService propertyViewService;
    private final PropertySimilarityService propertySimilarityService;

    public PropertyController(PropertyService propertyService, PropertyViewService propertyViewService, PropertySimilarityService propertySimilarityService) {
        this.propertyService = propertyService;
        this.propertyViewService = propertyViewService;
        this.propertySimilarityService = propertySimilarityService;
    }

    @GetMapping("/properties")
    public List<PropertySummaryDTO> getAllProperties() {
        return propertyService.getAllPropertiesWithoutDetails();
    }

    @GetMapping("/details/{propertyId}")
    public ResponseEntity<?> getPropertyDetails(@PathVariable Long propertyId, @RequestParam(value = "user") String user) {
        String propertyDetails;

        try {
            //Getting the property details
            propertyDetails = propertyService.getPropertyDetails(propertyId);
        } catch (PropertyNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Property not found"));
        }

        //Getting relevant properties sorted
        List<PropertySummaryDTO> relevantProperties = getRelevantPropertiesByPropertyId(propertyId);

        //Creating a Property View
        propertyViewService.incrementPropertyViewCount(propertyId, user);

        PropertyDetailsDTO response = new PropertyDetailsDTO(propertyDetails, relevantProperties);
        return ResponseEntity.ok(response);
    }

    private List<PropertySummaryDTO> getRelevantPropertiesByPropertyId(Long propertyId) {
        // Retrieve property views for the given propertyId
        List<PropertyView> propertyViews = propertyViewService.getPropertyViewsByPropertyId(propertyId);

        // Calculate and sort properties by relevance
        TreeMap<Integer, PropertySummaryDTO> relevantPropertiesMap = new TreeMap<>(Comparator.reverseOrder());
        List<PropertySummaryDTO> allProperties = propertyService.getAllPropertiesWithoutDetails();

        for (PropertySummaryDTO otherProperty : allProperties) {
            if (otherProperty.getId().equals(propertyId)) { //Can't compare property to itself & no point returning it
                continue;
            }
            List<PropertyView> otherPropertyViews = propertyViewService.getPropertyViewsByPropertyId(otherProperty.getId());
            double similarity = propertySimilarityService.calculateSimilarityByViews(propertyViews, otherPropertyViews);
            // Multiply similarity by 1000,000 and convert to integer for easier sorting
            int scaledSimilarityAsKey = (int) (similarity * 1000000);
            relevantPropertiesMap.put(scaledSimilarityAsKey, otherProperty);

        }

        return new ArrayList<>(relevantPropertiesMap.values());
    }
}

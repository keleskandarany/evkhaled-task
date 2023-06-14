package com.evkhaled.taskapp.dto;

import java.util.List;

public class PropertyDetailsDTO {
    private String details;
    private List<PropertySummaryDTO> relevantProperties;

    public PropertyDetailsDTO(String details, List<PropertySummaryDTO> relevantProperties) {
        this.details = details;
        this.relevantProperties = relevantProperties;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public List<PropertySummaryDTO> getRelevantProperties() {
        return relevantProperties;
    }

    public void setRelevantProperties(List<PropertySummaryDTO> relevantProperties) {
        this.relevantProperties = relevantProperties;
    }
}

package com.evkhaled.taskapp.service;

import com.evkhaled.taskapp.dto.PropertySummaryDTO;
import com.evkhaled.taskapp.entity.Property;
import com.evkhaled.taskapp.repository.PropertyRepository;
import jakarta.el.PropertyNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class PropertyService {
    private final PropertyRepository propertyRepository;

    public PropertyService(PropertyRepository propertyRepository) {
        this.propertyRepository = propertyRepository;
    }

    public List<PropertySummaryDTO> getAllPropertiesWithoutDetails() {
        return propertyRepository.getAllWithoutDetails();
    }

    public String getPropertyDetails(Long propertyId) {
        Property property = propertyRepository.findById(propertyId).orElseThrow(PropertyNotFoundException::new);
        return property.getDetails();
    }

    public void saveMany(List<Property> properties) {
        propertyRepository.saveAll(properties);
    }

    public void truncate() {
        propertyRepository.truncate();
    }
}

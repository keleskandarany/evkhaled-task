package com.evkhaled.taskapp.service;

import com.evkhaled.taskapp.entity.Property;
import com.evkhaled.taskapp.entity.PropertyView;
import com.evkhaled.taskapp.repository.PropertyViewRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PropertyViewService {
    private final PropertyViewRepository propertyViewRepository;

    public PropertyViewService(PropertyViewRepository propertyViewRepository) {
        this.propertyViewRepository = propertyViewRepository;
    }

    public void incrementPropertyViewCount(Long propertyId, String user) {
        PropertyView propertyView = propertyViewRepository.findByPropertyIdAndUser(propertyId, user);
        if (propertyView == null) {
            propertyView = new PropertyView();
            propertyView.setPropertyId(propertyId);
            propertyView.setUser(user);
            propertyView.setCount(1);
        } else {
            // Increment the viewCount
            Integer count = propertyView.getCount();
            propertyView.setCount(count != null ? count + 1 : 1);
        }

        propertyViewRepository.save(propertyView);
    }

    public List<PropertyView> getPropertyViewsByPropertyId(Long id) {
        return propertyViewRepository.getByPropertyId(id);
    }

    public void saveMany(List<PropertyView> propertyViews) {
        propertyViewRepository.saveAll(propertyViews);
    }

    public void truncate() {
        propertyViewRepository.truncate();
    }
}
package com.evkhaled.taskapp.service;

import com.evkhaled.taskapp.entity.Property;
import com.evkhaled.taskapp.entity.PropertyView;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
public class PropertyViewsDatabaseSeeder {
    private final Resource seedDataResource;
    private final PropertyViewService propertyViewService;

    @Autowired
    public PropertyViewsDatabaseSeeder(PropertyViewService propertyViewService, @Value("classpath:data/seed-property-views.json") Resource seedDataResource) {
        this.propertyViewService = propertyViewService;
        this.seedDataResource = seedDataResource;
    }

    //Used the @PostConstruct annotation to make sure that this method runs after the service init. with each app build.
    @PostConstruct
    public void seedData() {
        try {
            truncatePropertyViews(); //We delete all entries to ensure a clean state on each app run.
            List<PropertyView> propertyViews = parseSeedDataJson();
            savePropertyViewsInBatch(propertyViews);
        } catch (IOException e) {
            // Handle exception, maybe log it somewhere, left empty for now
        }
    }

    private List<PropertyView> parseSeedDataJson() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        try (InputStream inputStream = seedDataResource.getInputStream()) {
            return objectMapper.readValue(inputStream, new TypeReference<List<PropertyView>>() {});
        }
    }

    private void truncatePropertyViews() {
        propertyViewService.truncate();
    }

    private void savePropertyViewsInBatch(List<PropertyView> propertyViews) {
        propertyViewService.saveMany(propertyViews);
    }
}

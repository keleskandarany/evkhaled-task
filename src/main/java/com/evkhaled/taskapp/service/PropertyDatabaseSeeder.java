package com.evkhaled.taskapp.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.evkhaled.taskapp.entity.Property;
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
public class PropertyDatabaseSeeder {
    private final Resource seedDataResource;
    private final PropertyService propertyService;

    @Autowired
    public PropertyDatabaseSeeder(PropertyService propertyService, @Value("classpath:data/seed-properties.json") Resource seedDataResource) {
        this.propertyService = propertyService;
        this.seedDataResource = seedDataResource;
    }

    //Used the @PostConstruct annotation to make sure that this method runs after the service init. with each app build.
    @PostConstruct
    public void seedData() {
        try {
            truncateProperties(); //We delete all entries to ensure a clean state on each app run.
            List<Property> properties = parseSeedDataJson();
            savePropertiesInBatch(properties);
        } catch (IOException e) {
            // Handle exception, maybe log it somewhere, left empty for now
        }
    }

    private List<Property> parseSeedDataJson() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        try (InputStream inputStream = seedDataResource.getInputStream()) {
            return objectMapper.readValue(inputStream, new TypeReference<List<Property>>() {});
        }
    }

    private void truncateProperties() {
        propertyService.truncate();
    }

    private void savePropertiesInBatch(List<Property> properties) {
        propertyService.saveMany(properties);
    }
}

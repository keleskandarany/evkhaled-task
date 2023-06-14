package com.evkhaled.taskapp.service;

import com.evkhaled.taskapp.entity.PropertyView;
import com.evkhaled.taskapp.utils.VectorUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PropertySimilarityService {
    public double calculateSimilarityByViews(List<PropertyView> propertyViews1, List<PropertyView> propertyViews2) {
        Map<String, Integer> propertyViewsMap1 = createPropertyViewsMap(propertyViews1);
        Map<String, Integer> propertyViewsMap2 = createPropertyViewsMap(propertyViews2);

        return calculateCosineSimilarity(propertyViewsMap1, propertyViewsMap2);
    }

    private Map<String, Integer> createPropertyViewsMap(List<PropertyView> propertyViews) {
        Map<String, Integer> propertyViewsMap = new HashMap<>();
        for (PropertyView view : propertyViews) {
            String user = view.getUser();
            int viewCount = view.getCount();
            propertyViewsMap.put(user, viewCount);
        }
        return propertyViewsMap;
    }

    private double calculateCosineSimilarity(Map<String, Integer> propertyViewsMap1, Map<String, Integer> propertyViewsMap2) {
        return VectorUtils.calculateCosineSimilarity(propertyViewsMap1, propertyViewsMap2);
    }
}

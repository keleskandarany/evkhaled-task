package com.evkhaled.taskapp.utils;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class VectorUtils {
    public static double calculateCosineSimilarity(Map<String, Integer> vector1, Map<String, Integer> vector2) {
        Set<String> allKeys = getCombinedKeys(vector1, vector2);

        double dotProduct = 0.0;
        double normVector1 = 0.0;
        double normVector2 = 0.0;

        for (String key : allKeys) {
            int count1 = vector1.getOrDefault(key, 0);
            int count2 = vector2.getOrDefault(key, 0);

            dotProduct += count1 * count2;
            normVector1 += count1 * count1;
            normVector2 += count2 * count2;
        }

        double similarity = 0.0;
        if (normVector1 != 0.0 && normVector2 != 0.0) {
            similarity = dotProduct / (Math.sqrt(normVector1) * Math.sqrt(normVector2));
        }
        return similarity;
    }

    private static Set<String> getCombinedKeys(Map<String, Integer> vector1, Map<String, Integer> vector2) {
        Set<String> allKeys = new HashSet<>(vector1.keySet());
        allKeys.addAll(vector2.keySet());
        return allKeys;
    }
}

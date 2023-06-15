package com.evkhaled.taskapp;

import com.evkhaled.taskapp.utils.VectorUtils;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class VectorUtilsTest {

    @Test
    public void testCalculateCosineSimilarity() {
        // Create sample vectors
        Map<String, Integer> vector1 = new HashMap<>();
        vector1.put("u1", 1);
        vector1.put("u3", 1);

        Map<String, Integer> vector2 = new HashMap<>();
        vector2.put("u1", 1);
        vector2.put("u2", 1);
        vector2.put("u3", 1);

        // Calculate cosine similarity
        double similarity = VectorUtils.calculateCosineSimilarity(vector1, vector2);

        // Assert the expected similarity value (rounding to 2 decimal places)
        assertEquals(0.82, Math.round(similarity * 100.0) / 100.0);
    }
}
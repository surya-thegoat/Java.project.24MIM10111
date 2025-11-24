package com.healthdiet;

import com.healthdiet.models.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

public class NutritionAnalyzerTest {

    @Test
    public void testAggregateEmpty() {
        NutritionAnalyzer analyzer = new NutritionAnalyzer();
        List<com.healthdiet.models.Meal> meals = new ArrayList<>();
        com.healthdiet.models.NutritionTotals t = analyzer.aggregate(meals);
        assertEquals(0.0, t.getCalories());
        assertEquals(0.0, t.getProtein());
        assertEquals(0.0, t.getIron());
    }
}

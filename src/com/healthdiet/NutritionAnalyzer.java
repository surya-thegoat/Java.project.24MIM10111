package com.healthdiet;

import com.healthdiet.models.*;
import java.util.*;

public class NutritionAnalyzer {

    public NutritionTotals aggregate(List<Meal> meals) {
        double calories = 0, protein = 0, iron = 0;
        for (Meal m : meals) {
            for (MealItem it : m.getItems()) {
                calories += it.getCalories();
                protein += it.getProtein();
                iron += it.getIron();
            }
        }
        return new NutritionTotals(calories, protein, iron);
    }

    public String estimateRisk(NutritionTotals t) {
        // Simple heuristic
        if (t.getProtein() < 40 || t.getIron() < 8) return "HIGH";
        if (t.getProtein() < 60 || t.getIron() < 10) return "MEDIUM";
        return "LOW";
    }
}

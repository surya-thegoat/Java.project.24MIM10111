package com.healthdiet.models;

public class NutritionTotals {
    private double calories;
    private double protein;
    private double iron;

    public NutritionTotals(double calories, double protein, double iron) {
        this.calories = calories; this.protein = protein; this.iron = iron;
    }

    public double getCalories() { return calories; }
    public double getProtein() { return protein; }
    public double getIron() { return iron; }

    public String toString() {
        return String.format("Calories: %.1f | Protein: %.1f g | Iron: %.1f mg", calories, protein, iron);
    }
}

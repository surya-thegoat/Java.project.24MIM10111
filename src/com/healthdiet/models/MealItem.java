package com.healthdiet.models;

public class MealItem {
    private String name;
    private double calories;
    private double protein;
    private double iron;

    public MealItem(String name, double calories, double protein, double iron) {
        this.name = name; this.calories = calories; this.protein = protein; this.iron = iron;
    }

    public String getName() { return name; }
    public double getCalories() { return calories; }
    public double getProtein() { return protein; }
    public double getIron() { return iron; }
}

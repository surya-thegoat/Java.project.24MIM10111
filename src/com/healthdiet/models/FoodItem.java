package com.healthdiet.models;

public class FoodItem {
    private String name;
    private String serving;
    private double calories;
    private double protein;
    private double iron;

    public FoodItem(String name, String serving, double calories, double protein, double iron) {
        this.name = name; this.serving = serving; this.calories = calories; this.protein = protein; this.iron = iron;
    }

    public String toString() {
        return name + " | " + serving + " | cal:" + calories + " | prot:" + protein + "g | iron:" + iron + "mg";
    }
}

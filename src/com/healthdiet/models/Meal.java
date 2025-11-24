package com.healthdiet.models;

import java.util.*;

public class Meal {
    private String date;
    private List<MealItem> items;

    public Meal(String date, List<MealItem> items) {
        this.date = date;
        this.items = items;
    }

    public String getDate() { return date; }
    public List<MealItem> getItems() { return items; }
}

package com.healthdiet;

import com.healthdiet.models.*;
import java.util.*;

public class MealService {
    private Database db;

    public MealService(Database db) {
        this.db = db;
    }

    public void addMeal(String uid, Meal meal) {
        db.addMeal(uid, meal);
    }

    public List<Meal> getMealsForDate(String uid, String date) {
        return db.getMeals(uid, date);
    }
}

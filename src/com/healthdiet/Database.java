package com.healthdiet;

import com.healthdiet.models.*;
import java.nio.file.*;
import java.io.*;
import java.util.*;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

public class Database {
    private String path;
    private Gson gson;
    private Store store;

    public Database(String path) {
        this.path = path;
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        load();
    }

    private void load() {
        try {
            Path p = Paths.get(path);
            if (!Files.exists(p)) {
                Files.createDirectories(p.getParent());
                this.store = new Store();
                save();
                return;
            }
            String txt = new String(Files.readAllBytes(p), "UTF-8");
            if (txt.trim().isEmpty()) {
                this.store = new Store();
                save();
                return;
            }
            this.store = gson.fromJson(txt, Store.class);
            if (this.store == null) this.store = new Store();
        } catch (Exception ex) {
            ex.printStackTrace();
            this.store = new Store();
        }
    }

    private void save() {
        try {
            String out = gson.toJson(this.store);
            Files.write(Paths.get(path), out.getBytes("UTF-8"), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public List<FoodItem> getFoodItems() {
        return store.foodItems;
    }

    public void setFoodItems(List<FoodItem> items) {
        store.foodItems = items;
        save();
    }

    public void addMeal(String uid, Meal meal) {
        Map<String, List<Meal>> userMeals = store.meals.computeIfAbsent(uid, k -> new HashMap<>());
        List<Meal> arr = userMeals.computeIfAbsent(meal.getDate(), k -> new ArrayList<>());
        arr.add(meal);
        save();
    }

    public List<Meal> getMeals(String uid, String date) {
        Map<String, List<Meal>> userMeals = store.meals.get(uid);
        if (userMeals == null) return new ArrayList<>();
        List<Meal> arr = userMeals.get(date);
        if (arr == null) return new ArrayList<>();
        return arr;
    }

    public void seedSampleData() {
        List<FoodItem> items = new ArrayList<>();
        items.add(new FoodItem("Cooked Rice (1 cup)", "1 cup", 200, 4, 0.8));
        items.add(new FoodItem("Lentils (1 cup cooked)", "1 cup", 230, 18, 6.6));
        items.add(new FoodItem("Milk (250 ml)", "250 ml", 150, 8, 0));
        items.add(new FoodItem("Spinach (1 cup)", "1 cup", 40, 2.9, 6.4));
        this.store.foodItems = items;
        save();
    }

    // internal store structure
    static class Store {
        List<FoodItem> foodItems = new ArrayList<>();
        Map<String, Map<String, List<Meal>>> meals = new HashMap<>();
    }
}

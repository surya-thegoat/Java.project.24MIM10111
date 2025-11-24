package com.healthdiet.server;

import org.springframework.web.bind.annotation.*;
import com.google.gson.*;
import java.nio.file.*;
import java.util.*;

@RestController
@RequestMapping("/api")
public class ApiController {

    private static final Path DB_PATH = Paths.get("server-data/db.json");
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private synchronized Map<String,Object> readDb() {
        try {
            if (!Files.exists(DB_PATH)) {
                Files.createDirectories(DB_PATH.getParent());
                Map<String,Object> init = new HashMap<>();
                init.put("foodItems", new ArrayList<>());
                init.put("meals", new HashMap<>());
                Files.write(DB_PATH, gson.toJson(init).getBytes("UTF-8"));
                return init;
            }
            String txt = Files.readString(DB_PATH);
            return gson.fromJson(txt, Map.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private synchronized void writeDb(Map<String,Object> m) {
        try {
            Files.write(DB_PATH, gson.toJson(m).getBytes("UTF-8"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/food")
    public Object listFood() {
        Map<String,Object> db = readDb();
        return db.getOrDefault("foodItems", Collections.emptyList());
    }

    @PostMapping("/seed-food")
    public Object seed(@RequestBody Map<String,Object> body) {
        Map<String,Object> db = readDb();
        db.put("foodItems", body.getOrDefault("items", new ArrayList<>()));
        writeDb(db);
        return Map.of("ok", true);
    }

    @PostMapping("/users/{uid}/meals")
    public Object addMeal(@PathVariable String uid, @RequestBody Map<String,Object> body) {
        Map<String,Object> db = readDb();
        Map<String,Object> meals = (Map<String,Object>)db.getOrDefault("meals", new HashMap<>());
        Map<String,Object> userMeals = (Map<String,Object>)meals.getOrDefault(uid, new HashMap<>());
        String date = (String)body.get("date");
        List<Object> arr = (List<Object>)userMeals.getOrDefault(date, new ArrayList<>());
        arr.add(body.get("items"));
        userMeals.put(date, arr);
        meals.put(uid, userMeals);
        db.put("meals", meals);
        writeDb(db);
        return Map.of("ok", true);
    }

    @GetMapping("/users/{uid}/meals/{date}/summary")
    public Object summary(@PathVariable String uid, @PathVariable String date) {
        Map<String,Object> db = readDb();
        Map<String,Object> meals = (Map<String,Object>)db.getOrDefault("meals", new HashMap<>());
        Map<String,Object> userMeals = (Map<String,Object>)meals.getOrDefault(uid, new HashMap<>());
        List<Object> dayMeals = (List<Object>)userMeals.getOrDefault(date, new ArrayList<>());
        double calories=0, protein=0, iron=0;
        for (Object m : dayMeals) {
            try {
                List<Map<String,Object>> items = (List<Map<String,Object>>)m;
                for (Map<String,Object> it : items) {
                    calories += ((Number)it.getOrDefault("calories",0)).doubleValue();
                    protein += ((Number)it.getOrDefault("protein_g",0)).doubleValue();
                    iron += ((Number)it.getOrDefault("iron_mg",0)).doubleValue();
                }
            } catch (Exception e) {}
        }
        String risk = (protein < 40 || iron < 8) ? "HIGH" : (protein < 60 || iron < 10) ? "MEDIUM" : "LOW";
        return Map.of("totals", Map.of("calories", calories, "protein", protein, "iron", iron), "risk", risk);
    }
}

package com.healthdiet;

import com.healthdiet.models.*;
import java.util.*;
import java.time.LocalDate;

public class Main {
    public static final String STUDENT_NAME = "GAURAV BHARDWAJ";
    public static final String REG_NO = "24BSA10187";

    public static void main(String[] args) {
        System.out.println("Health & Diet Analyzer (Java Console) - " + STUDENT_NAME + " - " + REG_NO);
        Database db = new Database("data/db.json");
        MealService mealService = new MealService(db);
        NutritionAnalyzer analyzer = new NutritionAnalyzer();

        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. List food items");
            System.out.println("2. Add meal entry (today)");
            System.out.println("3. Show today's summary");
            System.out.println("4. Seed sample data");
            System.out.println("0. Exit");
            System.out.print("Choose: ");
            String ch = sc.nextLine().trim();
            try {
                int c = Integer.parseInt(ch);
                switch (c) {
                    case 1:
                        List<FoodItem> foods = db.getFoodItems();
                        for (int i=0;i<foods.size();i++) {
                            FoodItem f = foods.get(i);
                            System.out.println((i+1)+". " + f);
                        }
                        break;
                    case 2:
                        System.out.print("Food name: ");
                        String name = sc.nextLine().trim();
                        System.out.print("Calories: ");
                        double cal = Double.parseDouble(sc.nextLine().trim());
                        System.out.print("Protein (g): ");
                        double prot = Double.parseDouble(sc.nextLine().trim());
                        System.out.print("Iron (mg): ");
                        double iron = Double.parseDouble(sc.nextLine().trim());
                        Meal m = new Meal(LocalDate.now().toString(), Arrays.asList(
                            new MealItem(name, cal, prot, iron)
                        ));
                        mealService.addMeal("default_user", m);
                        System.out.println("Added meal.");
                        break;
                    case 3:
                        String today = LocalDate.now().toString();
                        List<Meal> meals = mealService.getMealsForDate("default_user", today);
                        NutritionTotals totals = analyzer.aggregate(meals);
                        String risk = analyzer.estimateRisk(totals);
                        System.out.println("Date: " + today);
                        System.out.println(totals);
                        System.out.println("Estimated risk: " + risk);
                        break;
                    case 4:
                        db.seedSampleData();
                        System.out.println("Sample data seeded.");
                        break;
                    case 0:
                        System.out.println("Exiting.");
                        sc.close();
                        return;
                    default:
                        System.out.println("Invalid choice.");
                }
            } catch (Exception ex) {
                System.out.println("Error: " + ex.getMessage());
            }
        }
    }
}

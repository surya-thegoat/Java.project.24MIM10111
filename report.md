# Project Report — Health & Diet Analyzer for Hostel Students (Java)

## Introduction
This Java console application allows a student to log meals, compute daily nutrition totals and detect likely deficiencies using heuristic rules.

## Architecture
- `Main` - console interface
- `Database` - JSON file read/write
- `MealService` - meal management (CRUD)
- `NutritionAnalyzer` - aggregation and risk heuristics
- `models` - User, FoodItem, Meal

## How to run
See README.md

## Future enhancements
- Convert to Spring Boot + REST API
- Add JavaFX GUI
- Replace JSON file store with embedded DB (H2)
- Add unit tests (JUnit)

# Smart Pantry Manager — Written Report Template

> Replace all bracketed placeholders with your own details and evidence. Only describe features you have run and tested. The assignment requires a report with screenshots of your actual running app, code explanations, challenges, reflection, and Harvard-style references.

## Cover Page
- App name: Smart Pantry Manager
- Student name: [Your name]
- Student number: [Your student number]
- Module: Mobile App Development 700
- Submission date: [Date]

## Table of Contents
1. Introduction
2. System Design
3. Implementation and Screenshots
4. Key Code Snippets
5. Challenges and Solutions
6. Conclusion and Reflection
7. References

## 1. Introduction
Explain the food-waste problem addressed by the application, its intended users, and the app's goal of recommending recipes using only available pantry ingredients.

## 2. System Design
### 2.1 Screen Flow
Home → Pantry List → Add/Edit Ingredient; Home → Suggested Recipes → Recipe Detail; Home → Settings.

### 2.2 Data Model
Describe the `ingredients` table (`id`, `name`, `quantity`, `unit`, `expiry_date`) and the `recipes` table (`id`, `name`, `ingredients`, `method`). Add your own ER diagram.

### 2.3 Strict-Matching Rule
Explain that a recipe is suggested only when each required ingredient can be found in the pantry. Describe the current prototype's one-unit-per-listed-ingredient assumption and its limitations.

## 3. Implementation and Screenshots
Insert your own screenshots and captions for:
- Home screen
- Empty pantry
- Add ingredient form and validation message
- Pantry list with saved ingredients
- Edit ingredient
- Delete confirmation / result
- Suggested recipes with matches
- No-match message
- Recipe details
- Settings preference

## 4. Key Code Snippets
Include 3–5 short excerpts from your actual code and explain them:
1. SQLite table creation and recipe seeding (`DatabaseHelper`)
2. Ingredient insert/update/delete methods (`DatabaseHelper`)
3. Strict matching (`getSuggestedRecipes`)
4. Form validation (`IngredientFormActivity`)
5. List display and navigation (`PantryActivity` / `SuggestedRecipesActivity`)

## 5. Challenges and Solutions
Write 2–3 genuine challenges you personally encountered and how you resolved them. Do not invent challenges.

## 6. Conclusion and Reflection
Summarise what you learned, limitations you observed during testing, and improvements you would make.

## 7. References
Use Harvard style and include only sources you actually consulted, such as official Android developer documentation. Confirm your institution's exact Harvard style guidance before submitting.

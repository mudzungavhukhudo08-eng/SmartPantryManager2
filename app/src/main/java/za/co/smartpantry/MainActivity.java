package za.co.smartpantry;

import android.os.Bundle;

public class MainActivity extends BaseActivity {
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupScreen("Smart Pantry Manager");
        addText("Reduce food waste by cooking with ingredients you already have.");
        addButton("Manage Pantry", () -> navigate(PantryActivity.class));
        addButton("Find Suggested Recipes", () -> navigate(SuggestedRecipesActivity.class));
        addButton("Settings", () -> navigate(SettingsActivity.class));
        addText("Tip: add ingredients with their available quantity. Recipe suggestions only appear when every required ingredient is present.");
    }
}

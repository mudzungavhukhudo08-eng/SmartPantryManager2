package za.co.smartpantry;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;

public class SuggestedRecipesActivity extends BaseActivity {
    private DatabaseHelper db;
    private List<Recipe> recipes;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new DatabaseHelper(this);
        setupScreen("Suggested Recipes");
        addText("Only recipes for which all required ingredients are in your pantry are shown.");
        ListView list = new ListView(this);
        root.addView(list, new android.widget.LinearLayout.LayoutParams(-1, 0, 1));
        recipes = db.getSuggestedRecipes();
        List<String> names = new ArrayList<>();
        for (Recipe r : recipes) names.add(r.name + "\nIngredients: " + r.ingredientsText);
        if (names.isEmpty()) {
            names.add("No recipes match your pantry yet. Add more ingredients or check their quantities.");
        }
        list.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, names));
        list.setEnabled(!recipes.isEmpty());
        list.setOnItemClickListener((parent, view, position, id) -> {
            if (recipes.isEmpty()) return;
            Intent intent = new Intent(this, RecipeDetailActivity.class);
            intent.putExtra("recipe_id", recipes.get(position).id);
            startActivity(intent);
        });
        addButton("Refresh Suggestions", () -> recreate());
        addNavigation();
    }
}

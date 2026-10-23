package za.co.smartpantry;

import android.os.Bundle;
import android.widget.ScrollView;
import android.widget.TextView;

public class RecipeDetailActivity extends BaseActivity {
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DatabaseHelper db = new DatabaseHelper(this);
        long id = getIntent().getLongExtra("recipe_id", -1);
        Recipe selected = null;
        for (Recipe r : db.getRecipes()) if (r.id == id) { selected = r; break; }
        setupScreen(selected == null ? "Recipe Details" : selected.name);
        if (selected == null) {
            addText("Recipe not found.");
        } else {
            addText("Required ingredients");
            addText(selected.ingredientsText.replace(", ", "\n• "));
            addText("Preparation method");
            addText(selected.method);
        }
        addButton("Back to Suggestions", () -> finish());
        addNavigation();
    }
}

package za.co.smartpantry;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.LinearLayout;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

public class PantryActivity extends BaseActivity {
    private DatabaseHelper db;
    private List<Ingredient> ingredients;
    private ListView listView;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new DatabaseHelper(this);
        setupScreen("My Pantry");
        addButton("+ Add Ingredient", () -> startActivity(new Intent(this, IngredientFormActivity.class)));
        listView = new ListView(this);
        root.addView(listView, new LinearLayout.LayoutParams(-1, 0, 1));
        listView.setOnItemClickListener((parent, view, position, id) -> {
            Ingredient item = ingredients.get(position);
            Intent intent = new Intent(this, IngredientFormActivity.class);
            intent.putExtra("ingredient_id", item.id);
            startActivity(intent);
        });
        listView.setOnItemLongClickListener((parent, view, position, id) -> {
            Ingredient item = ingredients.get(position);
            new androidx.appcompat.app.AlertDialog.Builder(this)
                .setTitle("Delete ingredient?")
                .setMessage("Delete " + item.name + " from your pantry?")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Delete", (d, w) -> {
                    db.deleteIngredient(item.id);
                    loadItems();
                    Toast.makeText(this, "Ingredient deleted", Toast.LENGTH_SHORT).show();
                }).show();
            return true;
        });
        addText("Tap an ingredient to edit it. Long-press an ingredient to delete it.");
        addNavigation();
        loadItems();
    }

    @Override protected void onResume() { super.onResume(); if (db != null && listView != null) loadItems(); }

    private void loadItems() {
        ingredients = db.getIngredients();
        List<String> rows = new ArrayList<>();
        for (Ingredient i : ingredients) {
            String expiry = (i.expiryDate == null || i.expiryDate.isEmpty()) ? "" : " • Expires: " + i.expiryDate;
            rows.add(i.name + " — " + i.quantity + " " + i.unit + expiry);
        }
        if (rows.isEmpty()) rows.add("Your pantry is empty. Add an ingredient to get started.");
        listView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, rows));
        listView.setEnabled(!ingredients.isEmpty());
    }
}

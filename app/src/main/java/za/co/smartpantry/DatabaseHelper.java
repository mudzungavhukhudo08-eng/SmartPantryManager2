package za.co.smartpantry;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "smart_pantry.db";
    private static final int DB_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE ingredients (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL, quantity REAL NOT NULL, unit TEXT NOT NULL, expiry_date TEXT)");
        db.execSQL("CREATE TABLE recipes (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL, ingredients TEXT NOT NULL, method TEXT NOT NULL)");
        seedRecipes(db);
    }

    private void seedRecipes(SQLiteDatabase db) {
        String[][] recipes = {
                {"Tomato Egg Scramble", "egg, tomato, salt", "Beat eggs, chop tomato, cook tomato briefly, add eggs and salt, then stir until cooked."},
                {"Cheese Omelette", "egg, cheese, salt", "Beat eggs with salt. Cook in a pan, add cheese, fold and cook until set."},
                {"Garlic Tomato Pasta", "pasta, tomato, garlic, salt", "Boil pasta. Cook chopped tomato and garlic, season with salt, then mix with pasta."},
                {"Potato Mash", "potato, butter, milk, salt", "Boil potatoes until soft, mash with butter and milk, then season with salt."},
                {"Banana Pancakes", "banana, egg, flour", "Mash banana, mix with egg and flour, then cook small pancakes in a lightly oiled pan."},
                {"Chicken Rice Bowl", "chicken, rice, onion, salt", "Cook rice. Cook chicken with onion and salt until fully cooked, then serve over rice."},
                {"Onion Tomato Relish", "onion, tomato, oil, salt", "Chop onion and tomato. Fry onion in oil, add tomato and salt, and simmer."},
                {"Cheesy Potato Bake", "potato, cheese, milk, salt", "Slice and boil potatoes until nearly tender. Layer with milk and cheese, season, and bake until golden."},
                {"Garlic Rice", "rice, garlic, oil, salt", "Cook rice. Gently fry chopped garlic in oil, stir in rice and salt, and heat through."},
                {"Apple Banana Fruit Bowl", "apple, banana", "Wash and chop apple and banana, then combine in a bowl."},
                {"Flour Flatbread", "flour, water, salt, oil", "Mix flour, water and salt into a dough. Roll flat and cook in a lightly oiled pan."},
                {"Egg Fried Rice", "rice, egg, onion, oil, salt", "Cook egg in oil, add onion and cooked rice, season with salt, and stir-fry."},
                {"Chicken Tomato Stew", "chicken, tomato, onion, salt", "Cook onion, add chicken and brown, then add tomato and salt. Simmer until chicken is fully cooked."},
                {"Cheese Tomato Toast", "bread, cheese, tomato", "Place sliced tomato and cheese on bread and toast until the cheese melts."},
                // Database helper manages SQLite persistence for pantry ingredients
                {"Milk Banana Smoothie", "banana, milk", "Blend banana and milk until smooth. Serve immediately."},
                {"Potato Onion Hash", "potato, onion, oil, salt", "Dice potatoes and onion. Fry in oil until potatoes are tender and browned; season with salt."},
                {"Tomato Cheese Pasta", "pasta, tomato, cheese, salt", "Boil pasta. Warm chopped tomato, stir in cheese until melted, then combine with pasta and salt."},
                {"Apple Pancakes", "apple, egg, flour, milk", "Grate apple and mix with egg, flour and milk. Cook spoonfuls in a lightly oiled pan."},
                {"Chicken Garlic Rice", "chicken, rice, garlic, salt", "Cook rice. Cook chicken with garlic and salt until fully cooked, then serve together."},
                {"Simple Tomato Soup", "tomato, onion, water, salt", "Chop tomato and onion, simmer in water until soft, then mash or blend and season."}
        };
        for (String[] r : recipes) {
            ContentValues cv = new ContentValues();
            cv.put("name", r[0]);
            cv.put("ingredients", r[1]);
            cv.put("method", r[2]);
            db.insert("recipes", null, cv);
        }
    }

    public long addIngredient(String name, double quantity, String unit, String expiry) {
        ContentValues cv = new ContentValues();
        cv.put("name", canonical(name));
        cv.put("quantity", quantity);
        cv.put("unit", unit.trim());
        cv.put("expiry_date", expiry == null ? "" : expiry.trim());
        return getWritableDatabase().insert("ingredients", null, cv);
    }

    public int updateIngredient(long id, String name, double quantity, String unit, String expiry) {
        ContentValues cv = new ContentValues();
        cv.put("name", canonical(name));
        cv.put("quantity", quantity);
        cv.put("unit", unit.trim());
        cv.put("expiry_date", expiry == null ? "" : expiry.trim());
        return getWritableDatabase().update("ingredients", cv, "id=?", new String[]{String.valueOf(id)});
    }

    public int deleteIngredient(long id) {
        return getWritableDatabase().delete("ingredients", "id=?", new String[]{String.valueOf(id)});
    }

    public List<Ingredient> getIngredients() {
        List<Ingredient> list = new ArrayList<>();
        try (Cursor c = getReadableDatabase().rawQuery("SELECT id,name,quantity,unit,expiry_date FROM ingredients ORDER BY name", null)) {
            while (c.moveToNext())
                list.add(new Ingredient(c.getLong(0), c.getString(1), c.getDouble(2), c.getString(3), c.getString(4)));
        }
        return list;
    }

    public Ingredient getIngredient(long id) {
        try (Cursor c = getReadableDatabase().rawQuery("SELECT id,name,quantity,unit,expiry_date FROM ingredients WHERE id=?", new String[]{String.valueOf(id)})) {
            if (c.moveToFirst())
                return new Ingredient(c.getLong(0), c.getString(1), c.getDouble(2), c.getString(3), c.getString(4));
        }
        return null;
    }

    public List<Recipe> getRecipes() {
        List<Recipe> list = new ArrayList<>();
        try (Cursor c = getReadableDatabase().rawQuery("SELECT id,name,ingredients,method FROM recipes ORDER BY name", null)) {
            while (c.moveToNext())
                list.add(new Recipe(c.getLong(0), c.getString(1), c.getString(2), c.getString(3)));
        }
        return list;
    }

    // Strict matching: every listed recipe ingredient must exist in the pantry with enough quantity.
    // Recipe seed data uses a one-unit requirement for each ingredient.
    public List<Recipe> getSuggestedRecipes() {
        List<Recipe> matches = new ArrayList<>();
        List<Ingredient> pantry = getIngredients();
        for (Recipe recipe : getRecipes()) {
            boolean qualifies = true;
            for (String required : recipe.ingredientNames()) {
                Ingredient found = null;
                for (Ingredient item : pantry) {
                    if (sameIngredient(item.name, required)) {
                        found = item;
                        break;
                    }
                }
                if (found == null || found.quantity < 1.0) {
                    qualifies = false;
                    break;
                }
            }
            if (qualifies) matches.add(recipe);
        }
        return matches;
    }

    private String canonical(String s) {
        if (s.endsWith("oes") && s.length() > 4) return s.substring(0, s.length() - 2);
        if (s.endsWith("ies") && !s.endsWith("ss") && s.length() > 3)
            return s.substring(0, s.length() - 2);
        return s;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Example: drop and recreate - change to your table names
        db.execSQL("DROP TABLE IF EXISTS pantry");
        onCreate(db);
    }

    private boolean sameIngredient(String a, String b) {
        return canonical(a).equals(canonical(b));
    }
}

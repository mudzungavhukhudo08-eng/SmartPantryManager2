package za.co.smartpantry;

import android.os.Bundle;
import android.text.InputType;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class IngredientFormActivity extends BaseActivity {
    private DatabaseHelper db;
    private EditText nameInput, quantityInput, unitInput, expiryInput;
    private long ingredientId = -1;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new DatabaseHelper(this);
        ingredientId = getIntent().getLongExtra("ingredient_id", -1);
        setupScreen(ingredientId == -1 ? "Add Ingredient" : "Edit Ingredient");

        nameInput = field("Ingredient name (e.g. tomato)", InputType.TYPE_CLASS_TEXT);
        quantityInput = field("Quantity (number)", InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        unitInput = field("Unit (e.g. pieces, g, ml)", InputType.TYPE_CLASS_TEXT);
        expiryInput = field("Expiry date (optional, YYYY-MM-DD)", InputType.TYPE_CLASS_TEXT);

        if (ingredientId != -1) {
            Ingredient item = db.getIngredient(ingredientId);
            if (item != null) {
                nameInput.setText(item.name);
                quantityInput.setText(String.valueOf(item.quantity));
                unitInput.setText(item.unit);
                expiryInput.setText(item.expiryDate);
            }
        }

        addButton("Save Ingredient", this::saveIngredient);
        addButton("Cancel", this::finish);
    }

    private EditText field(String hint, int inputType) {
        EditText e = new EditText(this);
        e.setHint(hint);
        e.setSingleLine(true);
        e.setInputType(inputType);
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(-1, ViewGroup.LayoutParams.WRAP_CONTENT);
        p.setMargins(0, 5, 0, 5);
        root.addView(e, p);
        return e;
    }

    private void saveIngredient() {
        String name = nameInput.getText().toString().trim();
        String qty = quantityInput.getText().toString().trim();
        String unit = unitInput.getText().toString().trim();
        String expiry = expiryInput.getText().toString().trim();

        if (name.isEmpty() || qty.isEmpty() || unit.isEmpty()) {
            Toast.makeText(this, "Name, quantity and unit are required.", Toast.LENGTH_LONG).show();
            return;
        }
        double quantity;
        try { quantity = Double.parseDouble(qty); }
        catch (NumberFormatException e) {
            Toast.makeText(this, "Enter a valid quantity.", Toast.LENGTH_LONG).show();
            return;
        }
        if (quantity <= 0) {
            Toast.makeText(this, "Quantity must be greater than zero.", Toast.LENGTH_LONG).show();
            return;
        }
        if (ingredientId == -1) db.addIngredient(name, quantity, unit, expiry);
        else db.updateIngredient(ingredientId, name, quantity, unit, expiry);
        Toast.makeText(this, "Ingredient saved.", Toast.LENGTH_SHORT).show();
        finish();
    }
}

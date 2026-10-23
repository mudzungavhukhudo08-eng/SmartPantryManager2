package za.co.smartpantry;

import android.content.Intent;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {
    protected LinearLayout root;

    protected void setupScreen(String title) {
        root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setPadding(24, 28, 24, 24);
        root.setBackgroundColor(0xFFF7F8F5);
        setContentView(root);

        TextView heading = new TextView(this);
        heading.setText(title);
        heading.setTextSize(26);
        heading.setTypeface(null, Typeface.BOLD);
        heading.setTextColor(0xFF1B5E20);
        heading.setPadding(0, 0, 0, 18);
        root.addView(heading);
    }

    protected Button addButton(String label, Runnable action) {
        Button b = new Button(this);
        b.setText(label);
        b.setAllCaps(false);
        b.setOnClickListener(v -> action.run());
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        p.setMargins(0, 6, 0, 6);
        root.addView(b, p);
        return b;
    }

    protected TextView addText(String value) {
        TextView t = new TextView(this);
        t.setText(value);
        t.setTextSize(16);
        t.setTextColor(0xFF263238);
        t.setPadding(0, 8, 0, 8);
        root.addView(t);
        return t;
    }

    protected void navigate(Class<?> activity) {
        startActivity(new Intent(this, activity));
    }

    protected void addNavigation() {
        addButton("🏠 Home", () -> navigate(MainActivity.class));
        addButton("🥕 Pantry", () -> navigate(PantryActivity.class));
        addButton("🍲 Suggested Recipes", () -> navigate(SuggestedRecipesActivity.class));
        addButton("⚙ Settings", () -> navigate(SettingsActivity.class));
    }
}

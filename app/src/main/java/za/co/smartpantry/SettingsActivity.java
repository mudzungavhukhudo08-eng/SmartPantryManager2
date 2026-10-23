package za.co.smartpantry;

import android.os.Bundle;
import android.widget.Switch;
import android.widget.Toast;

public class SettingsActivity extends BaseActivity {
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupScreen("Settings");
        addText("Preferences");
        Switch expirySwitch = new Switch(this);
        expirySwitch.setText("Enable expiring-soon reminder preference");
        expirySwitch.setChecked(getPreferences(MODE_PRIVATE).getBoolean("expiry_alerts", true));
        root.addView(expirySwitch);
        expirySwitch.setOnCheckedChangeListener((button, checked) -> {
            getPreferences(MODE_PRIVATE).edit().putBoolean("expiry_alerts", checked).apply();
            Toast.makeText(this, "Preference saved", Toast.LENGTH_SHORT).show();
        });
        addText("This preference is stored on this device. Expiry alerts are a future enhancement; this starter version stores the preference only.");
        addNavigation();
    }
}

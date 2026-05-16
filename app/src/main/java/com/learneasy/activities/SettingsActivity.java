package com.learneasy.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.learneasy.R;

public class SettingsActivity extends AppCompatActivity {

    private Switch darkModeSwitch;
    private Switch ttsSwitch;
    private Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        darkModeSwitch = findViewById(R.id.dark_mode_switch);
        ttsSwitch = findViewById(R.id.tts_switch);
        backButton = findViewById(R.id.back_button);

        SharedPreferences preferences = getSharedPreferences("app_prefs", MODE_PRIVATE);

        boolean darkModeEnabled = preferences.getBoolean("dark_mode", false);
        boolean ttsEnabled = preferences.getBoolean("tts_enabled", true);

        darkModeSwitch.setChecked(darkModeEnabled);
        ttsSwitch.setChecked(ttsEnabled);

        darkModeSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("dark_mode", isChecked);
            editor.apply();

            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
        });

        ttsSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("tts_enabled", isChecked);
            editor.apply();
        });

        backButton.setOnClickListener(v -> finish());
    }
}

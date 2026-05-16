package com.learneasy.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.learneasy.R;

public class MainActivity extends AppCompatActivity {

    private TextView welcomeText;
    private Button lessonsButton;
    private Button quizButton;
    private Button flashcardsButton;
    private Button progressButton;
    private Button settingsButton;
    private Button logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences preferences = getSharedPreferences("app_prefs", MODE_PRIVATE);
        String username = preferences.getString("username", "Student");

        welcomeText = findViewById(R.id.welcome_text);
        lessonsButton = findViewById(R.id.lessons_button);
        quizButton = findViewById(R.id.quiz_button);
        flashcardsButton = findViewById(R.id.flashcards_button);
        progressButton = findViewById(R.id.progress_button);
        settingsButton = findViewById(R.id.settings_button);
        logoutButton = findViewById(R.id.logout_button);

        welcomeText.setText("Welcome, " + username + "!");

        lessonsButton.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, LessonsActivity.class)));
        quizButton.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, QuizActivity.class)));
        flashcardsButton.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, FlashcardsActivity.class)));
        progressButton.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, ProgressActivity.class)));
        settingsButton.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, SettingsActivity.class)));
        logoutButton.setOnClickListener(v -> handleLogout());
    }

    private void handleLogout() {
        SharedPreferences preferences = getSharedPreferences("app_prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("is_logged_in", false);
        editor.apply();

        startActivity(new Intent(MainActivity.this, LoginActivity.class));
        finish();
    }
}

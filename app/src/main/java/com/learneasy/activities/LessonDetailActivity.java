package com.learneasy.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.learneasy.R;
import com.learneasy.database.DatabaseHelper;
import com.learneasy.models.Lesson;

import java.util.Locale;

public class LessonDetailActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {

    private TextView lessonTitle;
    private TextView lessonContent;
    private ImageView lessonImage;
    private Button readAloudButton;
    private Button backButton;
    private DatabaseHelper databaseHelper;
    private TextToSpeech textToSpeech;
    private Lesson currentLesson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson_detail);

        databaseHelper = new DatabaseHelper(this);
        textToSpeech = new TextToSpeech(this, this);

        lessonTitle = findViewById(R.id.lesson_title);
        lessonContent = findViewById(R.id.lesson_content);
        lessonImage = findViewById(R.id.lesson_image);
        readAloudButton = findViewById(R.id.read_aloud_button);
        backButton = findViewById(R.id.back_button);

        int lessonId = getIntent().getIntExtra("lesson_id", -1);
        if (lessonId != -1) {
            currentLesson = databaseHelper.getLessonById(lessonId);
            if (currentLesson != null) {
                displayLesson();
            }
        }

        readAloudButton.setOnClickListener(v -> readAloud());
        backButton.setOnClickListener(v -> finish());
    }

    private void displayLesson() {
        lessonTitle.setText(currentLesson.getTitle());
        lessonContent.setText(currentLesson.getContent());

        int imageResourceId = getResources().getIdentifier(currentLesson.getImageUrl(), "drawable", getPackageName());
        if (imageResourceId != 0) {
            lessonImage.setImageResource(imageResourceId);
        }
    }

    private void readAloud() {
        SharedPreferences preferences = getSharedPreferences("app_prefs", MODE_PRIVATE);
        boolean ttsEnabled = preferences.getBoolean("tts_enabled", true);

        if (!ttsEnabled) {
            Toast.makeText(this, "Text-to-Speech is disabled", Toast.LENGTH_SHORT).show();
            return;
        }

        if (textToSpeech != null && currentLesson != null) {
            textToSpeech.speak(currentLesson.getTitle() + ". " + currentLesson.getContent(),
                    TextToSpeech.QUEUE_FLUSH, null);
        }
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            textToSpeech.setLanguage(Locale.ENGLISH);
        }
    }

    @Override
    protected void onDestroy() {
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onDestroy();
    }
}

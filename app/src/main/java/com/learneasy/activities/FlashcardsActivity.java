package com.learneasy.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.learneasy.R;
import com.learneasy.database.DatabaseHelper;
import com.learneasy.models.Flashcard;

import java.util.List;
import java.util.Locale;

public class FlashcardsActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {

    private LinearLayout flashcardContainer;
    private TextView flashcardQuestion;
    private TextView flashcardAnswer;
    private ImageButton nextButton;
    private ImageButton previousButton;
    private ImageButton speakButton;
    private Button backButton;
    private Button toggleButton;
    private DatabaseHelper databaseHelper;
    private List<Flashcard> flashcards;
    private int currentIndex = 0;
    private boolean showingQuestion = true;
    private TextToSpeech textToSpeech;
    private String selectedCategory = "Mathematics";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flashcards);

        databaseHelper = new DatabaseHelper(this);
        textToSpeech = new TextToSpeech(this, this);

        flashcardContainer = findViewById(R.id.flashcard_container);
        flashcardQuestion = findViewById(R.id.flashcard_question);
        flashcardAnswer = findViewById(R.id.flashcard_answer);
        nextButton = findViewById(R.id.next_button);
        previousButton = findViewById(R.id.previous_button);
        speakButton = findViewById(R.id.speak_button);
        backButton = findViewById(R.id.back_button);
        toggleButton = findViewById(R.id.toggle_button);

        flashcards = databaseHelper.getFlashcardsByCategory(selectedCategory);

        if (flashcards.isEmpty()) {
            Toast.makeText(this, "No flashcards available", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        displayFlashcard();

        flashcardContainer.setOnClickListener(v -> toggleFlashcard());
        nextButton.setOnClickListener(v -> nextFlashcard());
        previousButton.setOnClickListener(v -> previousFlashcard());
        speakButton.setOnClickListener(v -> speakFlashcard());
        backButton.setOnClickListener(v -> finish());
        toggleButton.setOnClickListener(v -> toggleFlashcard());
    }

    private void displayFlashcard() {
        if (!flashcards.isEmpty()) {
            Flashcard card = flashcards.get(currentIndex);
            flashcardQuestion.setText(card.getQuestion());
            flashcardAnswer.setText(card.getAnswer());
            showingQuestion = true;
            flashcardAnswer.setAlpha(0.3f);
        }
    }

    private void toggleFlashcard() {
        if (showingQuestion) {
            flashcardAnswer.setAlpha(1f);
            showingQuestion = false;
        } else {
            flashcardAnswer.setAlpha(0.3f);
            showingQuestion = true;
        }
    }

    private void nextFlashcard() {
        currentIndex = (currentIndex + 1) % flashcards.size();
        displayFlashcard();
    }

    private void previousFlashcard() {
        currentIndex = (currentIndex - 1 + flashcards.size()) % flashcards.size();
        displayFlashcard();
    }

    private void speakFlashcard() {
        SharedPreferences preferences = getSharedPreferences("app_prefs", MODE_PRIVATE);
        boolean ttsEnabled = preferences.getBoolean("tts_enabled", true);

        if (!ttsEnabled) {
            Toast.makeText(this, "Text-to-Speech is disabled", Toast.LENGTH_SHORT).show();
            return;
        }

        if (textToSpeech != null && !flashcards.isEmpty()) {
            Flashcard card = flashcards.get(currentIndex);
            String text = showingQuestion ? card.getQuestion() : card.getAnswer();
            textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null);
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

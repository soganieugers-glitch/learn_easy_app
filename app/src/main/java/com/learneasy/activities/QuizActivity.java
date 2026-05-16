package com.learneasy.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.learneasy.R;
import com.learneasy.database.DatabaseHelper;
import com.learneasy.models.QuizQuestion;

import java.util.List;
import java.util.Locale;

public class QuizActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {

    private TextView questionText;
    private RadioGroup optionsGroup;
    private RadioButton optionA;
    private RadioButton optionB;
    private RadioButton optionC;
    private RadioButton optionD;
    private Button nextButton;
    private Button backButton;
    private TextView questionCounter;
    private DatabaseHelper databaseHelper;
    private List<QuizQuestion> quizQuestions;
    private int currentQuestionIndex = 0;
    private int score = 0;
    private TextToSpeech textToSpeech;
    private String selectedCategory = "Mathematics";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        databaseHelper = new DatabaseHelper(this);
        textToSpeech = new TextToSpeech(this, this);

        selectedCategory = getIntent().getStringExtra("category");
        if (selectedCategory == null) {
            selectedCategory = "Mathematics";
        }

        questionText = findViewById(R.id.question_text);
        optionsGroup = findViewById(R.id.options_group);
        optionA = findViewById(R.id.option_a);
        optionB = findViewById(R.id.option_b);
        optionC = findViewById(R.id.option_c);
        optionD = findViewById(R.id.option_d);
        nextButton = findViewById(R.id.next_button);
        backButton = findViewById(R.id.back_button);
        questionCounter = findViewById(R.id.question_counter);

        quizQuestions = databaseHelper.getQuizQuestionsByCategory(selectedCategory);

        if (quizQuestions.isEmpty()) {
            Toast.makeText(this, "No questions available", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        displayQuestion();

        nextButton.setOnClickListener(v -> handleNext());
        backButton.setOnClickListener(v -> finish());
    }

    private void displayQuestion() {
        if (currentQuestionIndex < quizQuestions.size()) {
            QuizQuestion question = quizQuestions.get(currentQuestionIndex);
            questionText.setText(question.getQuestion());
            optionA.setText(question.getOptionA());
            optionB.setText(question.getOptionB());
            optionC.setText(question.getOptionC());
            optionD.setText(question.getOptionD());
            optionsGroup.clearCheck();

            questionCounter.setText("Question " + (currentQuestionIndex + 1) + "/" + quizQuestions.size());

            SharedPreferences preferences = getSharedPreferences("app_prefs", MODE_PRIVATE);
            boolean ttsEnabled = preferences.getBoolean("tts_enabled", true);
            if (ttsEnabled && textToSpeech != null) {
                textToSpeech.speak(question.getQuestion(), TextToSpeech.QUEUE_FLUSH, null);
            }
        }
    }

    private void handleNext() {
        if (optionsGroup.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Please select an answer", Toast.LENGTH_SHORT).show();
            return;
        }

        int selectedOptionId = optionsGroup.getCheckedRadioButtonId();
        char selectedOption = getSelectedOption(selectedOptionId);
        QuizQuestion currentQuestion = quizQuestions.get(currentQuestionIndex);

        if (String.valueOf(selectedOption).equals(currentQuestion.getCorrectAnswer())) {
            score++;
        }

        currentQuestionIndex++;

        if (currentQuestionIndex < quizQuestions.size()) {
            displayQuestion();
        } else {
            finishQuiz();
        }
    }

    private char getSelectedOption(int selectedOptionId) {
        if (selectedOptionId == R.id.option_a) return 'A';
        if (selectedOptionId == R.id.option_b) return 'B';
        if (selectedOptionId == R.id.option_c) return 'C';
        if (selectedOptionId == R.id.option_d) return 'D';
        return 'A';
    }

    private void finishQuiz() {
        databaseHelper.insertQuizResult(selectedCategory, score, quizQuestions.size());

        Intent intent = new Intent(QuizActivity.this, QuizResultActivity.class);
        intent.putExtra("score", score);
        intent.putExtra("total", quizQuestions.size());
        intent.putExtra("category", selectedCategory);
        startActivity(intent);
        finish();
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

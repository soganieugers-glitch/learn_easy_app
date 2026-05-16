package com.learneasy.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.learneasy.R;

public class QuizResultActivity extends AppCompatActivity {

    private TextView scoreText;
    private TextView percentageText;
    private TextView resultMessage;
    private Button retakeButton;
    private Button homeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_result);

        scoreText = findViewById(R.id.score_text);
        percentageText = findViewById(R.id.percentage_text);
        resultMessage = findViewById(R.id.result_message);
        retakeButton = findViewById(R.id.retake_button);
        homeButton = findViewById(R.id.home_button);

        int score = getIntent().getIntExtra("score", 0);
        int total = getIntent().getIntExtra("total", 0);
        String category = getIntent().getStringExtra("category");

        int percentage = (total > 0) ? (score * 100) / total : 0;

        scoreText.setText("Your Score: " + score + "/" + total);
        percentageText.setText("Percentage: " + percentage + "%");

        if (percentage >= 80) {
            resultMessage.setText("Excellent! Keep it up!");
        } else if (percentage >= 60) {
            resultMessage.setText("Good effort! Practice more.");
        } else {
            resultMessage.setText("Keep learning and try again!");
        }

        retakeButton.setOnClickListener(v -> {
            Intent intent = new Intent(QuizResultActivity.this, QuizActivity.class);
            intent.putExtra("category", category);
            startActivity(intent);
            finish();
        });

        homeButton.setOnClickListener(v -> {
            startActivity(new Intent(QuizResultActivity.this, MainActivity.class));
            finish();
        });
    }
}

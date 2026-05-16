package com.learneasy.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.learneasy.R;
import com.learneasy.adapters.QuizResultAdapter;
import com.learneasy.database.DatabaseHelper;
import com.learneasy.models.QuizResult;

import java.util.List;

public class ProgressActivity extends AppCompatActivity {

    private RecyclerView resultsRecyclerView;
    private Button backButton;
    private DatabaseHelper databaseHelper;
    private TextView completedQuizzesText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);

        databaseHelper = new DatabaseHelper(this);

        resultsRecyclerView = findViewById(R.id.results_recycler_view);
        backButton = findViewById(R.id.back_button);
        completedQuizzesText = findViewById(R.id.completed_quizzes_text);

        resultsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<QuizResult> results = databaseHelper.getAllQuizResults();
        completedQuizzesText.setText("Total Quizzes Completed: " + results.size());

        QuizResultAdapter adapter = new QuizResultAdapter(results);
        resultsRecyclerView.setAdapter(adapter);

        backButton.setOnClickListener(v -> finish());
    }
}

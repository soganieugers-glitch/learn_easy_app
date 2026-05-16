package com.learneasy.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.learneasy.R;
import com.learneasy.adapters.LessonAdapter;
import com.learneasy.database.DatabaseHelper;
import com.learneasy.models.Lesson;

import java.util.List;

public class LessonsActivity extends AppCompatActivity {

    private RecyclerView lessonsRecyclerView;
    private Button backButton;
    private DatabaseHelper databaseHelper;
    private LessonAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lessons);

        databaseHelper = new DatabaseHelper(this);

        lessonsRecyclerView = findViewById(R.id.lessons_recycler_view);
        backButton = findViewById(R.id.back_button);

        lessonsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<Lesson> lessons = databaseHelper.getAllLessons();
        adapter = new LessonAdapter(lessons, lesson -> openLessonDetail(lesson.getId()));
        lessonsRecyclerView.setAdapter(adapter);

        backButton.setOnClickListener(v -> finish());
    }

    private void openLessonDetail(int lessonId) {
        Intent intent = new Intent(LessonsActivity.this, LessonDetailActivity.class);
        intent.putExtra("lesson_id", lessonId);
        startActivity(intent);
    }
}

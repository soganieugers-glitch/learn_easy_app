package com.learneasy.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.learneasy.models.Flashcard;
import com.learneasy.models.Lesson;
import com.learneasy.models.QuizQuestion;
import com.learneasy.models.QuizResult;
import com.learneasy.models.User;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "learneasy.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_USERS = "users";
    private static final String TABLE_LESSONS = "lessons";
    private static final String TABLE_QUIZ_QUESTIONS = "quiz_questions";
    private static final String TABLE_QUIZ_RESULTS = "quiz_results";
    private static final String TABLE_FLASHCARDS = "flashcards";

    private static final String COLUMN_ID = "id";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_CATEGORY = "category";
    private static final String COLUMN_CONTENT = "content";
    private static final String COLUMN_IMAGE_URL = "image_url";
    private static final String COLUMN_DURATION = "duration";
    private static final String COLUMN_QUESTION = "question";
    private static final String COLUMN_OPTION_A = "option_a";
    private static final String COLUMN_OPTION_B = "option_b";
    private static final String COLUMN_OPTION_C = "option_c";
    private static final String COLUMN_OPTION_D = "option_d";
    private static final String COLUMN_CORRECT_ANSWER = "correct_answer";
    private static final String COLUMN_EXPLANATION = "explanation";
    private static final String COLUMN_SCORE = "score";
    private static final String COLUMN_TOTAL_QUESTIONS = "total_questions";
    private static final String COLUMN_TIMESTAMP = "timestamp";
    private static final String COLUMN_ANSWER = "answer";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createUserTable = "CREATE TABLE " + TABLE_USERS + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USERNAME + " TEXT UNIQUE NOT NULL, " +
                COLUMN_EMAIL + " TEXT UNIQUE NOT NULL, " +
                COLUMN_PASSWORD + " TEXT NOT NULL)";
        db.execSQL(createUserTable);

        String createLessonsTable = "CREATE TABLE " + TABLE_LESSONS + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TITLE + " TEXT NOT NULL, " +
                COLUMN_CATEGORY + " TEXT NOT NULL, " +
                COLUMN_CONTENT + " TEXT NOT NULL, " +
                COLUMN_IMAGE_URL + " TEXT, " +
                COLUMN_DURATION + " INTEGER)";
        db.execSQL(createLessonsTable);

        String createQuizTable = "CREATE TABLE " + TABLE_QUIZ_QUESTIONS + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_QUESTION + " TEXT NOT NULL, " +
                COLUMN_OPTION_A + " TEXT NOT NULL, " +
                COLUMN_OPTION_B + " TEXT NOT NULL, " +
                COLUMN_OPTION_C + " TEXT NOT NULL, " +
                COLUMN_OPTION_D + " TEXT NOT NULL, " +
                COLUMN_CORRECT_ANSWER + " TEXT NOT NULL, " +
                COLUMN_CATEGORY + " TEXT NOT NULL, " +
                COLUMN_EXPLANATION + " TEXT)";
        db.execSQL(createQuizTable);

        String createResultsTable = "CREATE TABLE " + TABLE_QUIZ_RESULTS + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_CATEGORY + " TEXT NOT NULL, " +
                COLUMN_SCORE + " INTEGER NOT NULL, " +
                COLUMN_TOTAL_QUESTIONS + " INTEGER NOT NULL, " +
                COLUMN_TIMESTAMP + " LONG NOT NULL)";
        db.execSQL(createResultsTable);

        String createFlashcardsTable = "CREATE TABLE " + TABLE_FLASHCARDS + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_QUESTION + " TEXT NOT NULL, " +
                COLUMN_ANSWER + " TEXT NOT NULL, " +
                COLUMN_CATEGORY + " TEXT NOT NULL)";
        db.execSQL(createFlashcardsTable);

        insertSampleData(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LESSONS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUIZ_QUESTIONS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUIZ_RESULTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FLASHCARDS);
        onCreate(db);
    }

    private void insertSampleData(SQLiteDatabase db) {
        ContentValues values;

        values = new ContentValues();
        values.put(COLUMN_TITLE, "Introduction to Algebra");
        values.put(COLUMN_CATEGORY, "Mathematics");
        values.put(COLUMN_CONTENT, "Algebra is the branch of mathematics that uses letters and symbols to represent numbers in equations and formulas. Learn about variables, coefficients, and how to solve basic equations.");
        values.put(COLUMN_IMAGE_URL, "ic_math");
        values.put(COLUMN_DURATION, 15);
        db.insert(TABLE_LESSONS, null, values);

        values = new ContentValues();
        values.put(COLUMN_TITLE, "Basic Geometry");
        values.put(COLUMN_CATEGORY, "Mathematics");
        values.put(COLUMN_CONTENT, "Geometry explores shapes, angles, and spatial relationships. Master the properties of triangles, circles, and other polygons with practical applications.");
        values.put(COLUMN_IMAGE_URL, "ic_math");
        values.put(COLUMN_DURATION, 20);
        db.insert(TABLE_LESSONS, null, values);

        values = new ContentValues();
        values.put(COLUMN_TITLE, "Photosynthesis Explained");
        values.put(COLUMN_CATEGORY, "Science");
        values.put(COLUMN_CONTENT, "Photosynthesis is the process by which plants convert light energy into chemical energy. Understand how chlorophyll, sunlight, water, and carbon dioxide work together to create glucose and oxygen.");
        values.put(COLUMN_IMAGE_URL, "ic_science");
        values.put(COLUMN_DURATION, 18);
        db.insert(TABLE_LESSONS, null, values);

        values = new ContentValues();
        values.put(COLUMN_TITLE, "Atomic Structure");
        values.put(COLUMN_CATEGORY, "Science");
        values.put(COLUMN_CONTENT, "Atoms are the basic building blocks of matter. Learn about protons, neutrons, and electrons, and how they determine an element's properties and behavior.");
        values.put(COLUMN_IMAGE_URL, "ic_science");
        values.put(COLUMN_DURATION, 22);
        db.insert(TABLE_LESSONS, null, values);

        values = new ContentValues();
        values.put(COLUMN_QUESTION, "What is the value of x in 2x + 4 = 10?");
        values.put(COLUMN_OPTION_A, "2");
        values.put(COLUMN_OPTION_B, "3");
        values.put(COLUMN_OPTION_C, "4");
        values.put(COLUMN_OPTION_D, "5");
        values.put(COLUMN_CORRECT_ANSWER, "B");
        values.put(COLUMN_CATEGORY, "Mathematics");
        values.put(COLUMN_EXPLANATION, "2x + 4 = 10 means 2x = 6, so x = 3");
        db.insert(TABLE_QUIZ_QUESTIONS, null, values);

        values = new ContentValues();
        values.put(COLUMN_QUESTION, "What is the sum of angles in a triangle?");
        values.put(COLUMN_OPTION_A, "90 degrees");
        values.put(COLUMN_OPTION_B, "180 degrees");
        values.put(COLUMN_OPTION_C, "270 degrees");
        values.put(COLUMN_OPTION_D, "360 degrees");
        values.put(COLUMN_CORRECT_ANSWER, "B");
        values.put(COLUMN_CATEGORY, "Mathematics");
        values.put(COLUMN_EXPLANATION, "The sum of all interior angles in any triangle is always 180 degrees");
        db.insert(TABLE_QUIZ_QUESTIONS, null, values);

        values = new ContentValues();
        values.put(COLUMN_QUESTION, "What is the main product of photosynthesis?");
        values.put(COLUMN_OPTION_A, "Carbon dioxide");
        values.put(COLUMN_OPTION_B, "Water");
        values.put(COLUMN_OPTION_C, "Glucose");
        values.put(COLUMN_OPTION_D, "Nitrogen");
        values.put(COLUMN_CORRECT_ANSWER, "C");
        values.put(COLUMN_CATEGORY, "Science");
        values.put(COLUMN_EXPLANATION, "Photosynthesis produces glucose (sugar) and oxygen using light energy");
        db.insert(TABLE_QUIZ_QUESTIONS, null, values);

        values = new ContentValues();
        values.put(COLUMN_QUESTION, "What is the center of an atom called?");
        values.put(COLUMN_OPTION_A, "Electron shell");
        values.put(COLUMN_OPTION_B, "Nucleus");
        values.put(COLUMN_OPTION_C, "Proton cloud");
        values.put(COLUMN_OPTION_D, "Neutron layer");
        values.put(COLUMN_CORRECT_ANSWER, "B");
        values.put(COLUMN_CATEGORY, "Science");
        values.put(COLUMN_EXPLANATION, "The nucleus is the dense center containing protons and neutrons");
        db.insert(TABLE_QUIZ_QUESTIONS, null, values);

        values = new ContentValues();
        values.put(COLUMN_QUESTION, "Solve: 5x - 3 = 2x + 6");
        values.put(COLUMN_OPTION_A, "1");
        values.put(COLUMN_OPTION_B, "2");
        values.put(COLUMN_OPTION_C, "3");
        values.put(COLUMN_OPTION_D, "4");
        values.put(COLUMN_CORRECT_ANSWER, "C");
        values.put(COLUMN_CATEGORY, "Mathematics");
        values.put(COLUMN_EXPLANATION, "5x - 3 = 2x + 6, then 3x = 9, so x = 3");
        db.insert(TABLE_QUIZ_QUESTIONS, null, values);

        values = new ContentValues();
        values.put(COLUMN_QUESTION, "How many sides does a hexagon have?");
        values.put(COLUMN_OPTION_A, "4");
        values.put(COLUMN_OPTION_B, "5");
        values.put(COLUMN_OPTION_C, "6");
        values.put(COLUMN_OPTION_D, "7");
        values.put(COLUMN_CORRECT_ANSWER, "C");
        values.put(COLUMN_CATEGORY, "Mathematics");
        values.put(COLUMN_EXPLANATION, "A hexagon has 6 sides and 6 angles");
        db.insert(TABLE_QUIZ_QUESTIONS, null, values);

        values = new ContentValues();
        values.put(COLUMN_QUESTION, "Algebra");
        values.put(COLUMN_ANSWER, "Mathematics branch using symbols and letters to represent numbers");
        values.put(COLUMN_CATEGORY, "Mathematics");
        db.insert(TABLE_FLASHCARDS, null, values);

        values = new ContentValues();
        values.put(COLUMN_QUESTION, "Photosynthesis");
        values.put(COLUMN_ANSWER, "Process where plants convert light energy into chemical energy for food");
        values.put(COLUMN_CATEGORY, "Science");
        db.insert(TABLE_FLASHCARDS, null, values);

        values = new ContentValues();
        values.put(COLUMN_QUESTION, "Hypothesis");
        values.put(COLUMN_ANSWER, "A testable prediction or proposed explanation for a phenomenon");
        values.put(COLUMN_CATEGORY, "Science");
        db.insert(TABLE_FLASHCARDS, null, values);

        values = new ContentValues();
        values.put(COLUMN_QUESTION, "Triangle");
        values.put(COLUMN_ANSWER, "A polygon with three sides, three angles, and sum of 180 degrees");
        values.put(COLUMN_CATEGORY, "Mathematics");
        db.insert(TABLE_FLASHCARDS, null, values);
    }

    public boolean registerUser(String username, String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_PASSWORD, password);
        long result = db.insert(TABLE_USERS, null, values);
        return result != -1;
    }

    public User loginUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS,
                null,
                COLUMN_USERNAME + "=? AND " + COLUMN_PASSWORD + "=?",
                new String[]{username, password},
                null, null, null);
        User user = null;
        if (cursor.moveToFirst()) {
            user = new User();
            user.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)));
            user.setUsername(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USERNAME)));
            user.setEmail(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EMAIL)));
            user.setPassword(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PASSWORD)));
        }
        cursor.close();
        return user;
    }

    public boolean userExists(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS,
                null,
                COLUMN_USERNAME + "=?",
                new String[]{username},
                null, null, null);
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    public List<Lesson> getAllLessons() {
        List<Lesson> lessons = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_LESSONS, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                Lesson lesson = new Lesson();
                lesson.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)));
                lesson.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE)));
                lesson.setCategory(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CATEGORY)));
                lesson.setContent(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTENT)));
                lesson.setImageUrl(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_IMAGE_URL)));
                lesson.setDuration(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_DURATION)));
                lessons.add(lesson);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return lessons;
    }

    public Lesson getLessonById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_LESSONS,
                null,
                COLUMN_ID + "=?",
                new String[]{String.valueOf(id)},
                null, null, null);
        Lesson lesson = null;
        if (cursor.moveToFirst()) {
            lesson = new Lesson();
            lesson.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)));
            lesson.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE)));
            lesson.setCategory(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CATEGORY)));
            lesson.setContent(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTENT)));
            lesson.setImageUrl(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_IMAGE_URL)));
            lesson.setDuration(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_DURATION)));
        }
        cursor.close();
        return lesson;
    }

    public List<Lesson> getLessonsByCategory(String category) {
        List<Lesson> lessons = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_LESSONS,
                null,
                COLUMN_CATEGORY + "=?",
                new String[]{category},
                null, null, null);
        if (cursor.moveToFirst()) {
            do {
                Lesson lesson = new Lesson();
                lesson.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)));
                lesson.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE)));
                lesson.setCategory(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CATEGORY)));
                lesson.setContent(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTENT)));
                lesson.setImageUrl(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_IMAGE_URL)));
                lesson.setDuration(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_DURATION)));
                lessons.add(lesson);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return lessons;
    }

    public List<QuizQuestion> getQuizQuestionsByCategory(String category) {
        List<QuizQuestion> questions = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_QUIZ_QUESTIONS,
                null,
                COLUMN_CATEGORY + "=?",
                new String[]{category},
                null, null, null);
        if (cursor.moveToFirst()) {
            do {
                QuizQuestion question = new QuizQuestion();
                question.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)));
                question.setQuestion(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_QUESTION)));
                question.setOptionA(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_OPTION_A)));
                question.setOptionB(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_OPTION_B)));
                question.setOptionC(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_OPTION_C)));
                question.setOptionD(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_OPTION_D)));
                question.setCorrectAnswer(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CORRECT_ANSWER)));
                question.setCategory(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CATEGORY)));
                question.setExplanation(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EXPLANATION)));
                questions.add(question);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return questions;
    }

    public QuizQuestion getQuizQuestionById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_QUIZ_QUESTIONS,
                null,
                COLUMN_ID + "=?",
                new String[]{String.valueOf(id)},
                null, null, null);
        QuizQuestion question = null;
        if (cursor.moveToFirst()) {
            question = new QuizQuestion();
            question.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)));
            question.setQuestion(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_QUESTION)));
            question.setOptionA(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_OPTION_A)));
            question.setOptionB(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_OPTION_B)));
            question.setOptionC(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_OPTION_C)));
            question.setOptionD(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_OPTION_D)));
            question.setCorrectAnswer(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CORRECT_ANSWER)));
            question.setCategory(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CATEGORY)));
            question.setExplanation(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EXPLANATION)));
        }
        cursor.close();
        return question;
    }

    public void insertQuizResult(String category, int score, int totalQuestions) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_CATEGORY, category);
        values.put(COLUMN_SCORE, score);
        values.put(COLUMN_TOTAL_QUESTIONS, totalQuestions);
        values.put(COLUMN_TIMESTAMP, System.currentTimeMillis());
        db.insert(TABLE_QUIZ_RESULTS, null, values);
    }

    public List<QuizResult> getAllQuizResults() {
        List<QuizResult> results = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_QUIZ_RESULTS, null, null, null, null, null, COLUMN_TIMESTAMP + " DESC");
        if (cursor.moveToFirst()) {
            do {
                QuizResult result = new QuizResult();
                result.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)));
                result.setCategory(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CATEGORY)));
                result.setScore(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_SCORE)));
                result.setTotalQuestions(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_TOTAL_QUESTIONS)));
                result.setTimestamp(cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_TIMESTAMP)));
                results.add(result);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return results;
    }

    public int getHighestScoreByCategory(String category) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT MAX(" + COLUMN_SCORE + ") FROM " + TABLE_QUIZ_RESULTS + " WHERE " + COLUMN_CATEGORY + "=?", new String[]{category});
        int highestScore = 0;
        if (cursor.moveToFirst()) {
            highestScore = cursor.getInt(0);
        }
        cursor.close();
        return highestScore;
    }

    public List<Flashcard> getFlashcardsByCategory(String category) {
        List<Flashcard> flashcards = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_FLASHCARDS,
                null,
                COLUMN_CATEGORY + "=?",
                new String[]{category},
                null, null, null);
        if (cursor.moveToFirst()) {
            do {
                Flashcard flashcard = new Flashcard();
                flashcard.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)));
                flashcard.setQuestion(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_QUESTION)));
                flashcard.setAnswer(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ANSWER)));
                flashcard.setCategory(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CATEGORY)));
                flashcards.add(flashcard);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return flashcards;
    }
}

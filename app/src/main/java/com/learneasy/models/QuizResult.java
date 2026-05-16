package com.learneasy.models;

public class QuizResult {
    private int id;
    private String category;
    private int score;
    private int totalQuestions;
    private long timestamp;

    public QuizResult() {}

    public QuizResult(String category, int score, int totalQuestions, long timestamp) {
        this.category = category;
        this.score = score;
        this.totalQuestions = totalQuestions;
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getTotalQuestions() {
        return totalQuestions;
    }

    public void setTotalQuestions(int totalQuestions) {
        this.totalQuestions = totalQuestions;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public int getPercentage() {
        if (totalQuestions == 0) return 0;
        return (score * 100) / totalQuestions;
    }
}

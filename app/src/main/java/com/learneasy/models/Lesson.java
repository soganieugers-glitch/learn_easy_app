package com.learneasy.models;

public class Lesson {
    private int id;
    private String title;
    private String category;
    private String content;
    private String imageUrl;
    private int duration;

    public Lesson() {}

    public Lesson(String title, String category, String content, String imageUrl, int duration) {
        this.title = title;
        this.category = category;
        this.content = content;
        this.imageUrl = imageUrl;
        this.duration = duration;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}

package com.worktracker.model;

public class Task {
    private String title;
    private String description;
    private boolean completed;

    public Task(String title, String description) {
        this.title = title;
        this.description = description;
        this.completed = false;
    }

    public Task(String title) {
        this(title, ""); // Call the two-parameter constructor with an empty description
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void completeTask() {
        this.completed = true;
    }

    public void reopenTask() {
        this.completed = false;
    }
}
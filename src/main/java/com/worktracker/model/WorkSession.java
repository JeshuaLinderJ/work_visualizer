package com.worktracker.model;

import java.util.ArrayList;
import java.util.List;

public class WorkSession {
    private List<Task> tasks;
    private long startTime;
    private long endTime;

    public WorkSession() {
        this.tasks = new ArrayList<>();
        this.startTime = System.currentTimeMillis();
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public void endSession() {
        this.endTime = System.currentTimeMillis();
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public long getDuration() {
        return endTime - startTime;
    }

    public boolean isCompleted() {
        return endTime > startTime;
    }

    public int getCompletedTasksCount() {
        return (int) tasks.stream().filter(Task::isCompleted).count();
    }

    public long getTotalTimeSpent() {
        return getDuration() / (60 * 1000); // Convert milliseconds to minutes
    }

    public long getTotalTime() {
        return getDuration() / (60 * 1000);
    }

    public String getDate() {
        return new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date(startTime));
    }
}
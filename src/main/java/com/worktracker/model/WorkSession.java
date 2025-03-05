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
        // Fix: ensure we never return negative values
        long calculatedTime = getDuration() / (60 * 1000); // Convert milliseconds to minutes
        
        // Return zero if negative or absurdly large
        return (calculatedTime < 0 || calculatedTime > Integer.MAX_VALUE) ? 0 : calculatedTime;
    }

    public long getTotalTime() {
        // If endTime is 0 (session not ended), use current time
        long currentEndTime = endTime > 0 ? endTime : System.currentTimeMillis();
        long calculatedTime = (currentEndTime - startTime) / (60 * 1000);
        
        // Return zero if negative or absurdly large
        return (calculatedTime < 0 || calculatedTime > Integer.MAX_VALUE) ? 0 : calculatedTime;
    }

    public String getDate() {
        return new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date(startTime));
    }
}
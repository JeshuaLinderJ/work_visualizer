package com.worktracker.controller;

import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;

import com.worktracker.model.Task;
import com.worktracker.service.EmailService;
import com.worktracker.service.StatisticsService;

public class TaskController {
    private List<Task> tasks;
    private StatisticsService statisticsService;
    private EmailService emailService;

    public TaskController(StatisticsService statisticsService, EmailService emailService) {
        this.statisticsService = statisticsService;
        this.emailService = emailService;
        this.tasks = new ArrayList<>();
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public void updateTask(int index, Task task) {
        if (index >= 0 && index < tasks.size()) {
            tasks.set(index, task);
        }
    }

    public void deleteTask(int index) {
        if (index >= 0 && index < tasks.size()) {
            tasks.remove(index);
        }
    }

    public void removeTask(Task task) {
        tasks.remove(task);
    }

    public List<Task> getAllTasks() {
        return tasks;
    }

    public void completeTask(Task task) {
        task.completeTask();
    }

    public void sendReport() throws MessagingException {
        String report = statisticsService.generateReport(tasks);
        emailService.sendEmail("recipient@example.com", "Work Report", report);
    }
}
package com.worktracker.service;
import java.util.List;

import com.worktracker.model.Task;
import com.worktracker.model.WorkSession;

public class StatisticsService {
    public int calculateTotalTasksCompleted(List<WorkSession> workSessions) {
        return workSessions.stream()
                .mapToInt(session -> session.getTasks().size())
                .sum();
    }

    public double calculateAverageWorkTime(List<WorkSession> workSessions) {
        if (workSessions.isEmpty()) {
            return 0;
        }
        return workSessions.stream()
                .mapToLong(WorkSession::getDuration)
                .average()
                .orElse(0) / (60 * 1000); // Convert to minutes
    }

    public int calculateTotalWorkSessions(List<WorkSession> workSessions) {
        return workSessions.size();
    }

    public int calculateTasksCompletedInSession(WorkSession session) {
        return session.getTasks().size();
    }

    public double calculateProductivityPercentage(List<WorkSession> workSessions, int threshold) {
        int completedTasks = calculateTotalTasksCompleted(workSessions);
        return (completedTasks / (double) threshold) * 100;
    }

    public String generateReport(List<Task> tasks) {
        // Generate report based on tasks
        StringBuilder report = new StringBuilder();
        report.append("WORK REPORT\n");
        report.append("====================\n\n");
        
        int completedTasks = 0;
        int pendingTasks = 0;
        
        report.append("TASKS:\n");
        
        // List completed tasks
        report.append("\nCompleted Tasks:\n");
        for (Task task : tasks) {
            if (task.isCompleted()) {
                completedTasks++;
                report.append("✓ ").append(task.getTitle()).append("\n");
            }
        }
        
        // List pending tasks
        report.append("\nPending Tasks:\n");
        for (Task task : tasks) {
            if (!task.isCompleted()) {
                pendingTasks++;
                report.append("○ ").append(task.getTitle()).append("\n");
            }
        }
        
        report.append("\n====================\n");
        report.append("SUMMARY:\n");
        report.append("Total tasks: ").append(tasks.size()).append("\n");
        report.append("Completed tasks: ").append(completedTasks).append(" (")
              .append(tasks.isEmpty() ? 0 : Math.round(100.0 * completedTasks / tasks.size()))
              .append("%)\n");
        report.append("Pending tasks: ").append(pendingTasks).append("\n");
        report.append("====================\n");
        
        return report.toString();
    }
}
package com.worktracker.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.mail.MessagingException;

import com.worktracker.model.WorkSession;
import com.worktracker.service.EmailService;
import com.worktracker.service.GraphGeneratorService;

public class ReportController {

    private final EmailService emailService;
    private final GraphGeneratorService graphGeneratorService;

    public ReportController(EmailService emailService, GraphGeneratorService graphGeneratorService) {
        this.emailService = emailService;
        this.graphGeneratorService = graphGeneratorService;
    }

    public void generateReport(WorkSession workSession) throws MessagingException {
        try {
            String reportContent = createReportContent(workSession);
            String graphImagePath = graphGeneratorService.generateGraph(workSession);
            emailService.sendReport(reportContent, graphImagePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String createReportContent(WorkSession workSession) {
        // Create a formatter for legible local time (without timezone/region)
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String formattedDateTime = LocalDateTime.now().format(formatter);
        
        // Get total time and handle invalid values
        long totalMinutes = workSession.getTotalTimeSpent();
        String timeDisplay;
        
        if (totalMinutes <= 0 || totalMinutes > Integer.MAX_VALUE) {
            // Fix for invalid time value
            timeDisplay = formatTime(0); // Display 0 hours 0 minutes
        } else {
            timeDisplay = formatTime(totalMinutes);
        }
        
        StringBuilder reportBuilder = new StringBuilder();
        reportBuilder.append("Work Session Report\n");
        reportBuilder.append("Tasks Completed: ").append(workSession.getCompletedTasksCount()).append("\n");
        reportBuilder.append("Total Time Spent: ").append(timeDisplay).append("\n");
        reportBuilder.append("Date: ").append(formattedDateTime).append("\n");
        return reportBuilder.toString();
    }

    /**
     * Format minutes into a readable hours and minutes string
     */
    private String formatTime(long totalMinutes) {
        long hours = totalMinutes / 60;
        long minutes = totalMinutes % 60;
        
        if (hours > 0) {
            return hours + " hour" + (hours != 1 ? "s" : "") + 
                   " " + minutes + " minute" + (minutes != 1 ? "s" : "");
        } else {
            return minutes + " minute" + (minutes != 1 ? "s" : "");
        }
    }
}
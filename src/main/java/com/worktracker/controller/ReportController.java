package com.worktracker.controller;

import java.io.IOException;

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
        StringBuilder reportBuilder = new StringBuilder();
        reportBuilder.append("Work Session Report\n");
        reportBuilder.append("Tasks Completed: ").append(workSession.getCompletedTasksCount()).append("\n");
        reportBuilder.append("Total Time Spent: ").append(workSession.getTotalTimeSpent()).append(" minutes\n");
        reportBuilder.append("Date: ").append(workSession.getDate()).append("\n");
        return reportBuilder.toString();
    }
}
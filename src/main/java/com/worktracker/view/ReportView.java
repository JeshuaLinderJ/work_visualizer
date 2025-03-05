package com.worktracker.view;

import com.worktracker.model.WorkSession;
import com.worktracker.service.EmailService;
import com.worktracker.service.GraphGeneratorService;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ReportView extends JPanel {
    private JTextArea reportArea;
    private JButton sendReportButton;
    private EmailService emailService;
    private GraphGeneratorService graphGeneratorService;

    public ReportView(EmailService emailService, GraphGeneratorService graphGeneratorService) {
        this.emailService = emailService;
        this.graphGeneratorService = graphGeneratorService;
        initializeUI();
    }

    private void initializeUI() {
        setBackground(Color.WHITE);
        setLayout(new BorderLayout());

        reportArea = new JTextArea();
        reportArea.setEditable(false);
        add(new JScrollPane(reportArea), BorderLayout.CENTER);

        sendReportButton = new JButton("Send Report");
        sendReportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendReport();
            }
        });
        add(sendReportButton, BorderLayout.SOUTH);
    }

    public void displayReport(WorkSession workSession) {
        String reportContent = generateReportContent(workSession);
        reportArea.setText(reportContent);
    }

    private String generateReportContent(WorkSession workSession) {
        // Generate report content based on the work session data
        try {
            return "Report for Work Session:\n" +
                   "Tasks Completed: " + workSession.getTasks() + "\n" +
                   "Total Time: " + workSession.getTotalTime() + " minutes\n" +
                   "Graph: " + graphGeneratorService.generateGraph(workSession);
        } catch (IOException e) {
            return "Report for Work Session:\n" +
                   "Tasks Completed: " + workSession.getTasks() + "\n" +
                   "Total Time: " + workSession.getTotalTime() + " minutes\n" +
                   "Graph: Unable to generate graph";
        }
    }

    private void sendReport() {
        String reportContent = reportArea.getText();
        emailService.sendReport(reportContent);
        JOptionPane.showMessageDialog(this, "Report sent successfully!");
    }
}
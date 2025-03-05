package com.worktracker.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.worktracker.model.WorkSession;
import com.worktracker.service.EmailService;
import com.worktracker.service.GraphGeneratorService;
import com.worktracker.service.UserSession;

public class ReportView extends JPanel {
    private JTextArea reportArea;
    private JButton sendReportButton;
    private javax.swing.JLabel timeLabel;
    private final EmailService emailService;
    private final GraphGeneratorService graphGeneratorService;

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
        
        timeLabel = new javax.swing.JLabel("Total Time: 0 minutes");
        add(timeLabel, BorderLayout.NORTH);

        sendReportButton = new JButton("Send Report");
        sendReportButton = new JButton("Send Report");
        sendReportButton.addActionListener((ActionEvent e) -> {
            sendReport();
        });
        add(sendReportButton, BorderLayout.SOUTH);
    }

    public void displayReport(WorkSession workSession) {
        String reportContent = generateReportContent(workSession);
        reportArea.setText(reportContent);
        updateTimeDisplay(workSession); // Add this line to update the time label
    }

    private String generateReportContent(WorkSession workSession) {
        // Get properly formatted time
        long minutes = workSession.getTotalTime();
        if (minutes < 0) {
            minutes = 0;
        }
        String formattedTime = formatTime(minutes);
        
        // Generate report content based on the work session data
        try {
            return "Report for Work Session:\n" +
                   "Tasks Completed: " + workSession.getTasks() + "\n" +
                   "Total Time: " + formattedTime + "\n" +
                   "Graph: " + graphGeneratorService.generateGraph(workSession);
        } catch (IOException e) {
            return "Report for Work Session:\n" +
                   "Tasks Completed: " + workSession.getTasks() + "\n" +
                   "Total Time: " + formattedTime + "\n" +
                   "Graph: Unable to generate graph";
        }
    }

    private void sendReport() {
        // Add confirmation dialog
        int choice = JOptionPane.showConfirmDialog(
            this,
            "Do you want to send this report?",
            "Confirm Report Sending",
            JOptionPane.YES_NO_OPTION
        );
        
        // Only send if user confirms
        if (choice == JOptionPane.YES_OPTION) {
            String reportContent = reportArea.getText();
            
            // Check if user is logged in and show detailed message if not
            if (!isUserAuthenticated()) {
                System.out.println("Debug: User not authenticated. Username: " + 
                    (UserSession.getInstance().getUsername() == null ? "null" : 
                    UserSession.getInstance().getUsername()));
                    
                JOptionPane.showMessageDialog(
                    this, 
                    "Please log in before sending reports.\nUse the login button in the top-right corner.", 
                    "Authentication Required", 
                    JOptionPane.WARNING_MESSAGE
                );
                return;
            }
            
            System.out.println("Debug: Attempting to send report with user: " + 
                UserSession.getInstance().getUsername());
                
            // Attempt to send report and check if it was successful
            boolean success = emailService.sendReport(reportContent);
            
            if (success) {
                JOptionPane.showMessageDialog(
                    this, 
                    "Report sent successfully!", 
                    "Success", 
                    JOptionPane.INFORMATION_MESSAGE
                );
            } else {
                JOptionPane.showMessageDialog(
                    this, 
                    "<html>Failed to send report.<br><br>" +
                    "For Gmail accounts:<br>" +
                    "1. Make sure you're using an App Password if 2FA is enabled<br>" +
                    "2. Check that your email and password are correct<br>" +
                    "3. Allow less secure apps if not using 2FA</html>",
                    "Email Error", 
                    JOptionPane.ERROR_MESSAGE
                );
            }
        } else {
            // User canceled sending
            JOptionPane.showMessageDialog(
                this,
                "Report sending canceled.",
                "Canceled",
                JOptionPane.INFORMATION_MESSAGE
            );
        }
    }

    // Add this method to check authentication status
    private boolean isUserAuthenticated() {
        return com.worktracker.service.UserSession.getInstance().isLoggedIn();
    }

    private void updateTimeDisplay(WorkSession workSession) {
        // Get the time from work session and format it properly
        long minutes = workSession.getTotalTimeSpent();
        
        // Safety check - ensure positive value
        if (minutes < 0) {
            minutes = 0;
        }
        
        // Format the time into hours and minutes
        String formattedTime = formatTime(minutes);
        
        // Update the label/field
        timeLabel.setText("Total Time: " + formattedTime);
    }

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
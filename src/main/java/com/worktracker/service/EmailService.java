package com.worktracker.service;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.worktracker.view.EmailLoginDialog;

public class EmailService {
    // Remove hardcoded credentials - we'll get them from the dialog
    private String username;
    private String password;
    private boolean credentialsSet = false;

    // Updated constructor without credentials
    public EmailService() {
        // No credentials set initially
    }

    // If we still want to support direct credential setting
    public EmailService(String username, String password) {
        this.username = username;
        this.password = password;
        this.credentialsSet = true;
    }

    // Method to get credentials via dialog
    private boolean promptForCredentials() {
        // Find the current active window to use as owner
        JFrame owner = null;
        for (java.awt.Frame f : java.awt.Frame.getFrames()) {
            if (f.isActive() && f instanceof JFrame) {
                owner = (JFrame) f;
                break;
            }
        }

        EmailLoginDialog dialog = new EmailLoginDialog(owner);
        dialog.setVisible(true);

        if (!dialog.isCancelled()) {
            this.username = dialog.getEmail();
            this.password = dialog.getPassword();
            this.credentialsSet = true;
            return true;
        }
        return false;
    }

    public void sendEmail(String recipient, String subject, String body) throws MessagingException {
        if (!credentialsSet) {
            boolean gotCredentials = promptForCredentials();
            if (!gotCredentials) {
                throw new MessagingException("Email sending cancelled: No credentials provided");
            }
        }

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com"); // Default to Gmail
        properties.put("mail.smtp.port", "587");
        
        // For Gmail, you might need to use an app password if 2FA is enabled
        // See: https://support.google.com/accounts/answer/185833

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(username));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
        message.setSubject(subject);
        message.setText(body);

        Transport.send(message);
    }

    public boolean sendReport(String reportContent) {
        try {
            // Ask for confirmation before sending
            int choice = JOptionPane.showConfirmDialog(
                null,
                "Would you like to send this report via email?",
                "Confirm Email",
                JOptionPane.YES_NO_OPTION
            );
            
            if (choice == JOptionPane.YES_OPTION) {
                // Before sending, ask for recipient email
                String recipient = JOptionPane.showInputDialog(
                    null, 
                    "Enter recipient email address:",
                    "Email Address", 
                    JOptionPane.QUESTION_MESSAGE
                );
                
                if (recipient == null || recipient.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(
                        null,
                        "Email address required. Report not sent.",
                        "Email Cancelled",
                        JOptionPane.INFORMATION_MESSAGE
                    );
                    return false;
                }

                try {
                    // This will now prompt for credentials if needed
                    sendEmail(recipient, "Work Report", reportContent);
                    
                    JOptionPane.showMessageDialog(
                        null,
                        "Email sent successfully to " + recipient,
                        "Email Sent",
                        JOptionPane.INFORMATION_MESSAGE
                    );
                    return true;
                } catch (MessagingException e) {
                    if (e.getMessage().contains("cancelled")) {
                        JOptionPane.showMessageDialog(
                            null,
                            "Email sending cancelled.",
                            "Email Cancelled",
                            JOptionPane.INFORMATION_MESSAGE
                        );
                    } else {
                        JOptionPane.showMessageDialog(
                            null,
                            "Failed to send email: " + e.getMessage() + 
                            "\n\nNote: For Gmail, you may need to use an app password if 2FA is enabled." +
                            "\nSee: https://support.google.com/accounts/answer/185833",
                            "Email Error",
                            JOptionPane.ERROR_MESSAGE
                        );
                    }
                    return false;
                }
            } else {
                // User declined to send email
                return false;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                null, 
                "Error processing report: " + e.getMessage(), 
                "Report Error", 
                JOptionPane.ERROR_MESSAGE
            );
            return false;
        }
    }

    public boolean sendReport(String content, String attachmentPath) {
        // Ask for confirmation before sending
        int choice = JOptionPane.showConfirmDialog(
            null,
            "Would you like to send this report with attachment via email?",
            "Confirm Email",
            JOptionPane.YES_NO_OPTION
        );
        
        if (choice == JOptionPane.YES_OPTION) {
            String recipient = JOptionPane.showInputDialog(
                null, 
                "Enter recipient email address:",
                "Email Address", 
                JOptionPane.QUESTION_MESSAGE
            );
            
            if (recipient == null || recipient.trim().isEmpty()) {
                JOptionPane.showMessageDialog(
                    null,
                    "Email address required. Report not sent.",
                    "Email Cancelled",
                    JOptionPane.INFORMATION_MESSAGE
                );
                return false;
            }

            try {
                // This will now prompt for credentials if needed
                sendEmail(recipient, "Work Report with Graph", content);
                
                JOptionPane.showMessageDialog(
                    null,
                    "Email sent successfully to " + recipient,
                    "Email Sent",
                    JOptionPane.INFORMATION_MESSAGE
                );
                return true;
            } catch (MessagingException e) {
                JOptionPane.showMessageDialog(
                    null,
                    "Failed to send email: " + e.getMessage(),
                    "Email Error",
                    JOptionPane.ERROR_MESSAGE
                );
                return false;
            }
        } else {
            // User declined to send email
            return false;
        }
    }
}
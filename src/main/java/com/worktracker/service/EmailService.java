package com.worktracker.service;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
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
        properties.put("mail.smtp.ssl.protocols", "TLSv1.2");
        
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
        UserSession session = UserSession.getInstance();
        if (!session.isLoggedIn()) {
            System.err.println("Cannot send email: User not logged in");
            return false;
        }
        
        // Get user credentials from session
        final String username = session.getUsername();
        final String password = session.getPassword(); 
        
        System.out.println("Attempting to send email using: " + username);

        // Email configuration for Gmail with SSL
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");
        
        // Fix the username confusion - always use the full email address
        if (!username.contains("@")) {
            JOptionPane.showMessageDialog(
                null,
                "Email username must be a valid email address format (e.g., user@gmail.com)",
                "Invalid Email Format",
                JOptionPane.ERROR_MESSAGE
            );
            return false;
        }

        try {
            // Create authenticator with proper error handling
            Authenticator auth = new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            };

            // Create session
            Session mailSession = Session.getInstance(props, auth);
            mailSession.setDebug(true); // Set to false in production

            // Create message
            MimeMessage message = new MimeMessage(mailSession);
            message.setFrom(new InternetAddress(username));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(username));
            message.setSubject("Work Tracker Report");
            
            // Create multipart message
            MimeMultipart multipart = new MimeMultipart();
            
            // Text part
            MimeBodyPart textPart = new MimeBodyPart();
            textPart.setText(reportContent, "utf-8");
            multipart.addBodyPart(textPart);
            
            // Set content
            message.setContent(multipart);
            
            // Send message
            Transport.send(message);
            System.out.println("Email sent successfully to: " + username);
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
            System.err.println("Email error: " + e.getMessage());
            
            // Show helpful error dialog for Gmail authentication issues
            if (e.getMessage().contains("Username and Password not accepted")) {
                JOptionPane.showMessageDialog(
                    null,
                    "<html><b>Gmail Authentication Failed</b><br><br>" +
                    "If you have 2-Factor Authentication enabled on your Google account:<br>" +
                    "1. Go to Google Account → Security → App passwords<br>" +
                    "2. Generate a new app password for 'Mail' and 'Other'<br>" +
                    "3. Use that 16-character password instead of your regular password<br><br>" +
                    "If you don't have 2FA enabled:<br>" +
                    "Ensure you've allowed less secure apps in your Google account settings</html>",
                    "Gmail Authentication Error",
                    JOptionPane.ERROR_MESSAGE
                );
            }
            
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

    // Add this to your EmailService class
    public boolean testGmailConnection(String username, String password) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        
        try {
            Session session = Session.getInstance(props, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });
            
            Transport transport = session.getTransport("smtp");
            transport.connect("smtp.gmail.com", username, password);
            transport.close();
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
    }
}
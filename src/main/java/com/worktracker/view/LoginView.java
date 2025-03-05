package com.worktracker.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.worktracker.service.EmailService;
import com.worktracker.service.UserSession;
import com.worktracker.util.ColorTheme;

public class LoginView extends JDialog {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JCheckBox rememberMeCheckbox;
    private boolean loginSuccessful = false;

    public LoginView(JFrame parent) {
        super(parent, "Login", true);
        initComponents();
        setSize(350, 250);
        setLocationRelativeTo(parent);
    }

    private void initComponents() {
        // Set layout
        setLayout(new BorderLayout());
        
        // Create panel for form with some padding
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(ColorTheme.BACKGROUND_COLOR);
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Username label and field
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setForeground(ColorTheme.TEXT_COLOR);
        formPanel.add(usernameLabel, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1;
        usernameField = new JTextField(15);
        formPanel.add(usernameField, gbc);
        
        // Password label and field
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setForeground(ColorTheme.TEXT_COLOR);
        formPanel.add(passwordLabel, gbc);
        
        // Password panel with 2FA note
        JPanel passwordPanel = new JPanel(new BorderLayout());
        passwordPanel.setBackground(ColorTheme.BACKGROUND_COLOR);
        passwordField = new JPasswordField(15);
        passwordPanel.add(passwordField, BorderLayout.NORTH);

        JLabel twoFANote = new JLabel("<html>For Gmail with 2FA: Use an App Password<br>" +
            "Go to Google Account → Security → App passwords</html>");
        twoFANote.setFont(new Font("Arial", Font.ITALIC, 10));
        twoFANote.setForeground(ColorTheme.TEXT_COLOR);
        passwordPanel.add(twoFANote, BorderLayout.SOUTH);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 1;
        formPanel.add(passwordPanel, gbc);
        
        // Remember me checkbox
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        rememberMeCheckbox = new JCheckBox("Remember me");
        rememberMeCheckbox.setForeground(ColorTheme.TEXT_COLOR);
        rememberMeCheckbox.setBackground(ColorTheme.BACKGROUND_COLOR);
        formPanel.add(rememberMeCheckbox, gbc);
        
        add(formPanel, BorderLayout.CENTER);
        
        // Create buttons panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(ColorTheme.BACKGROUND_COLOR);
        
        JButton loginButton = new JButton("Login");
        loginButton.addActionListener((ActionEvent e) -> {
            attemptLogin();
        });
        
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener((ActionEvent e) -> {
            dispose();
        });
        
        buttonPanel.add(loginButton);
        buttonPanel.add(cancelButton);
        
        add(buttonPanel, BorderLayout.SOUTH);
        
        // Set default button to login button
        getRootPane().setDefaultButton(loginButton);
    }
    
    private void attemptLogin() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        boolean rememberMe = rememberMeCheckbox.isSelected();
        
        // Ensure email format for Gmail
        if (!username.contains("@")) {
            JOptionPane.showMessageDialog(
                this,
                "Please enter a complete email address (e.g., user@gmail.com)",
                "Invalid Email Format",
                JOptionPane.ERROR_MESSAGE
            );
            return;
        }
        
        // Test Gmail connection first
        EmailService emailTester = new EmailService();
        boolean connectionSuccessful = emailTester.testGmailConnection(username, password);
        
        if (connectionSuccessful) {
            UserSession session = UserSession.getInstance();
            session.login(username, password, rememberMe);
            loginSuccessful = true;
            dispose(); // Close dialog on successful login
            
            JOptionPane.showMessageDialog(
                null,
                "Login successful! Gmail connection verified.",
                "Login Success",
                JOptionPane.INFORMATION_MESSAGE
            );
        } else {
            // Show detailed help for Gmail authentication issues
            JOptionPane.showMessageDialog(
                this,
                "<html><b>Gmail Authentication Failed</b><br><br>" +
                "If you have 2-Factor Authentication enabled:<br>" +
                "1. Go to Google Account → Security → App passwords<br>" +
                "2. Generate a new app password for 'Mail'<br>" +
                "3. Use that 16-character password instead of your regular password<br><br>" +
                "If you don't have 2FA enabled:<br>" +
                "Check that your email and password are correct</html>",
                "Login Failed",
                JOptionPane.ERROR_MESSAGE
            );
            
            // Clear the password field for security
            passwordField.setText("");
        }
    }
    
    public boolean isLoginSuccessful() {
        return loginSuccessful;
    }
}
package com.worktracker.view;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class EmailLoginDialog extends JDialog {
    private JTextField emailField;
    private JPasswordField passwordField;
    private boolean cancelled = true;
    
    public EmailLoginDialog(Frame owner) {
        super(owner, "Email Login", true);
        
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(5, 5, 5, 5);
        
        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(new JLabel("Email:"), constraints);
        
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.weightx = 1.0;
        emailField = new JTextField(20);
        panel.add(emailField, constraints);
        
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.weightx = 0.0;
        panel.add(new JLabel("Password:"), constraints);
        
        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.weightx = 1.0;
        passwordField = new JPasswordField(20);
        panel.add(passwordField, constraints);
        
        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> {
            cancelled = false;
            dispose();
        });
        
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> dispose());
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);
        
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(panel, BorderLayout.CENTER);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);
        
        pack();
        setLocationRelativeTo(owner);
    }
    
    public String getEmail() {
        return emailField.getText();
    }
    
    public String getPassword() {
        return new String(passwordField.getPassword());
    }
    
    public boolean isCancelled() {
        return cancelled;
    }
}
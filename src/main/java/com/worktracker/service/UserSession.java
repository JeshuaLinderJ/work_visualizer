package com.worktracker.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class UserSession {
    private static final UserSession INSTANCE = new UserSession(); // Eager initialization for reliability
    private boolean loggedIn = false;
    private String username = null;
    private String password = null; // Store securely in real application
    private static final String CONFIG_FILE = "user_session.properties";
    
    // Private constructor prevents instantiation
    private UserSession() {
        loadSavedSession();
    }
    
    public static UserSession getInstance() {
        return INSTANCE;
    }
    
    public boolean isLoggedIn() {
        return loggedIn;
    }
    
    public String getUsername() {
        return username;
    }
    
    public String getPassword() {
        return password; // In a real app, use secure credential storage
    }
    
    public boolean login(String username, String password, boolean rememberMe) {
        // In a real app, validate credentials against a database
        if (isValidCredentials(username, password)) {
            this.loggedIn = true;
            this.username = username;
            this.password = password; // Store for email service authentication
            
            if (rememberMe) {
                saveSession();
            }
            return true;
        }
        return false;
    }
    
    public void logout() {
        this.loggedIn = false;
        this.username = null;
        this.password = null;
        clearSavedSession();
    }
    
    private boolean isValidCredentials(String username, String password) {
        // In a real app, check against database
        // For demo purposes, accept non-empty credentials
        return username != null && !username.trim().isEmpty() && 
               password != null && !password.trim().isEmpty();
    }
    
    private void saveSession() {
        try {
            Properties props = new Properties();
            props.setProperty("username", username);
            // In a real app, NEVER store passwords in plain text
            props.setProperty("password", password); 
            props.setProperty("loggedIn", "true");
            
            try (FileOutputStream out = new FileOutputStream(CONFIG_FILE)) {
                props.store(out, "Work Tracker User Session");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void loadSavedSession() {
        File configFile = new File(CONFIG_FILE);
        if (!configFile.exists()) {
            return;
        }
        
        try {
            Properties props = new Properties();
            try (FileInputStream in = new FileInputStream(configFile)) {
                props.load(in);
            }
            
            String savedUsername = props.getProperty("username");
            String savedPassword = props.getProperty("password");
            String savedLoggedIn = props.getProperty("loggedIn");
            
            if ("true".equals(savedLoggedIn) && savedUsername != null) {
                this.username = savedUsername;
                this.password = savedPassword;
                this.loggedIn = true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void clearSavedSession() {
        File configFile = new File(CONFIG_FILE);
        if (configFile.exists()) {
            configFile.delete();
        }
    }
}

package com.worktracker.model;

public class UserSettings {
    private int workThreshold; // Minimum work units to be considered productive
    private boolean notificationsEnabled; // User preference for notifications

    public UserSettings() {
        this.workThreshold = 5; // Default threshold
        this.notificationsEnabled = true; // Default notification preference
    }

    public int getWorkThreshold() {
        return workThreshold;
    }

    public void setWorkThreshold(int workThreshold) {
        this.workThreshold = workThreshold;
    }

    public boolean isNotificationsEnabled() {
        return notificationsEnabled;
    }

    public void setNotificationsEnabled(boolean notificationsEnabled) {
        this.notificationsEnabled = notificationsEnabled;
    }

    public void setNotificationPreference(boolean preference) {
        this.notificationsEnabled = preference;
    }

    public void resetToDefaults() {
        this.workThreshold = 5;
        this.notificationsEnabled = true;
    }
}
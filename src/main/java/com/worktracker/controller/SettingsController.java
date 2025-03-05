package com.worktracker.controller;

import com.worktracker.model.UserSettings;

public class SettingsController {
    private UserSettings userSettings;

    public SettingsController() {
        this.userSettings = new UserSettings();
    }

    public UserSettings getUserSettings() {
        return userSettings;
    }

    public void setWorkThreshold(int threshold) {
        userSettings.setWorkThreshold(threshold);
    }

    public void setNotificationPreference(boolean preference) {
        userSettings.setNotificationPreference(preference);
    }

    public void resetSettings() {
        userSettings.resetToDefaults();
    }
}
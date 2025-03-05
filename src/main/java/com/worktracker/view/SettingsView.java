package com.worktracker.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingsView extends JFrame {
    private JTextField workThresholdField;
    private JButton saveButton;
    private JLabel messageLabel;

    public SettingsView() {
        setTitle("Settings");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(4, 2));

        add(new JLabel("Work Threshold:"));
        workThresholdField = new JTextField();
        add(workThresholdField);

        saveButton = new JButton("Save Settings");
        add(saveButton);

        messageLabel = new JLabel("");
        add(messageLabel);

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveSettings();
            }
        });

        setVisible(true);
    }

    private void saveSettings() {
        String threshold = workThresholdField.getText();
        // Logic to save the threshold in UserSettings would go here
        messageLabel.setText("Settings saved: Work Threshold = " + threshold);
    }
}
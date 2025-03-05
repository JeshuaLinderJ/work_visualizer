package com.worktracker.view.components;

import javax.swing.*;
import java.awt.*;

public class TaskBox extends JPanel {
    private String title;
    private String description;
    private boolean isCompleted;

    public TaskBox(String title, String description) {
        this.title = title;
        this.description = description;
        this.isCompleted = false;
        initialize();
    }

    private void initialize() {
        setLayout(new BorderLayout());
        setBackground(new Color(222, 184, 135)); // Light brown background
        setBorder(BorderFactory.createLineBorder(new Color(205, 133, 63), 2)); // Border color

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setForeground(new Color(139, 69, 19)); // Dark brown text
        add(titleLabel, BorderLayout.NORTH);

        JTextArea descriptionArea = new JTextArea(description);
        descriptionArea.setEditable(false);
        descriptionArea.setBackground(new Color(255, 228, 225)); // Soft pink background
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        add(descriptionArea, BorderLayout.CENTER);

        JButton completeButton = new JButton("Complete");
        completeButton.addActionListener(e -> markAsCompleted());
        add(completeButton, BorderLayout.SOUTH);
    }

    private void markAsCompleted() {
        isCompleted = true;
        setBackground(new Color(144, 238, 144)); // Light green background for completed tasks
        repaint();
    }

    public boolean isCompleted() {
        return isCompleted;
    }
}
package com.worktracker.view;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import com.worktracker.controller.TaskController;
import com.worktracker.model.Task;
import com.worktracker.model.WorkSession;
import com.worktracker.service.EmailService;
import com.worktracker.service.GraphGeneratorService;
import com.worktracker.service.StatisticsService;
import com.worktracker.util.ColorTheme;

public class MainView extends JFrame {

    public MainView() {
        setTitle("Work Tracker");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Set the color theme
        getContentPane().setBackground(ColorTheme.BACKGROUND_COLOR);

        // Create header
        JLabel header = new JLabel("Work Tracker", SwingConstants.CENTER);
        header.setFont(new Font("Arial", Font.BOLD, 24));
        header.setForeground(ColorTheme.TEXT_COLOR);
        getContentPane().add(header, BorderLayout.NORTH);

        // Create main content area
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(1, 2));
        mainPanel.setBackground(ColorTheme.BACKGROUND_COLOR);
        
        // Create controller with services
        TaskController taskController = new TaskController(
            new StatisticsService(), 
            new EmailService()
        );

        // Add some sample tasks
        Task task1 = new Task("Complete project proposal", "First task description");
        Task task2 = new Task("Review code", "Second task description");
        task2.completeTask();
        Task task3 = new Task("Update documentation", "Third task description");
        taskController.addTask(task1);
        taskController.addTask(task2);
        taskController.addTask(task3);

        // Create a sample work session
        WorkSession sampleSession = new WorkSession();
        sampleSession.addTask(task1);
        sampleSession.addTask(task2);
        sampleSession.addTask(task3);

        // Create views with data
        mainPanel.add(new TaskBoardView(taskController));
        
        ReportView reportView = new ReportView(
            new EmailService(), 
            new GraphGeneratorService()
        );
        reportView.displayReport(sampleSession); // Show sample data
        mainPanel.add(reportView);

        getContentPane().add(mainPanel, BorderLayout.CENTER);

        // Create footer
        JLabel footer = new JLabel("Stay productive!", SwingConstants.CENTER);
        footer.setFont(new Font("Arial", Font.ITALIC, 16));
        footer.setForeground(ColorTheme.TEXT_COLOR);
        getContentPane().add(footer, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainView mainView = new MainView();
            mainView.setVisible(true);
        });
    }
}
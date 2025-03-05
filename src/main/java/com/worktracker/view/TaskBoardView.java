package com.worktracker.view;

import com.worktracker.controller.TaskController;
import com.worktracker.model.Task;
import com.worktracker.util.ColorTheme;

import javax.swing.*;
import java.awt.*;
import java.awt.font.TextAttribute;
import java.util.List;
import java.util.Map;

public class TaskBoardView extends JPanel {
    private TaskController taskController;
    private JPanel tasksPanel; // Add this field to store the tasks panel
    
    public TaskBoardView(TaskController taskController) {
        this.taskController = taskController;
        initializeUI();
    }
    
    private void initializeUI() {
        setBackground(Color.WHITE);
        setLayout(new BorderLayout());
        
        // Create header
        JLabel header = new JLabel("Task Board", SwingConstants.CENTER);
        header.setFont(new Font("Arial", Font.BOLD, 18));
        add(header, BorderLayout.NORTH);
        
        // Create task input panel
        JPanel inputPanel = new JPanel(new BorderLayout());
        JTextField taskInput = new JTextField();
        JButton addButton = new JButton("Add Task");
        addButton.addActionListener(e -> {
            String title = taskInput.getText().trim();
            if (!title.isEmpty()) {
                taskController.addTask(new Task(title, "Added from UI"));
                taskInput.setText("");
                updateTaskList(); // Refresh the display
            }
        });
        inputPanel.add(taskInput, BorderLayout.CENTER);
        inputPanel.add(addButton, BorderLayout.EAST);
        add(inputPanel, BorderLayout.SOUTH);
        
        // Create tasks panel with scroll
        tasksPanel = new JPanel();
        tasksPanel.setLayout(new BoxLayout(tasksPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(tasksPanel);
        add(scrollPane, BorderLayout.CENTER);
        
        // Initial population of tasks
        updateTaskList();
    }
    
    // Add this method to refresh the task list
    private void updateTaskList() {
        // Clear existing tasks
        tasksPanel.removeAll();
        
        // Get updated task list
        List<Task> tasks = taskController.getAllTasks();
        
        // Add tasks to panel
        for (Task task : tasks) {
            JPanel taskPanel = createTaskPanel(task);
            tasksPanel.add(taskPanel);
            tasksPanel.add(Box.createVerticalStrut(5)); // spacing
        }
        
        // Force UI update
        tasksPanel.revalidate();
        tasksPanel.repaint();
    }
    
    private JPanel createTaskPanel(Task task) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        panel.setBackground(task.isCompleted() ? new Color(230, 255, 230) : Color.WHITE);
        
        JLabel titleLabel = new JLabel(task.getTitle());
        titleLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        if (task.isCompleted()) {
            titleLabel.setText("✓ " + task.getTitle());
            Font font = titleLabel.getFont();
            @SuppressWarnings("unchecked")
            Map<TextAttribute, Object> attributes = (Map<TextAttribute, Object>) font.getAttributes();
            attributes.put(TextAttribute.STRIKETHROUGH, TextAttribute.STRIKETHROUGH_ON);
            titleLabel.setFont(font.deriveFont(attributes));
        }
        panel.add(titleLabel, BorderLayout.CENTER);
        
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonsPanel.setOpaque(false);
        
        JButton completeButton = new JButton(task.isCompleted() ? "Reopen" : "Complete");
        completeButton.addActionListener(e -> {
            if (task.isCompleted()) {
                task.reopenTask();
            } else {
                task.completeTask();
            }
            updateTaskList(); // Update UI after changing task state
        });
        
        JButton deleteButton = new JButton("Delete");
        deleteButton.setForeground(Color.RED);
        deleteButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete this task?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION
            );
            if (confirm == JOptionPane.YES_OPTION) {
                taskController.removeTask(task);
                updateTaskList();
            }
        });
        
        buttonsPanel.add(completeButton);
        buttonsPanel.add(deleteButton);
        panel.add(buttonsPanel, BorderLayout.EAST);
        
        return panel;
    }
}
package com.worktracker.view.components;

import javax.swing.*;
import java.awt.*;

public class CelebrationAnimation extends JPanel {
    private String message;
    private Color color;

    public CelebrationAnimation(String message) {
        this.message = message;
        this.color = new Color(255, 182, 193); // Light pink color
        setPreferredSize(new Dimension(400, 200));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawCelebration(g);
    }

    private void drawCelebration(Graphics g) {
        g.setColor(color);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 24));
        g.drawString(message, 50, 100);
        // Additional animation effects can be added here
    }

    public void showCelebration() {
        JFrame frame = new JFrame("Celebration!");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.add(this);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
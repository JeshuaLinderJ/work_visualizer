package com.worktracker.view.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.List;

import javax.swing.JPanel;

public class ProgressChart extends JPanel {
    private List<Integer> workData;
    private String title;

    public ProgressChart(List<Integer> workData, String title) {
        this.workData = workData;
        this.title = title;
        setPreferredSize(new Dimension(400, 300));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawChart(g);
    }

    private void drawChart(Graphics g) {
        int maxWork = workData.stream().max(Integer::compare).orElse(1);
        int barWidth = getWidth() / workData.size();
        
        g.setColor(new Color(210, 180, 140)); // Light brown
        g.fillRect(0, getHeight() - 20, getWidth(), 20); // Base line

        for (int i = 0; i < workData.size(); i++) {
            int barHeight = (int) ((workData.get(i) / (double) maxWork) * (getHeight() - 40));
            g.setColor(new Color(255, 192, 203)); // Soft pink
            g.fillRect(i * barWidth, getHeight() - barHeight - 20, barWidth - 2, barHeight);
        }

        g.setColor(Color.BLACK);
        g.drawString(title, 10, 20);
    }

    public void updateData(List<Integer> newData) {
        this.workData = newData;
        repaint();
    }
}
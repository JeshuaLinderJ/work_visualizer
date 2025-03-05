package com.worktracker.service;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

import com.worktracker.model.WorkSession;

public class GraphGeneratorService {

    public ImageIcon generateWorkGraph(List<Integer> workData) {
        int width = 800;
        int height = 400;
        BufferedImage graphImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = graphImage.getGraphics();

        // Set background color
        g.setColor(new Color(240, 240, 240)); // Light gray background
        g.fillRect(0, 0, width, height);

        // Draw axes
        g.setColor(Color.BLACK);
        g.drawLine(50, height - 50, width - 50, height - 50); // X-axis
        g.drawLine(50, height - 50, 50, 50); // Y-axis

        // Draw graph
        int maxWork = workData.stream().max(Integer::compare).orElse(1);
        int prevX = 50;
        int prevY = height - 50 - (int) ((workData.get(0) / (double) maxWork) * (height - 100));

        for (int i = 1; i < workData.size(); i++) {
            int x = 50 + (i * (width - 100) / (workData.size() - 1));
            int y = height - 50 - (int) ((workData.get(i) / (double) maxWork) * (height - 100));
            g.setColor(new Color(100, 150, 200)); // Line color
            g.drawLine(prevX, prevY, x, y);
            prevX = x;
            prevY = y;
        }

        g.dispose();
        return new ImageIcon(graphImage);
    }

    // Add this method
    public String generateGraph(WorkSession workSession) throws IOException {
        // Extract work data from the session and generate a graph
        List<Integer> workData = new ArrayList<>();
        // Simple implementation - add more logic as needed
        workSession.getTasks().forEach(task -> workData.add(task.isCompleted() ? 1 : 0));
        
        // Generate and save temporary image
        String tempFilePath = "temp_graph_" + System.currentTimeMillis() + ".png";
        // Create a buffered image and save it
        // ...
        
        return tempFilePath;
    }
}
package com.worktracker;

import javax.swing.SwingUtilities;

import com.worktracker.view.MainView;

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainView mainView = new MainView();
            mainView.setVisible(true);
        });
    }
}
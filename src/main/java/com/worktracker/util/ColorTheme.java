package com.worktracker.util;

import java.awt.Color;

public class ColorTheme {
    public static final Color WHITE = new Color(255, 255, 255);
    public static final Color SOFT_PINK = new Color(255, 182, 193);
    public static final Color MUTE_GREEN = new Color(139, 169, 119);
    public static final Color DUSTY_BLUE = new Color(100, 149, 237);
    public static final Color GRAY = new Color(128, 128, 128);
    
    public static final Color BACKGROUND_COLOR = GRAY;
    public static final Color TEXT_COLOR = Color.BLACK;
    public static final Color BORDER_COLOR = WHITE;
    public static final Color TASK_BOX_COLOR = SOFT_PINK;
    
    public static Color getBackgroundColor() {
        return GRAY;
    }

    public static Color getPrimaryColor() {
        return WHITE;
    }

    public static Color getAccentColor() {
        return SOFT_PINK;
    }

    public static Color getTextColor() {
        return Color.BLACK;
    }
}
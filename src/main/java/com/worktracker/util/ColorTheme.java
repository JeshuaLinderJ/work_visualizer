package com.worktracker.util;

import java.awt.Color;

public class ColorTheme {
    public static final Color LIGHT_BROWN = new Color(181, 101, 29);
    public static final Color SOFT_PINK = new Color(255, 182, 193);
    public static final Color MUTE_GREEN = new Color(139, 169, 119);
    public static final Color DUSTY_BLUE = new Color(100, 149, 237);
    public static final Color WARM_BEIGE = new Color(245, 222, 179);
    
    public static final Color BACKGROUND_COLOR = WARM_BEIGE;
    public static final Color TEXT_COLOR = Color.BLACK;
    public static final Color BORDER_COLOR = LIGHT_BROWN;
    public static final Color TASK_BOX_COLOR = SOFT_PINK;
    
    public static Color getBackgroundColor() {
        return WARM_BEIGE;
    }

    public static Color getPrimaryColor() {
        return LIGHT_BROWN;
    }

    public static Color getAccentColor() {
        return SOFT_PINK;
    }

    public static Color getTextColor() {
        return Color.BLACK;
    }
}
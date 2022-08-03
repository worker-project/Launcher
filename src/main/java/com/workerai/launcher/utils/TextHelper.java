package com.workerai.launcher.utils;

import javafx.scene.text.Text;

public class TextHelper {

    public static double getTextWidth(String string) {
        final Text text3 = new Text(string);
        text3.applyCss();
        return text3.getLayoutBounds().getWidth();
    }
}

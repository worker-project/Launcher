package com.noideaindustry.jui.geometry;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public abstract class JuiRectangle {
    public static Rectangle createRectangle(double posX, double posY, double sizeX, double sizeY, Color color) {
        Rectangle rectangle = new Rectangle(sizeX, sizeY);
        rectangle.setFill(color);

        rectangle.setTranslateX(posX);
        rectangle.setTranslateY(posY);

        return rectangle;
    }

    public static Rectangle createRoundedRectangle(double width, double height, double arcHeight, double arcWidth, Color color) {
        return createRoundedRectangle(0d, 0d, width, height, arcHeight, arcWidth, color);
    }

    public static Rectangle createRoundedRectangle(double posX, double posY, double width, double height, double arcHeight, double arcWidth, Color color) {
        Rectangle rectangle = new Rectangle(width, height);
        rectangle.setFill(color);

        rectangle.setArcHeight(arcHeight);
        rectangle.setArcWidth(arcWidth);

        rectangle.setTranslateX(posX);
        rectangle.setTranslateY(posY);

        return rectangle;
    }

    public static Rectangle createStyledRectangle(double posX, double posY, double width, double height, String styleClass) {
        Rectangle rectangle = new Rectangle(width, height);

        rectangle.getStyleClass().add(styleClass);

        rectangle.setTranslateX(posX);
        rectangle.setTranslateY(posY);

        return rectangle;
    }
}

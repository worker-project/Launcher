package com.noideaindustry.jui.geometry;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;

public abstract class JuiCircle {
    public static Circle createStrokeCircle(double posX, double posY, double radius, double strokeWidth, double strokeLimit, StrokeType strokeType, Color strokeColor, Color color, Pane pane) {
        Circle circle = new Circle(radius, color);

        circle.setTranslateX(posX);
        circle.setTranslateY(posY);

        circle.setStrokeWidth(strokeWidth);
        circle.setStrokeMiterLimit(strokeLimit);
        circle.setStrokeType(strokeType);
        circle.setStroke(strokeColor);

        if(pane != null) pane.getChildren().add(circle);
        return circle;
    }

    public static Circle createCircle(double radius, Color color) {
        return new Circle(radius, color);
    }
}

package com.noideaindustry.jui;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;

public abstract class JuiGeometry {
    public static abstract class JuiRectangle {
        public static Rectangle createRectangle(double posX, double posY, double sizeX, double sizeY, Color color) {
            Rectangle rectangle = new Rectangle(sizeX, sizeY);
            rectangle.setFill(color);

            rectangle.setTranslateX(posX);
            rectangle.setTranslateY(posY);

            return rectangle;
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

    public static abstract class JuiCircle {
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
}

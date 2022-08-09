package com.noideaindustry.jui.components;

import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;

public abstract class JuiSlider {
    public static Slider createSlider(double posX, double posY, double width, double height, double minValue, double maxValue, double currValue, String styleClass, HBox pane) {
        Slider slider = new Slider();

        slider.getStyleClass().add(styleClass);
        slider.setTranslateX(posX);
        slider.setTranslateY(posY);
        slider.setPrefWidth(width);
        slider.setPrefHeight(height);
        slider.setMin(minValue);
        slider.setMax(maxValue);
        slider.setValue(currValue);

        if (pane != null) pane.getChildren().add(slider);
        return slider;
    }
}

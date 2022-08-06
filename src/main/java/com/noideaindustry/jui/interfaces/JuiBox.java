package com.noideaindustry.jui.interfaces;

import javafx.geometry.Insets;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public abstract class JuiBox {
    public static HBox createHBox(double spacing, double topInsets, double rightInsets, double bottomInsets, double leftInsets) {
        return createHBox(spacing, topInsets, rightInsets, bottomInsets, leftInsets, null);
    }

    public static HBox createHBox(double spacing, double topInsets, double rightInsets, double bottomInsets, double leftInsets, Pane pane) {
        HBox hBox = new HBox(spacing);
        hBox.setPadding(new Insets(topInsets, rightInsets, bottomInsets, leftInsets));

        if (pane != null) pane.getChildren().add(hBox);
        return hBox;
    }
}

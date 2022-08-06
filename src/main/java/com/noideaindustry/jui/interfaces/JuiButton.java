package com.noideaindustry.jui.interfaces;

import com.noideaindustry.jui.JuiInitialization;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public abstract class JuiButton {
    public static Button createFontButton(double posX, double posY, double width, double height, String displayText, String styleClass, Tooltip tooltip, FontAwesomeIconView displayIcon, Pos alignement, Pane pane) {
        Button button = new Button();

        button.setTooltip(tooltip);
        button.getStyleClass().add(styleClass);
        button.setText(displayText);
        button.setMaxWidth(width);
        button.setMaxHeight(height);
        button.setGraphic(displayIcon);
        StackPane.setAlignment(button, alignement);
        button.setTranslateX(posX);
        button.setTranslateY(posY);

        button.setOnMouseEntered(e -> JuiInitialization.getFxStage().getScene().setCursor(Cursor.HAND));
        button.setOnMouseExited(e -> JuiInitialization.getFxStage().getScene().setCursor(Cursor.DEFAULT));

        if (pane != null) pane.getChildren().add(button);
        return button;
    }

    public static Button createMaterialButton(double posX, double posY, boolean listener, String styleClass, MaterialDesignIconView displayIcon, Pane pane) {
        return createMaterialButton(posX, posY, 0d, 0d, listener, null, styleClass, null, displayIcon, null, pane);
    }
    public static Button createMaterialButton(double posX, double posY, double width, double height, boolean listener, String styleClass, MaterialDesignIconView displayIcon, Pane pane) {
        return createMaterialButton(posX, posY, width, height, listener, null, styleClass, null, displayIcon, null, pane);
    }
    public static Button createMaterialButton(double posX, double posY, double width, double height, boolean listener, String displayText, String styleClass, Tooltip tooltip, MaterialDesignIconView displayIcon, Pos alignement, Pane pane) {
        Button button = new Button();

        button.setTooltip(tooltip);
        button.getStyleClass().add(styleClass);
        button.setText(displayText);
        button.setMaxWidth(width);
        button.setMaxHeight(height);
        button.setGraphic(displayIcon);
        StackPane.setAlignment(button, alignement);
        button.setTranslateX(posX);
        button.setTranslateY(posY);

        if (listener) {
            button.setOnMouseEntered(e -> JuiInitialization.getFxStage().getScene().setCursor(Cursor.HAND));
            button.setOnMouseExited(e -> JuiInitialization.getFxStage().getScene().setCursor(Cursor.DEFAULT));
        } else {
            button.setDisable(true);
            button.setStyle("-fx-opacity: 1.0;");
        }

        if (pane != null) pane.getChildren().add(button);
        return button;
    }
}

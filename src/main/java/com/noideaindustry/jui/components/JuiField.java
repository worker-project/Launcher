package com.noideaindustry.jui.components;

import javafx.geometry.Pos;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

public abstract class JuiField {
    public static TextField createTextField(double posX, double posY, double width, double height, String promptText, String styleClass, Pos alignement, Pane pane) {
        TextField textField = new TextField();

        if (alignement != null) textField.setAlignment(alignement);
        textField.setPromptText(promptText);
        textField.setText(promptText);
        textField.setMaxWidth(width);
        textField.setMaxHeight(height);
        textField.setTranslateX(posX);
        textField.setTranslateY(posY);
        textField.getStyleClass().add(styleClass);

        pane.getChildren().add(textField);
        return textField;
    }

    public static PasswordField createPasswordField(double posX, double posY, double width, double height, String promptText, String styleClass, Pane pane) {
        PasswordField passwordField = new PasswordField();

        passwordField.setPromptText(promptText);
        passwordField.setText(promptText);
        passwordField.setMaxWidth(width);
        passwordField.setMaxHeight(height);
        passwordField.setTranslateX(posX);
        passwordField.setTranslateY(posY);
        passwordField.getStyleClass().add(styleClass);

        pane.getChildren().add(passwordField);
        return passwordField;
    }
}

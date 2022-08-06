package com.noideaindustry.jui.interfaces;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public abstract class JuiLabel {
    public static Label createLabel(double posX, double posY, String displayText, String styleClass, Pane pane) {
        return createLabel(posX, posY, displayText, null, styleClass, Pos.TOP_CENTER, pane);
    }

    public static Label createLabel(double posX, double posY, String displayText, FontAwesomeIconView displayIcon, String styleClass, Pos alignement, Pane pane) {
        Label label = new Label(displayText);

        label.getStyleClass().add(styleClass);
        StackPane.setAlignment(label, alignement);
        label.setTranslateX(posX);
        label.setTranslateY(posY);
        label.setGraphic(displayIcon);

        if (pane != null) pane.getChildren().add(label);
        return label;
    }
}

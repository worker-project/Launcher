package com.noideaindustry.jui.components;

import com.noideaindustry.jui.JuiInitialization;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import javafx.scene.Cursor;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public abstract class JuiIcon {
    public static FontAwesomeIconView createAwesomeIcon(double posX, double posY, FontAwesomeIcon displayIcon, String size, String styleClass, Color color, Pane pane) {
        return createAwesomeIcon(posX, posY, displayIcon, size, styleClass, color, pane, Cursor.DEFAULT);
    }

    public static FontAwesomeIconView createAwesomeIcon(double posX, double posY, FontAwesomeIcon displayIcon, String size, Color color, Pane pane) {
        return createAwesomeIcon(posX, posY, displayIcon, size, null, color, pane, Cursor.DEFAULT);
    }

    public static FontAwesomeIconView createAwesomeIcon(double posX, double posY, FontAwesomeIcon displayIcon, String size) {
        return createAwesomeIcon(posX, posY, displayIcon, size, null, Color.WHITE, null, Cursor.DEFAULT);
    }

    public static FontAwesomeIconView createAwesomeIcon(double posX, double posY, FontAwesomeIcon displayIcon, String size, String styleClass, Color color, Pane pane, Cursor hoveredCursor) {
        FontAwesomeIconView iconView = new FontAwesomeIconView(displayIcon);

        iconView.getStyleClass().add(styleClass);
        iconView.setFill(color);
        iconView.setSize(size);
        iconView.setTranslateX(posX);
        iconView.setTranslateY(posY);

        if (hoveredCursor != Cursor.DEFAULT) {
            iconView.setOnMouseEntered(e -> JuiInitialization.getFxStage().getScene().setCursor(Cursor.HAND));
            iconView.setOnMouseExited(e -> JuiInitialization.getFxStage().getScene().setCursor(Cursor.DEFAULT));
        }

        if (pane != null) pane.getChildren().add(iconView);
        return iconView;
    }

    public static MaterialDesignIconView createDesignIcon(double posX, double posY, MaterialDesignIcon displayIcon, String size, String styleClass, Color color, Pane pane) {
        return createDesignIcon(posX, posY, displayIcon, size, styleClass, color, pane, Cursor.DEFAULT);
    }

    public static MaterialDesignIconView createDesignIcon(double posX, double posY, MaterialDesignIcon displayIcon, String size, Color color, Pane pane) {
        return createDesignIcon(posX, posY, displayIcon, size, null, color, pane, Cursor.DEFAULT);
    }

    public static MaterialDesignIconView createDesignIcon(double posX, double posY, MaterialDesignIcon displayIcon, String size, String styleClass, Color color, Pane pane, Cursor hoveredCursor) {
        MaterialDesignIconView iconView = new MaterialDesignIconView(displayIcon);

        iconView.getStyleClass().add(styleClass);
        iconView.setFill(color);
        iconView.setSize(size);
        iconView.setTranslateX(posX);
        iconView.setTranslateY(posY);

        if (hoveredCursor != Cursor.DEFAULT) {
            iconView.setOnMouseEntered(e -> JuiInitialization.getFxStage().getScene().setCursor(Cursor.HAND));
            iconView.setOnMouseExited(e -> JuiInitialization.getFxStage().getScene().setCursor(Cursor.DEFAULT));
        }

        if (pane != null) pane.getChildren().add(iconView);
        return iconView;
    }
}

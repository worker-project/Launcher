package com.noideaindustry.jui.components;

import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

import static com.noideaindustry.jui.geometry.JuiRectangle.createRoundedRectangle;
import static com.workerai.launcher.utils.LauncherInfos.DARK_BLACK;
import static javafx.scene.control.ScrollPane.ScrollBarPolicy.ALWAYS;
import static javafx.scene.control.ScrollPane.ScrollBarPolicy.NEVER;

public abstract class JuiPane {
    public static ScrollPane createScrollPane(double posX, double posY, double width, double height, String styleClass, Pane content, Pane pane) {
        return createScrollPane(posX, posY, width, height, styleClass, ALWAYS, NEVER, false, true, true, content, Pos.CENTER_RIGHT, pane);
    }
    public static ScrollPane createScrollPane(double posX, double posY, double width, double height, String styleClass, ScrollPane.ScrollBarPolicy vPolicy, ScrollPane.ScrollBarPolicy hPolicy, boolean fitHeight, boolean fitWidth, boolean pannable, Pane content, Pos alignement, Pane pane) {
        ScrollPane scrollPane = new ScrollPane();

        scrollPane.getStyleClass().add(styleClass);
        scrollPane.setMaxHeight(height);
        scrollPane.setMaxWidth(width);
        scrollPane.setVbarPolicy(vPolicy);
        scrollPane.setHbarPolicy(hPolicy);
        scrollPane.setPannable(pannable);
        scrollPane.setFitToHeight(fitHeight);
        scrollPane.setFitToWidth(fitWidth);
        scrollPane.setContent(content);
        StackPane.setAlignment(scrollPane, alignement);
        scrollPane.setTranslateX(posX);
        scrollPane.setTranslateY(posY);

        scrollPane.setOnScroll(event -> {
            if (event.getDeltaX() == 0 && event.getDeltaY() != 0) {
                scrollPane.setVvalue(scrollPane.getVvalue() - event.getDeltaY() / 350);
            }
        });

        pane.getChildren().add(scrollPane);
        return scrollPane;
    }

    public static StackPane createStackPane(double posX, double posY, double width, double height, Color color) {
        return createStackPane(posX, posY, width, height, 15d, 15d, false, null, null, color);
    }
    public static StackPane createStackPane(double posX, double posY, double width, double height, boolean concat) {
        return createStackPane(posX, posY, width, height, 15d, 15d, concat, null, null, DARK_BLACK);
    }
    public static StackPane createStackPane(double posX, double posY, double width, double height, boolean concat, Color color) {
        return createStackPane(posX, posY, width, height, 15d, 15d, concat, null, null, color);
    }
    public static StackPane createStackPane(double posX, double posY, double width, double height, String styleClass, Color color) {
        return createStackPane(posX, posY, width, height, 15d, 15d, false, styleClass, Pos.CENTER, color);
    }
    public static StackPane createStackPane(double posX, double posY, double width, double height, double arcHeight, double arcWidth, boolean concat, String styleClass, Pos alignement, Color color) {
        StackPane stackPane = new StackPane();

        if (concat) stackPane.setMaxSize(width, height);

        stackPane.setPrefSize(width, height);

        stackPane.setTranslateX(posX);
        stackPane.setTranslateY(posY);

        stackPane.getChildren().add(createRoundedRectangle(stackPane.getPrefWidth(), stackPane.getPrefHeight(), arcHeight, arcWidth, color));
        stackPane.getStyleClass().add(styleClass);

        if (!concat) {
            Region slip = new Region();
            slip.setMaxWidth(width);
            slip.setMaxHeight(height / 7);

            slip.getStyleClass().add("rectangle-border");
            slip.setTranslateY(220 * height / 600 * -1);
            stackPane.getChildren().add(slip);
        }

        if (alignement != null) StackPane.setAlignment(stackPane, alignement);
        return stackPane;
    }

    public static GridPane createGridPane(String styleClass) {
        return createGridPane(0d, 0d, 0d, 0d, 0d, 0d, styleClass, Color.TRANSPARENT);
    }
    public static GridPane createGridPane(double posX, double posY, double width, double height, double arcHeight, double arcWidth, String styleClass, Color color) {
        GridPane gridPane = new GridPane();

        gridPane.getStyleClass().add(styleClass);
        gridPane.setPrefSize(width, height);
        gridPane.setTranslateX(posX);
        gridPane.setTranslateY(posY);

        gridPane.getChildren().add(createRoundedRectangle(gridPane.getPrefWidth(), gridPane.getPrefHeight(), arcHeight, arcWidth, color));

        return gridPane;
    }
}

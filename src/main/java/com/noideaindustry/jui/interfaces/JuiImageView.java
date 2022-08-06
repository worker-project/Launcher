package com.noideaindustry.jui.interfaces;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public abstract class JuiImageView {
    public static ImageView createImageView(double posX, double posY, double width, double height, String displayUrl, Pane pane) {
        return createImageView(posX, posY, width, height, true, displayUrl, null, pane);
    }

    public static ImageView createImageView(double posX, double posY, double width, double height, String displayUrl, Pos alignement, Pane pane) {
        return createImageView(posX, posY, width, height, true, displayUrl, alignement, pane);
    }

    public static ImageView createImageView(double posX, double posY, double width, double height, boolean ratio, String displayUrl, Pos alignement, Pane pane) {
        ImageView imageView = new ImageView(new Image(displayUrl));

        imageView.setPreserveRatio(ratio);
        imageView.setFitHeight(height);
        imageView.setFitWidth(width);
        StackPane.setAlignment(imageView, alignement);
        imageView.setTranslateX(posX);
        imageView.setTranslateY(posY);

        if (pane != null) pane.getChildren().add(imageView);
        return imageView;
    }
}

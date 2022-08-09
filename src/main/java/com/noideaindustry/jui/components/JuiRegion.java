package com.noideaindustry.jui.components;

import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;

public abstract class JuiRegion {
    public static Region createRegion(double posX, double posY, String styleClass, Pane pane) {
        return createRegion(posX, posY, 0, 0, styleClass, pane);
    }

    public static Region createRegion(double posX, double posY, double width, double height, String styleClass, Pane pane) {
        Region region = new Region();
        region.getStyleClass().add(styleClass);
        region.setTranslateX(posX);
        region.setTranslateY(posY);

        region.setMaxWidth(width);
        region.setMaxHeight(height);

        if (pane != null) pane.getChildren().add(region);
        return region;
    }
}

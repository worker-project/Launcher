package com.noideaindustry.jui;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import static com.noideaindustry.jui.JuiGeometry.JuiRectangle.createRoundedRectangle;
import static javafx.scene.control.ScrollPane.ScrollBarPolicy;

public abstract class JuiInterface {

    public abstract static class JuiIcon {
        public static FontAwesomeIconView createFontIcon(double posX, double posY, FontAwesomeIcon displayIcon, String size, String styleClass, Color color, Pane pane) {
            return createFontIcon(posX, posY, displayIcon, size, styleClass, color, pane, Cursor.DEFAULT);
        }

        public static FontAwesomeIconView createFontIcon(double posX, double posY, FontAwesomeIcon displayIcon, String size, String styleClass, Color color, Pane pane, Cursor hoveredCursor) {
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

    public abstract static class JuiPane {
        public static ScrollPane createScrollPane(double posX, double posY, double width, double height, String styleClass, ScrollBarPolicy vPolicy, ScrollBarPolicy hPolicy, boolean fitHeight, boolean fitWidth, boolean pannable, Pane content, Pos alignement, Pane pane) {
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

        public static StackPane createStackPane(double posX, double posY, double width, double height, double arcHeight, double arcWidth, boolean concat, String styleClass, Pos alignement, Color color) {
            StackPane stackPane = new StackPane();

            if (concat) stackPane.setMaxSize(width, height);

            stackPane.setPrefSize(width, height);

            stackPane.setTranslateX(posX);
            stackPane.setTranslateY(posY);

            stackPane.getChildren().add(createRoundedRectangle(0, 0, stackPane.getPrefWidth(), stackPane.getPrefHeight(), arcHeight, arcWidth, color));
            stackPane.getStyleClass().add(styleClass);

            if(!concat) {
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

        public static GridPane createGridPane(double posX, double posY, double width, double height, double arcHeight, double arcWidth, String styleClass, Color color) {
            GridPane gridPane = new GridPane();

            gridPane.getStyleClass().add(styleClass);
            gridPane.setPrefSize(width, height);
            gridPane.setTranslateX(posX);
            gridPane.setTranslateY(posY);

            gridPane.getChildren().add(createRoundedRectangle(0, 0, gridPane.getPrefWidth(), gridPane.getPrefHeight(), arcHeight, arcWidth, color));

            return gridPane;
        }
    }

    public abstract static class JuiField {
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

    public abstract static class JuiButton {
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

        public static Button createMaterialButton(double posX, double posY, double width, double height, String displayText, String styleClass, Tooltip tooltip, MaterialDesignIconView displayIcon, Pos alignement, Pane pane) {
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

    public static Tooltip createTooltip(String displayText, FontAwesomeIconView displayIcon, Duration showDelay, Duration hideDelay) {
        Tooltip tooltip = new Tooltip(displayText);

        tooltip.setGraphic(displayIcon);

        tooltip.setShowDelay(showDelay);
        tooltip.setHideDelay(hideDelay);

        return tooltip;
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

    public static HBox createHBox(double spacing, double topInsets, double rightInsets, double bottomInsets, double leftInsets, Pane pane) {
        HBox hBox = new HBox(spacing);
        hBox.setPadding(new Insets(topInsets, rightInsets, bottomInsets, leftInsets));

        if (pane != null) pane.getChildren().add(hBox);
        return hBox;
    }
}

package com.workerai.launcher.ui.panels.pages.settings;

import com.workerai.launcher.App;
import com.workerai.launcher.ui.panels.pages.Settings;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import fr.theshark34.openlauncherlib.util.Saver;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import static java.awt.MouseInfo.getPointerInfo;

public class Display extends Settings {
    public void init(Rectangle displayContainer, GridPane pane, Saver saver) {
        //region [After Launch Sub Container]
        Label afterLaunchLabel = new Label();
        afterLaunchLabel.getStyleClass().add("afterLaunch-label");
        afterLaunchLabel.setText("After Launch");
        setCenterH(afterLaunchLabel);
        setTop(afterLaunchLabel);
        afterLaunchLabel.setTranslateX(displayContainer.getTranslateX() - 440);
        afterLaunchLabel.setTranslateY(displayContainer.getTranslateY() + 15d);
        pane.getChildren().add(afterLaunchLabel);

        FontAwesomeIconView afterLaunchIcon = new FontAwesomeIconView(FontAwesomeIcon.PLAY_CIRCLE);
        afterLaunchIcon.setFill(Color.WHITE);
        afterLaunchIcon.setSize("25px");
        setCenterH(afterLaunchIcon);
        setTop(afterLaunchIcon);
        afterLaunchIcon.setTranslateX(afterLaunchLabel.getTranslateX() - 90d);
        afterLaunchIcon.setTranslateY(afterLaunchLabel.getTranslateY() + 5d);
        pane.getChildren().add(afterLaunchIcon);

        Label afterLaunchSubLabel = new Label();
        afterLaunchSubLabel.getStyleClass().add("afterLaunch-subLabel");
        afterLaunchSubLabel.setText("Select which action your launcher should take on launch");
        setTop(afterLaunchSubLabel);
        setCenterH(afterLaunchSubLabel);
        afterLaunchSubLabel.setTranslateX(afterLaunchLabel.getTranslateX() - 15);
        afterLaunchSubLabel.setTranslateY(afterLaunchLabel.getTranslateY() + 35d);
        pane.getChildren().add(afterLaunchSubLabel);

        Button hideAfterLaunch = new Button();
        Button keepAfterLaunch = new Button();

        // Hide Launcher after Launch
        hideAfterLaunch.getStyleClass().add("afterLaunch-button");
        hideAfterLaunch.setText("Hide Launcher");
        hideAfterLaunch.setMaxWidth(250);
        hideAfterLaunch.setMaxHeight(30d);
        setTop(hideAfterLaunch);
        setCenterH(hideAfterLaunch);
        hideAfterLaunch.setTranslateX(afterLaunchSubLabel.getTranslateX());
        hideAfterLaunch.setTranslateY(afterLaunchSubLabel.getTranslateY() + 35d);
        pane.getChildren().add(hideAfterLaunch);

        hideAfterLaunch.setOnMouseEntered(e -> App.getInstance().getPanelManager().getStage().getScene().setCursor(Cursor.HAND));
        hideAfterLaunch.setOnMouseExited(e -> App.getInstance().getPanelManager().getStage().getScene().setCursor(Cursor.DEFAULT));
        hideAfterLaunch.setOnMouseClicked(e -> {
            App.getInstance().getSettingsManager().getSaver().set("HideAfterLaunch", String.valueOf(true));
            App.getInstance().getSettingsManager().getSaver().set("KeepAfterLaunch", String.valueOf(false));
            setButtonProperty(hideAfterLaunch, keepAfterLaunch);
        });

        FontAwesomeIconView hideIcon = new FontAwesomeIconView(FontAwesomeIcon.COMPRESS);
        hideIcon.setFill(Color.WHITE);
        hideIcon.setSize("20px");
        hideIcon.setTranslateX(-4d);
        hideAfterLaunch.setGraphic(hideIcon);

        // Keep Launcher after Launch
        keepAfterLaunch.setText("Keep Launcher Open");
        keepAfterLaunch.getStyleClass().add("afterLaunch-button");
        setCanTakeAllSize(keepAfterLaunch);
        keepAfterLaunch.setMaxWidth(250);
        keepAfterLaunch.setMaxHeight(30d);
        setTop(keepAfterLaunch);
        setCenterH(keepAfterLaunch);
        keepAfterLaunch.setTranslateX(afterLaunchSubLabel.getTranslateX());
        keepAfterLaunch.setTranslateY(hideAfterLaunch.getTranslateY() + 45d);
        pane.getChildren().add(keepAfterLaunch);

        FontAwesomeIconView keepIcon = new FontAwesomeIconView(FontAwesomeIcon.EXPAND);
        keepIcon.setFill(Color.WHITE);
        keepIcon.setSize("18px");
        keepIcon.setTranslateX(-4d);
        keepAfterLaunch.setGraphic(keepIcon);

        Tooltip tooltip = new Tooltip("After the game is launched your launcher will remain open.");
        tooltip.setShowDelay(Duration.ZERO);
        tooltip.setHideDelay(Duration.ZERO);
        FontAwesomeIconView tooltipIcon = new FontAwesomeIconView(FontAwesomeIcon.INFO_CIRCLE);
        tooltipIcon.setSize("20px");
        tooltipIcon.setFill(Color.WHITE);
        tooltip.setGraphic(tooltipIcon);
        keepAfterLaunch.setOnMouseMoved(e -> {
            tooltip.setX(getPointerInfo().getLocation().x);
            tooltip.setY(getPointerInfo().getLocation().y);
        });
        keepAfterLaunch.setTooltip(tooltip);

        keepAfterLaunch.setOnMouseEntered(e -> App.getInstance().getPanelManager().getStage().getScene().setCursor(Cursor.HAND));
        keepAfterLaunch.setOnMouseExited(e -> App.getInstance().getPanelManager().getStage().getScene().setCursor(Cursor.DEFAULT));
        keepAfterLaunch.setOnMouseClicked(e -> {
            App.getInstance().getSettingsManager().getSaver().set("KeepAfterLaunch", String.valueOf(true));
            App.getInstance().getSettingsManager().getSaver().set("HideAfterLaunch", String.valueOf(false));
            setButtonProperty(keepAfterLaunch, hideAfterLaunch);
        });

        if(saver.get("KeepAfterLaunch").equals(String.valueOf(true))) {
            saver.set("KeepAfterLaunch", "true");
            saver.set("HideAfterLaunch", "false");
            setButtonProperty(keepAfterLaunch, hideAfterLaunch);
        } else {
            saver.set("HideAfterLaunch", "true");
            saver.set("KeepAfterLaunch", "false");
            setButtonProperty(hideAfterLaunch, keepAfterLaunch);
        }
        //endregion
    }

    private void setButtonProperty(Button keepButton, Button hideButton) {
            keepButton.getStyleClass().add("afterLaunch-button-active");
            hideButton.getStyleClass().removeAll("afterLaunch-button-active");
    }
}

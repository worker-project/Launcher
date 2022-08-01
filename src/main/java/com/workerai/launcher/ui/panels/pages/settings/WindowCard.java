package com.workerai.launcher.ui.panels.pages.settings;

import com.workerai.launcher.App;
import com.workerai.launcher.ui.panels.pages.Settings;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import fr.theshark34.openlauncherlib.util.Saver;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import static com.noideaindustry.jui.JuiInterface.JuiButton.createFontButton;
import static com.noideaindustry.jui.JuiInterface.*;
import static java.awt.MouseInfo.getPointerInfo;

public class WindowCard extends Settings {
    public static void create(GridPane container, Saver saver, StackPane card) {
        container.getChildren().add(card);

        createLabel(0, 20d, "Launcher Window", JuiIcon.createFontIcon(-4d, 0, FontAwesomeIcon.PLAY_CIRCLE, "25px", null, Color.WHITE, card), "afterLaunch-label", Pos.TOP_CENTER, card);
        createLabel(0, 50d, "Select which action your launcher should take on launch", null, "afterLaunch-subLabel", Pos.TOP_CENTER, card);

        FontAwesomeIconView tooltipIcon = JuiIcon.createFontIcon(0d, 0d, FontAwesomeIcon.INFO_CIRCLE, "20px", null, Color.WHITE, (StackPane) null);
        Tooltip tooltip = createTooltip("After the game is launched your launcher will remain open.", tooltipIcon, Duration.ZERO, Duration.ZERO);

        FontAwesomeIconView keepIcon = JuiIcon.createFontIcon(0, 0, FontAwesomeIcon.EXPAND, "18px", null, Color.WHITE, card);
        Button keepButton = createFontButton(0, -70d, 250d, 30d, "Keep Launcher Open", "afterLaunch-button", tooltip, keepIcon, Pos.BOTTOM_CENTER, card);

        FontAwesomeIconView hideIcon = JuiIcon.createFontIcon(0, 0, FontAwesomeIcon.COMPRESS, "18px", null, Color.WHITE, card);
        Button hideButton = createFontButton(0, -25d, 250d, 30d, "Hide Launcher", "afterLaunch-button", null, hideIcon, Pos.BOTTOM_CENTER, card);

        keepButton.setOnMouseClicked(e -> {
            App.getInstance().getSettingsManager().getSaver().set("KeepAfterLaunch", String.valueOf(true));
            App.getInstance().getSettingsManager().getSaver().set("HideAfterLaunch", String.valueOf(false));
            setButtonProperty(keepButton, hideButton);
        });

        keepButton.setOnMouseMoved(e -> {
            tooltip.setX(getPointerInfo().getLocation().x);
            tooltip.setY(getPointerInfo().getLocation().y);
        });

        hideButton.setOnMouseClicked(e -> {
            App.getInstance().getSettingsManager().getSaver().set("HideAfterLaunch", String.valueOf(true));
            App.getInstance().getSettingsManager().getSaver().set("KeepAfterLaunch", String.valueOf(false));
            setButtonProperty(hideButton, keepButton);
        });

        if (saver.get("KeepAfterLaunch").equals(String.valueOf(true))) {
            saver.set("KeepAfterLaunch", "true");
            saver.set("HideAfterLaunch", "false");
            setButtonProperty(keepButton, hideButton);
        } else {
            saver.set("HideAfterLaunch", "true");
            saver.set("KeepAfterLaunch", "false");
            setButtonProperty(hideButton, keepButton);
        }
    }

    private static void setButtonProperty(Button active, Button inactive) {
        active.getStyleClass().add("afterLaunch-button-active");
        inactive.getStyleClass().removeAll("afterLaunch-button-active");
    }
}

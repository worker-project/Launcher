package com.workerai.launcher.ui.panels.settings;

import com.workerai.launcher.WorkerLauncher;
import com.workerai.launcher.ui.panels.pages.Settings;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import fr.theshark34.openlauncherlib.util.Saver;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import static com.noideaindustry.jui.interfaces.JuiButton.createFontButton;
import static com.noideaindustry.jui.interfaces.JuiIcon.createAwesomeIcon;
import static com.noideaindustry.jui.interfaces.JuiLabel.createLabel;
import static com.noideaindustry.jui.interfaces.JuiToolTip.createTooltip;
import static com.workerai.launcher.utils.LauncherInfos.WHITE;
import static java.awt.MouseInfo.getPointerInfo;

public class WindowCard extends Settings {
    public static void create(GridPane container, Saver saver, StackPane card) {
        container.getChildren().add(card);

        createLabel(0, 20d, "Launcher Window", createAwesomeIcon(-4d, 0, FontAwesomeIcon.PLAY_CIRCLE, "25px", WHITE, card), "afterLaunch-label", Pos.TOP_CENTER, card);
        createLabel(0, 50d, "Select the window behavior after launching Minecraft", null, "afterLaunch-subLabel", Pos.TOP_CENTER, card);

        FontAwesomeIconView tooltipIcon = createAwesomeIcon(0d, 0d, FontAwesomeIcon.INFO_CIRCLE, "20px", WHITE, null);
        Tooltip tooltip = createTooltip("After the game is launched your launcher will remain open.", tooltipIcon);

        FontAwesomeIconView keepIcon = createAwesomeIcon(0, 0, FontAwesomeIcon.EXPAND, "18px", WHITE, card);
        Button keepButton = createFontButton(0, -90d, 250d, 30d, "Keep Launcher Open", "afterLaunch-button", tooltip, keepIcon, Pos.BOTTOM_CENTER, card);

        FontAwesomeIconView hideIcon = createAwesomeIcon(0, 0, FontAwesomeIcon.COMPRESS, "18px", WHITE, card);
        Button hideButton = createFontButton(0, -45d, 250d, 30d, "Hide Launcher", "afterLaunch-button", null, hideIcon, Pos.BOTTOM_CENTER, card);

        keepButton.setOnMouseClicked(e -> {
            setButtonProperty(keepButton, hideButton);
            WorkerLauncher.getInstance().getSettingsManager().getSaver().set("KeepAfterLaunch", String.valueOf(true));
            WorkerLauncher.getInstance().getSettingsManager().getSaver().set("HideAfterLaunch", String.valueOf(false));
        });

        keepButton.setOnMouseMoved(e -> {
            tooltip.setX(getPointerInfo().getLocation().x);
            tooltip.setY(getPointerInfo().getLocation().y);
        });

        hideButton.setOnMouseClicked(e -> {
            setButtonProperty(hideButton, keepButton);
            WorkerLauncher.getInstance().getSettingsManager().getSaver().set("HideAfterLaunch", String.valueOf(true));
            WorkerLauncher.getInstance().getSettingsManager().getSaver().set("KeepAfterLaunch", String.valueOf(false));
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

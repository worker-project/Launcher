package com.workerai.launcher.ui.panels.settings;

import com.workerai.launcher.WorkerLauncher;
import com.workerai.launcher.ui.panels.pages.Settings;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import fr.theshark34.openlauncherlib.util.Saver;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.noideaindustry.jui.components.JuiButton.createFontButton;
import static com.noideaindustry.jui.components.JuiField.createTextField;
import static com.noideaindustry.jui.components.JuiIcon.createAwesomeIcon;
import static com.noideaindustry.jui.components.JuiLabel.createLabel;
import static com.noideaindustry.jui.components.JuiPane.createStackPane;
import static com.noideaindustry.jui.components.JuiRegion.createRegion;
import static com.workerai.launcher.utils.LauncherInfos.DARK_BLACK;
import static com.workerai.launcher.utils.LauncherInfos.WHITE;

public class ResolutionCard extends Settings {
    private static final List<Node> nodes = new ArrayList<>();
    private static boolean isResolutionsOpen = false;

    public static void create(Pane container, Saver saver, StackPane card, Pane overlay) {
        container.getChildren().add(card);

        createLabel(0, 20d, "Game Resolution", createAwesomeIcon(-4d, 0, FontAwesomeIcon.DESKTOP, "25px", WHITE, card), "afterLaunch-label", Pos.TOP_CENTER, card);
        createLabel(0, 50d, "Enter a resolution to launch your game in", null, "afterLaunch-subLabel", Pos.TOP_CENTER, card);

        createLabel(-70d, -105d, "Width", createAwesomeIcon(-3d, -1d, FontAwesomeIcon.TEXT_WIDTH, "15px", WHITE, card), "ratio-displayLabel", Pos.BOTTOM_CENTER, card);
        createRegion(-70d, 17.5d, 100, 40, "ratio-box", card);
        TextField widthField = createTextField(-70d, 17.5d, 90d, 35d, saver.get("LaunchWidth"), "ratio-promptText", Pos.BOTTOM_CENTER, card);
        widthField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (WorkerLauncher.isDebugMode()) return;

            if (!newValue.matches("\\d*")) {
                widthField.setText(newValue.replaceAll("[^\\d]", ""));
            } else if (Objects.equals(newValue, "") || newValue.length() > 5) {
                saver.set("LaunchWidth", saver.get("LaunchWidth"));
                widthField.setText(saver.get("LaunchWidth"));
            } else {
                saver.set("LaunchWidth", newValue);
            }
        });

        createAwesomeIcon(0d, 17.5d, FontAwesomeIcon.TIMES, "20px", WHITE, card);

        createLabel(70d, -105d, "Height", createAwesomeIcon(-3d, -1d, FontAwesomeIcon.TEXT_HEIGHT, "15px", WHITE, card), "ratio-displayLabel", Pos.BOTTOM_CENTER, card);
        createRegion(70d, 17.5d, 100, 40, "ratio-box", card);
        TextField heightField = createTextField(70d, 17.5d, 90d, 35d, saver.get("LaunchHeight"), "ratio-promptText", Pos.BOTTOM_CENTER, card);
        heightField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                heightField.setText(newValue.replaceAll("[^\\d]", ""));
            } else if (Objects.equals(newValue, "") || newValue.length() > 5) {
                saver.set("LaunchHeight", saver.get("LaunchHeight"));
                heightField.setText(saver.get("LaunchHeight"));
            } else {
                saver.set("LaunchHeight", newValue);
            }
        });

        FontAwesomeIconView presetsIcon = createAwesomeIcon(0d, 0d, FontAwesomeIcon.ANCHOR, "15px", WHITE, card);
        Button presetsButton = createFontButton(-70d, -25d, 110d, 25d, "Presets", "directory-button", null, presetsIcon, Pos.BOTTOM_CENTER, card);
        presetsButton.setOnMouseClicked(e -> {
            if (isResolutionsOpen) hideResolutions(overlay);
            else showResolutions(overlay);
        });

        FontAwesomeIconView renderIcon = createAwesomeIcon(0d, 0d, FontAwesomeIcon.EYE, "15px", WHITE, card);
        Button renderButton = createFontButton(70d, -25d, 110d, 25d, "Soon", "directory-button", null, renderIcon, Pos.BOTTOM_CENTER, card);
        renderButton.setOnMouseClicked(e -> System.out.println("Everything has a time..., wait a bit more."));

        createResolutions(overlay, widthField, heightField);
    }

    private static void createChoices(Label presetLabel, String[] presetChoice, Button[] presetsButtons, TextField widthField, TextField heightField) {
        for (int i = 0; i < presetChoice.length; i++) {
            int finalI = i;
            presetsButtons[i] = createFontButton(70d, -25d, 100d, 25d, String.valueOf(presetChoice[i]), "presets-button", null, null, Pos.BOTTOM_CENTER, null);
            presetsButtons[i].setOnMouseClicked(e -> {
                String[] split = presetChoice[finalI].split("x");
                WorkerLauncher.getInstance().getSettingsManager().getSaver().set("LaunchWidth", split[0]);
                widthField.setText(split[0]);

                WorkerLauncher.getInstance().getSettingsManager().getSaver().set("LaunchHeight", split[1]);
                heightField.setText(split[1]);
            });

            if (i >= 1) {
                presetsButtons[i].setTranslateX(presetsButtons[i - 1].getTranslateX());
                presetsButtons[i].setTranslateY(presetsButtons[i - 1].getTranslateY() + 40d);
            } else {
                presetsButtons[i].setTranslateX(presetLabel.getTranslateX());
                presetsButtons[i].setTranslateY(presetLabel.getTranslateY() + 34d);
            }

            nodes.add(presetsButtons[i]);
        }
    }

    private static void createResolutions(Pane overlay, TextField widthField, TextField heightField) {
        final String[] _4_3_ = {"640x480", "800x600", "1024x768", "1280x768", "1280x1024",};
        final String[] _16_9_ = {"854x480", "1280x720", "1360x768", "1600x900", "1920x1080",};
        final String[] _16_10_ = {"1280x768", "1280x800", "1440x900", "1600x1024", "1680x1050",};

        StackPane holder = createStackPane(470d, 300d, 460d, 300d, "preset-box", DARK_BLACK);
        nodes.add(holder);

        nodes.add(createLabel(0, 0d, "Resolution Presets", null, "afterLaunch-label", Pos.TOP_CENTER, null));
        nodes.add(createAwesomeIcon(0d, 0d, FontAwesomeIcon.DESKTOP, "25px", WHITE, null));
        nodes.add(createLabel(0, 0d, "Select a preset resolution for your Minecraft window", null, "afterLaunch-subLabel", Pos.TOP_CENTER, null));

        nodes.add(createRegion(0d, 40d, "presets-box", null));
        Label preset43Label = createLabel(120d, 0, "4:3 Ratio", null, "ratio-columnLabels", Pos.TOP_CENTER, null);
        createChoices(preset43Label, _4_3_, new Button[_4_3_.length], widthField, heightField);

        nodes.add(createRegion(0d, 40d, "presets-box", null));
        Label preset169Label = createLabel(0, 0, "16:9 Ratio", null, "ratio-columnLabels", Pos.TOP_CENTER, null);
        createChoices(preset169Label, _16_9_, new Button[_16_9_.length], widthField, heightField);

        nodes.add(createRegion(0d, 40d, "presets-box", null));
        Label preset1610Label = createLabel(-120d, 0, "16:10 Ratio", null, "ratio-columnLabels", Pos.TOP_CENTER, null);
        createChoices(preset1610Label, _16_10_, new Button[_16_10_.length], widthField, heightField);

        FontAwesomeIconView closeIcon = createAwesomeIcon(150d, 0d, FontAwesomeIcon.CLOSE, "25px", "close-button", Color.INDIANRED, null, Cursor.HAND);
        //Button closeButton = createFontButton(150d, 0d, 110d, 25d, null, "close-button", null, closeIcon, Pos.TOP_CENTER, null);
        nodes.add(closeIcon);
        closeIcon.setOnMouseClicked(e -> hideResolutions(overlay));
    }

    private static void showResolutions(Pane pane) {
        isResolutionsOpen = true;
        pane.getChildren().addAll(nodes);
        pane.setManaged(true);
        pane.setVisible(true);
    }

    private static void hideResolutions(Pane pane) {
        isResolutionsOpen = false;
        pane.getChildren().removeAll(nodes);
        pane.setManaged(false);
        pane.setVisible(false);
    }
}

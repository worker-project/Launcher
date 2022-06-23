package com.workerai.launcher.ui.panels.pages.settings;

import com.workerai.launcher.App;
import com.workerai.launcher.ui.panels.pages.Settings;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import fr.theshark34.openlauncherlib.util.Saver;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Resolution extends Settings {
    private List<Label> labels = new ArrayList<>();
    private List<Rectangle> rectangles = new ArrayList<>();
    private List<FontAwesomeIconView> icons = new ArrayList<>();
    private List<Region> regions = new ArrayList<>();
    private List<TextField> textFields = new ArrayList<>();
    private List<Button> buttons = new ArrayList<>();

    private boolean isOpen = false;
    private TextField heightText = new TextField();
    private TextField widthText = new TextField();

    public void init(Rectangle resolutionContainer, GridPane pane, Saver saver) {
        //region [Resolution Sub Container]
        Label resolutionLabel = new Label();
        resolutionLabel.getStyleClass().add("afterLaunch-label");
        resolutionLabel.setText("Resolution");
        setCenterH(resolutionLabel);
        setTop(resolutionLabel);
        resolutionLabel.setTranslateX(resolutionContainer.getTranslateX() - 440d);
        resolutionLabel.setTranslateY(resolutionContainer.getTranslateY() + 15d);
        pane.getChildren().add(resolutionLabel);

        FontAwesomeIconView resolutionIcon = new FontAwesomeIconView(FontAwesomeIcon.DESKTOP);
        resolutionIcon.setFill(Color.WHITE);
        resolutionIcon.setSize("25px");
        setCenterH(resolutionIcon);
        setTop(resolutionIcon);
        resolutionIcon.setTranslateX(resolutionLabel.getTranslateX() - 80d);
        resolutionIcon.setTranslateY(resolutionLabel.getTranslateY() + 7d);
        pane.getChildren().add(resolutionIcon);

        Label resolutionSubLabel = new Label();
        resolutionSubLabel.getStyleClass().add("afterLaunch-subLabel");
        resolutionSubLabel.setText("Enter a resolution to launch your game in");
        setTop(resolutionSubLabel);
        setCenterH(resolutionSubLabel);
        resolutionSubLabel.setTranslateX(resolutionLabel.getTranslateX() - 15d);
        resolutionSubLabel.setTranslateY(resolutionLabel.getTranslateY() + 35d);
        pane.getChildren().add(resolutionSubLabel);

        Label widthLabel = new Label();
        widthLabel.getStyleClass().add("ratio-displayLabel");
        widthLabel.setText("Width");
        setTop(widthLabel);
        setCenterH(widthLabel);
        widthLabel.setTranslateX(resolutionLabel.getTranslateX() - 77d);
        widthLabel.setTranslateY(resolutionSubLabel.getTranslateY() + 24d);
        pane.getChildren().add(widthLabel);

        FontAwesomeIconView widthIcon = new FontAwesomeIconView(FontAwesomeIcon.TEXT_WIDTH);
        widthIcon.setFill(Color.WHITE);
        widthIcon.setSize("15px");
        setCenterH(widthIcon);
        setTop(widthIcon);
        widthIcon.setTranslateX(widthLabel.getTranslateX() - 35d);
        widthIcon.setTranslateY(widthLabel.getTranslateY() + 1d);
        pane.getChildren().add(widthIcon);

        Region widthBox = new Region();
        widthBox.getStyleClass().add("ratio-box");
        setTop(widthBox);
        setCenterH(widthBox);
        widthBox.setTranslateX(widthLabel.getTranslateX() - 8d);
        widthBox.setTranslateY(widthLabel.getTranslateY() + 23d);
        pane.getChildren().add(widthBox);

        widthText.setPromptText(saver.get("LaunchWidth"));
        widthText.setAlignment(Pos.CENTER);
        widthText.getStyleClass().add("ratio-promptText");
        widthText.setMaxWidth(90d);
        widthText.setMaxHeight(35d);
        setTop(widthText);
        setCenterH(widthText);
        widthText.setTranslateX(widthBox.getTranslateX());
        widthText.setTranslateY(widthBox.getTranslateY() - 2d);
        pane.getChildren().add(widthText);

        widthText.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                widthText.setText(newValue.replaceAll("[^\\d]", ""));
            } else if(Objects.equals(newValue, "") || newValue.length() > 5) {
                saver.set("LaunchWidth", saver.get("LaunchWidth"));
                widthText.setText(saver.get("LaunchWidth"));
            } else {
                saver.set("LaunchWidth", newValue);
            }
        });

        FontAwesomeIconView xIcon = new FontAwesomeIconView(FontAwesomeIcon.TIMES);
        xIcon.setFill(Color.WHITE);
        xIcon.setSize("20px");
        setCenterH(xIcon);
        setTop(xIcon);
        xIcon.setTranslateX(widthBox.getTranslateX() + 68.5d);
        xIcon.setTranslateY(widthBox.getTranslateY() + 10d);
        pane.getChildren().add(xIcon);

        Label heightLabel = new Label();
        heightLabel.getStyleClass().add("ratio-displayLabel");
        heightLabel.setText("Height");
        setTop(heightLabel);
        setCenterH(heightLabel);
        heightLabel.setTranslateX(resolutionLabel.getTranslateX() + 60d);
        heightLabel.setTranslateY(resolutionSubLabel.getTranslateY() + 24d);
        pane.getChildren().add(heightLabel);

        FontAwesomeIconView heightIcon = new FontAwesomeIconView(FontAwesomeIcon.TEXT_HEIGHT);
        heightIcon.setFill(Color.WHITE);
        heightIcon.setSize("15px");
        setCenterH(heightIcon);
        setTop(heightIcon);
        heightIcon.setTranslateX(heightLabel.getTranslateX() - 35d);
        heightIcon.setTranslateY(heightLabel.getTranslateY() + 1d);
        pane.getChildren().add(heightIcon);

        Region heightBox = new Region();
        heightBox.getStyleClass().add("ratio-box");
        setTop(heightBox);
        setCenterH(heightBox);
        heightBox.setTranslateX(heightLabel.getTranslateX() - 8d);
        heightBox.setTranslateY(heightLabel.getTranslateY() + 23d);
        pane.getChildren().add(heightBox);

        heightText.setPromptText(saver.get("LaunchHeight"));
        heightText.setAlignment(Pos.CENTER);
        heightText.getStyleClass().add("ratio-promptText");
        heightText.setMaxWidth(90d);
        heightText.setMaxHeight(35d);
        setTop(heightText);
        setCenterH(heightText);
        heightText.setTranslateX(heightBox.getTranslateX());
        heightText.setTranslateY(heightBox.getTranslateY() - 2d);
        pane.getChildren().add(heightText);

        heightText.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                heightText.setText(newValue.replaceAll("[^\\d]", ""));
            } else if(Objects.equals(newValue, "") || newValue.length() > 5) {
                saver.set("LaunchHeight", saver.get("LaunchHeight"));
                heightText.setText(saver.get("LaunchHeight"));
            } else {
                saver.set("LaunchHeight", newValue);
            }
        });

        Button presetsButton = new Button("Presets");
        presetsButton.getStyleClass().add("directory-button");
        presetsButton.setMaxWidth(110d);
        presetsButton.setMaxHeight(25d);
        setTop(presetsButton);
        setCenterH(presetsButton);
        presetsButton.setTranslateX(widthBox.getTranslateX() + 0.5d);
        presetsButton.setTranslateY(widthBox.getTranslateY() + 50d);
        pane.getChildren().add(presetsButton);

        presetsButton.setOnMouseEntered(e -> App.getInstance().getPanelManager().getStage().getScene().setCursor(Cursor.HAND));
        presetsButton.setOnMouseExited(e -> App.getInstance().getPanelManager().getStage().getScene().setCursor(Cursor.DEFAULT));
        presetsButton.setOnMouseClicked(e -> {
            if(isOpen) deactivatePresets(pane);
            else activatePresets(pane);
        });

        FontAwesomeIconView presetsIcon = new FontAwesomeIconView(FontAwesomeIcon.ANCHOR);
        presetsButton.setGraphic(presetsIcon);
        presetsIcon.setFill(Color.WHITE);
        presetsIcon.setSize("15px");
        presetsIcon.setTranslateX(-4d);

        Button renderButton = new Button("Soon");
        renderButton.getStyleClass().add("directory-button");
        renderButton.setMaxWidth(110d);
        renderButton.setMaxHeight(25d);
        setTop(renderButton);
        setCenterH(renderButton);
        renderButton.setTranslateX(heightBox.getTranslateX() + 0.5d);
        renderButton.setTranslateY(heightBox.getTranslateY() + 50d);
        pane.getChildren().add(renderButton);

        renderButton.setOnMouseEntered(e -> App.getInstance().getPanelManager().getStage().getScene().setCursor(Cursor.HAND));
        renderButton.setOnMouseExited(e -> App.getInstance().getPanelManager().getStage().getScene().setCursor(Cursor.DEFAULT));
        renderButton.setOnMouseClicked(e -> {
            System.out.println("Everything has a time..., wait a bit more.");
        });

        FontAwesomeIconView renderIcon = new FontAwesomeIconView(FontAwesomeIcon.EYE);
        renderButton.setGraphic(renderIcon);
        renderIcon.setFill(Color.WHITE);
        renderIcon.setSize("15px");
        renderIcon.setTranslateX(-5d);

        createPresetContainer(pane);
        //endregion
    }

    private void createPresetsChoices(Label presetLabel, String[] presetChoice, Button[] presetsButtons) {
        for(int i = 0; i < presetChoice.length; i++) {
            int finalI = i;
            presetsButtons[i] = new Button();
            presetsButtons[i].getStyleClass().add("presets-button");
            presetsButtons[i].setText(String.valueOf(presetChoice[i]));
            presetsButtons[i].setMaxWidth(100d);
            presetsButtons[i].setMaxHeight(25d);
            setCenterH(presetsButtons[i]);
            setCenterV(presetsButtons[i]);

            presetsButtons[i].setOnMouseEntered(e -> App.getInstance().getPanelManager().getStage().getScene().setCursor(Cursor.HAND));
            presetsButtons[i].setOnMouseExited(e -> App.getInstance().getPanelManager().getStage().getScene().setCursor(Cursor.DEFAULT));
            presetsButtons[i].setOnMouseClicked(e -> {
                String[] split = presetChoice[finalI].split("x");
                App.getInstance().getSettingsManager().getSaver().set("LaunchWidth", split[0]);
                widthText.setText(split[0]);

                App.getInstance().getSettingsManager().getSaver().set("LaunchHeight", split[1]);
                heightText.setText(split[1]);
            });

            if(i >= 1) {
                presetsButtons[i].setTranslateX(presetsButtons[i-1].getTranslateX());
                presetsButtons[i].setTranslateY(presetsButtons[i-1].getTranslateY() + 40d);
            } else {
                presetsButtons[i].setTranslateX(presetLabel.getTranslateX());
                presetsButtons[i].setTranslateY(presetLabel.getTranslateY() + 34d);
            }

            buttons.add(presetsButtons[i]);
        }
    }

    private void createPresetContainer(GridPane pane) {
        System.out.println("Creating preset container.");

        final String[] presetChoice43 = { "640x480", "800x600", "1024x768", "1280x768", "1280x1024", };
        final String[] presetChoice169 = { "854x480", "1280x720", "1360x768", "1600x900", "1920x1080", };
        final String[] presetChoice1610 = { "1280x768", "1280x800", "1440x900", "1600x1024", "1680x1050", };

        Region ratioContainer = new Region();
        ratioContainer.getStyleClass().add("preset-box");
        setCenterH(ratioContainer);
        setCenterV(ratioContainer);
        regions.add(ratioContainer);

        Label resolutionPresetLabel = new Label();
        resolutionPresetLabel.setText("Resolution Presets");
        resolutionPresetLabel.getStyleClass().add("afterLaunch-label");
        setCenterH(resolutionPresetLabel);
        setCenterV(resolutionPresetLabel);
        resolutionPresetLabel.setTranslateX(ratioContainer.getTranslateX() + 15d);
        resolutionPresetLabel.setTranslateY(ratioContainer.getTranslateY() - 125d);
        labels.add(resolutionPresetLabel);

        FontAwesomeIconView resolutionPresetIcon = new FontAwesomeIconView(FontAwesomeIcon.DESKTOP);
        resolutionPresetIcon.setFill(Color.WHITE);
        resolutionPresetIcon.setSize("25px");
        setCenterH(resolutionPresetIcon);
        setCenterV(resolutionPresetIcon);
        resolutionPresetIcon.setTranslateX(resolutionPresetLabel.getTranslateX() - 125d);
        resolutionPresetIcon.setTranslateY(resolutionPresetLabel.getTranslateY() + 1d);
        icons.add(resolutionPresetIcon);

        Label resolutionPresetSubLabel = new Label();
        resolutionPresetSubLabel.setText("Select a preset resolution for your Minecraft window");
        resolutionPresetSubLabel.getStyleClass().add("afterLaunch-subLabel");
        setCenterH(resolutionPresetSubLabel);
        setCenterV(resolutionPresetSubLabel);
        resolutionPresetSubLabel.setTranslateX(ratioContainer.getTranslateX());
        resolutionPresetSubLabel.setTranslateY(resolutionPresetLabel.getTranslateY() + 23d);
        labels.add(resolutionPresetSubLabel);

        Region preset43Rec = new Region();
        preset43Rec.getStyleClass().add("presets-boxes");
        setCenterH(preset43Rec);
        setCenterV(preset43Rec);
        preset43Rec.setTranslateX(ratioContainer.getTranslateX() - 185d + 40d);
        preset43Rec.setTranslateY(40d);
        regions.add(preset43Rec);

        Label preset43Label = new Label();
        preset43Label.getStyleClass().add("ratio-columnLabels");
        preset43Label.setText("4:3 Ratio");
        setCenterH(preset43Label);
        setCenterV(preset43Label);
        preset43Label.setTranslateX(preset43Rec.getTranslateX());
        preset43Label.setTranslateY(preset43Rec.getTranslateY() - 115d);
        labels.add(preset43Label);

        Button[] presets43Buttons = new Button[presetChoice43.length];
        createPresetsChoices(preset43Label, presetChoice43, presets43Buttons);

        Region preset169Rec = new Region();
        preset169Rec.getStyleClass().add("presets-boxes");
        setCenterH(preset169Rec);
        setCenterV(preset169Rec);
        preset169Rec.setTranslateX(preset43Rec.getTranslateX() + 120d + 22.5d);
        preset169Rec.setTranslateY(preset43Rec.getTranslateY());
        regions.add(preset169Rec);

        Label preset169Label = new Label();
        preset169Label.getStyleClass().add("ratio-columnLabels");
        preset169Label.setText("16:9 Ratio");
        setCenterH(preset169Label);
        setCenterV(preset169Label);
        preset169Label.setTranslateX(preset169Rec.getTranslateX());
        preset169Label.setTranslateY(preset43Label.getTranslateY());
        labels.add(preset169Label);

        Button[] presets169Buttons = new Button[presetChoice169.length];
        createPresetsChoices(preset169Label, presetChoice169, presets169Buttons);

        Region preset1610Rec = new Region();
        preset1610Rec.getStyleClass().add("presets-boxes");
        setCenterH(preset1610Rec);
        setCenterV(preset1610Rec);
        preset1610Rec.setTranslateX(preset169Rec.getTranslateX() + 120d + 22.5d);
        preset1610Rec.setTranslateY(preset43Rec.getTranslateY());
        regions.add(preset1610Rec);

        Label preset1610Label = new Label();
        preset1610Label.getStyleClass().add("ratio-columnLabels");
        preset1610Label.setText("16:10 Ratio");
        setCenterH(preset1610Label);
        setCenterV(preset1610Label);
        preset1610Label.setTranslateX(preset1610Rec.getTranslateX());
        preset1610Label.setTranslateY(preset43Label.getTranslateY());
        labels.add(preset1610Label);

        Button[] presets1610Buttons = new Button[presetChoice1610.length];
        createPresetsChoices(preset1610Label, presetChoice1610, presets1610Buttons);

        FontAwesomeIconView closeIcon = new FontAwesomeIconView(FontAwesomeIcon.CLOSE);
        closeIcon.setFill(Color.INDIANRED);
        closeIcon.setSize("25px");
        setCenterH(closeIcon);
        setCenterV(closeIcon);
        closeIcon.setTranslateX(resolutionPresetLabel.getTranslateX() + 195d);
        closeIcon.setTranslateY(resolutionPresetLabel.getTranslateY() - 7d);
        icons.add(closeIcon);

        closeIcon.setOnMouseEntered(e -> App.getInstance().getPanelManager().getStage().getScene().setCursor(Cursor.HAND));
        closeIcon.setOnMouseExited(e -> App.getInstance().getPanelManager().getStage().getScene().setCursor(Cursor.DEFAULT));
        closeIcon.setOnMouseClicked(e -> {
            deactivatePresets(pane);
        });
    }

    private void activatePresets(GridPane pane) {
        isOpen = true;
        for(Region r : regions) {
            pane.getChildren().add(r);
        }
        for (Label l : labels) {
            pane.getChildren().add(l);
        }
        for (Rectangle rec : rectangles) {
            pane.getChildren().add(rec);
        }
        for (FontAwesomeIconView fa : icons) {
            pane.getChildren().add(fa);
        }
        for (TextField t : textFields) {
            pane.getChildren().add(t);
        }
        for (Button b : buttons) {
            pane.getChildren().add(b);
        }
    }
    private void deactivatePresets(GridPane pane) {
        isOpen = false;

        for(Region r : regions) {
            pane.getChildren().remove(r);
        }
        for (Label l : labels) {
            pane.getChildren().remove(l);
        }
        for (Rectangle rec : rectangles) {
            pane.getChildren().remove(rec);
        }
        for (FontAwesomeIconView fa : icons) {
            pane.getChildren().remove(fa);
        }
        for (TextField t : textFields) {
            pane.getChildren().remove(t);
        }
        for (Button b : buttons) {
            pane.getChildren().remove(b);
        }
    }
}

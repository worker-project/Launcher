package com.workerai.launcher.ui.panels.pages.settings;

import com.workerai.launcher.App;
import com.workerai.launcher.ui.panels.pages.Settings;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import fr.theshark34.openlauncherlib.util.Saver;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Folder extends Settings {
    public void init(Rectangle folderContainer, GridPane pane, Saver saver) {
        Label folderLabel = new Label();
        folderLabel.getStyleClass().add("afterLaunch-label");
        folderLabel.setText("Folders");
        setCenterH(folderLabel);
        setTop(folderLabel);
        folderLabel.setTranslateX(folderContainer.getTranslateX() - 440);
        folderLabel.setTranslateY(folderContainer.getTranslateY() + 15d);
        pane.getChildren().add(folderLabel);

        FontAwesomeIconView folderIcon = new FontAwesomeIconView(FontAwesomeIcon.ARCHIVE);
        folderIcon.setFill(Color.WHITE);
        folderIcon.setSize("25px");
        setCenterH(folderIcon);
        setTop(folderIcon);
        folderIcon.setTranslateX(folderLabel.getTranslateX() - 60d);
        folderIcon.setTranslateY(folderLabel.getTranslateY() + 5d);
        pane.getChildren().add(folderIcon);

        Label folderSubLab = new Label();
        folderSubLab.getStyleClass().add("afterLaunch-subLabel");
        folderSubLab.setText("Having trouble launching? Send us your logs!");
        setTop(folderSubLab);
        setCenterH(folderSubLab);
        folderSubLab.setTranslateX(folderLabel.getTranslateX() - 15);
        folderSubLab.setTranslateY(folderLabel.getTranslateY() + 35d);
        pane.getChildren().add(folderSubLab);

        createContainer(200,200, 350, 430);

        Button openLogButton = new Button();
        openLogButton.getStyleClass().add("directory-button");
        openLogButton.setText("Open Logs File");
        openLogButton.setMaxWidth(155d);
        openLogButton.setMaxHeight(30d);
        setTop(openLogButton);
        setCenterH(openLogButton);
        openLogButton.setTranslateX(folderSubLab.getTranslateX() - 85d);
        openLogButton.setTranslateY(folderSubLab.getTranslateY() + 27d);
        pane.getChildren().add(openLogButton);

        FontAwesomeIconView logsIcon = new FontAwesomeIconView(FontAwesomeIcon.FILE_TEXT);
        logsIcon.setFill(Color.WHITE);
        logsIcon.setSize("18px");
        logsIcon.setTranslateX(-4d);
        openLogButton.setGraphic(logsIcon);

        openLogButton.setOnMouseEntered(e -> App.getInstance().getPanelManager().getStage().getScene().setCursor(Cursor.HAND));
        openLogButton.setOnMouseExited(e -> App.getInstance().getPanelManager().getStage().getScene().setCursor(Cursor.DEFAULT));
        openLogButton.setOnMouseClicked(e -> {
            Desktop desktop = Desktop.getDesktop();
            try {
                desktop.open(new File((App.getInstance().getLauncherDirectory().toFile().getAbsoluteFile() + "\\launcher.log")));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        Button openSaveFileButton = new Button();
        openSaveFileButton.getStyleClass().add("directory-button");
        openSaveFileButton.setText("Open Save File");
        openSaveFileButton.setMaxWidth(155d);
        openSaveFileButton.setMaxHeight(30d);
        setTop(openSaveFileButton);
        setCenterH(openSaveFileButton);
        openSaveFileButton.setTranslateX(folderSubLab.getTranslateX() + 85d);
        openSaveFileButton.setTranslateY(folderSubLab.getTranslateY() + 27d);
        pane.getChildren().add(openSaveFileButton);

        FontAwesomeIconView saveIcon = new FontAwesomeIconView(FontAwesomeIcon.FILE);
        saveIcon.setFill(Color.WHITE);
        saveIcon.setSize("18px");
        saveIcon.setTranslateX(-4d);
        openSaveFileButton.setGraphic(saveIcon);

        openSaveFileButton.setOnMouseEntered(e -> App.getInstance().getPanelManager().getStage().getScene().setCursor(Cursor.HAND));
        openSaveFileButton.setOnMouseExited(e -> App.getInstance().getPanelManager().getStage().getScene().setCursor(Cursor.DEFAULT));
        openSaveFileButton.setOnMouseClicked(e -> {
            Desktop desktop = Desktop.getDesktop();
            try {
                desktop.open(new File((App.getInstance().getLauncherDirectory().toFile().getAbsoluteFile() + "\\settings.save")));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }
}

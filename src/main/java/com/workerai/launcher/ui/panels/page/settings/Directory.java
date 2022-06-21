package com.workerai.launcher.ui.panels.page.settings;

import com.workerai.launcher.App;
import com.workerai.launcher.ui.panels.page.Settings;
import com.workerai.launcher.utils.SettingsManager;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import fr.theshark34.openlauncherlib.util.Saver;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.DirectoryChooser;

import java.io.File;
import java.nio.file.Path;

public class Directory extends Settings {
    public void init(Rectangle directoryContainer, GridPane pane, Saver saver) {
        //region [Launch Directory Sub Container]
        Label directoryLabel = new Label();
        directoryLabel.getStyleClass().add("afterLaunch-label");
        directoryLabel.setText("Launch Directory");
        setCenterH(directoryLabel);
        setTop(directoryLabel);
        directoryLabel.setTranslateX(directoryContainer.getTranslateX() - 440);
        directoryLabel.setTranslateY(directoryContainer.getTranslateY() + 15d);
        pane.getChildren().add(directoryLabel);

        FontAwesomeIconView directoryIcon = new FontAwesomeIconView(FontAwesomeIcon.FOLDER_OPEN);
        directoryIcon.setFill(Color.WHITE);
        directoryIcon.setSize("25px");
        setCenterH(directoryIcon);
        setTop(directoryIcon);
        directoryIcon.setTranslateX(directoryLabel.getTranslateX() - 111d);
        directoryIcon.setTranslateY(directoryLabel.getTranslateY() + 5d);
        pane.getChildren().add(directoryIcon);

        Label directorySubLabel = new Label();
        directorySubLabel.getStyleClass().add("afterLaunch-subLabel");
        directorySubLabel.setText("Select which directory to launch Minecraft from");
        setTop(directorySubLabel);
        setCenterH(directorySubLabel);
        directorySubLabel.setTranslateX(directoryLabel.getTranslateX() - 15);
        directorySubLabel.setTranslateY(directoryLabel.getTranslateY() + 35d);
        pane.getChildren().add(directorySubLabel);

        Button changeDirectory = new Button();
        Label curDisplayedDirectoryLabel = new Label();

        // Hide Launcher after Launch
        changeDirectory.getStyleClass().add("directory-button");
        changeDirectory.setText("Change Directory");
        changeDirectory.setMaxWidth(250);
        changeDirectory.setMaxHeight(30d);
        setTop(changeDirectory);
        setCenterH(changeDirectory);
        changeDirectory.setTranslateX(directorySubLabel.getTranslateX());
        changeDirectory.setTranslateY(directorySubLabel.getTranslateY() + 28d);
        pane.getChildren().add(changeDirectory);

        FontAwesomeIconView changeIcon = new FontAwesomeIconView(FontAwesomeIcon.COMPASS);
        changeIcon.setFill(Color.WHITE);
        changeIcon.setSize("20px");
        changeIcon.setTranslateX(-4d);
        changeDirectory.setGraphic(changeIcon);

        changeDirectory.setOnMouseEntered(e -> App.getInstance().getPanelManager().getStage().getScene().setCursor(Cursor.HAND));
        changeDirectory.setOnMouseExited(e -> App.getInstance().getPanelManager().getStage().getScene().setCursor(Cursor.DEFAULT));
        changeDirectory.setOnMouseClicked(e -> {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            File selectedDirectory = directoryChooser.showDialog(App.getInstance().getPanelManager().getStage());
            App.getInstance().getSettingsManager().setGameDirectory(Path.of(selectedDirectory.getAbsolutePath()));
            curDisplayedDirectoryLabel.setText(displayPathReduced(selectedDirectory));
            saver.set("GameDirectory", selectedDirectory.getAbsolutePath());
            App.getInstance().getLogger().info("New game directory set at: " + Path.of(selectedDirectory.getAbsolutePath()));
        });

        Label curDirectoryLabel = new Label();
        curDirectoryLabel.getStyleClass().add("directory-subLabel");
        curDirectoryLabel.setText("YOUR CURRENT DIRECTORY IS SET TO:");
        setTop(curDirectoryLabel);
        setCenterH(curDirectoryLabel);
        curDirectoryLabel.setTranslateX(directoryLabel.getTranslateX() - 15d);
        curDirectoryLabel.setTranslateY(changeDirectory.getTranslateY() + 45d);
        pane.getChildren().add(curDirectoryLabel);

        Region curDirectoryContainer = new Region();
        curDirectoryContainer.getStyleClass().add("directory-container");
        setTop(curDirectoryContainer);
        setCenterH(curDirectoryContainer);
        curDirectoryContainer.setTranslateX(directoryLabel.getTranslateX() - 15d);
        curDirectoryContainer.setTranslateY(curDirectoryLabel.getTranslateY() + 25d);
        pane.getChildren().add(curDirectoryContainer);

        curDisplayedDirectoryLabel.getStyleClass().add("directory-displayLabel");
        curDisplayedDirectoryLabel.setText(displayPathReduced(new File(saver.get("GameDirectory"))));
        setTop(curDisplayedDirectoryLabel);
        setCenterH(curDisplayedDirectoryLabel);
        curDisplayedDirectoryLabel.setTranslateX(curDirectoryContainer.getTranslateX());
        curDisplayedDirectoryLabel.setTranslateY(curDirectoryContainer.getTranslateY() + 3d);
        pane.getChildren().add(curDisplayedDirectoryLabel);
        //endregion
    }

    private String displayPathReduced(File file) {
        String relativePath = String.valueOf(Path.of(file.getAbsolutePath()));
        if(relativePath.length() > 44 ) {
            String s = relativePath.substring(43);
            return relativePath.replace(s, "..");
        }
        return String.valueOf(file);
    }
}

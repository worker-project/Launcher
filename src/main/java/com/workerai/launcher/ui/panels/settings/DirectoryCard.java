package com.workerai.launcher.ui.panels.settings;

import com.workerai.launcher.WorkerLauncher;
import com.workerai.launcher.ui.panels.pages.Settings;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import fr.theshark34.openlauncherlib.util.Saver;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.DirectoryChooser;

import java.io.File;
import java.nio.file.Path;

import static com.noideaindustry.jui.components.JuiButton.createFontButton;
import static com.noideaindustry.jui.components.JuiIcon.createAwesomeIcon;
import static com.noideaindustry.jui.components.JuiLabel.createLabel;
import static com.noideaindustry.jui.components.JuiRegion.createRegion;
import static com.workerai.launcher.utils.ColorManager.WHITE;

public class DirectoryCard extends Settings {
    public static void create(Pane container, Saver saver, StackPane card) {
        container.getChildren().add(card);

        createLabel(0, 20d, "Game Directory", createAwesomeIcon(-4d, 0, FontAwesomeIcon.FOLDER_OPEN, "25px", WHITE, card), "afterLaunch-label", Pos.TOP_CENTER, card);
        createLabel(0, 50d, "Select which directory to launch Minecraft from", null, "afterLaunch-subLabel", Pos.TOP_CENTER, card);

        createLabel(0, -65d, "YOUR CURRENT DIRECTORY IS SET TO:", null, "directory-subLabel", Pos.BOTTOM_CENTER, card);
        Label gamePath = createLabel(0, -45d, displayPathReduced(new File(saver.get("GameDirectory"))), null, "directory-displayLabel", Pos.BOTTOM_CENTER, card);
        createRegion(gamePath.getTranslateX(), gamePath.getTranslateY() * -1.05d, 290, 18, "directory-container", card);

        FontAwesomeIconView directoryIcon = createAwesomeIcon(-4d, 0, FontAwesomeIcon.FILE_TEXT, "20px", WHITE, card);
        Button directoryButton = createFontButton(0, -90d, 200d, 30d, "Change Directory", "directory-button", null, directoryIcon, Pos.BOTTOM_CENTER, card);
        directoryButton.setOnMouseClicked(e -> {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            File selectedDirectory = directoryChooser.showDialog(WorkerLauncher.getInstance().getPanelManager().getStage());
            try {
                WorkerLauncher.getInstance().getSettingsManager().setGameDirectory(Path.of(selectedDirectory.getAbsolutePath()));
                gamePath.setText(displayPathReduced(selectedDirectory));
                saver.set("GameDirectory", selectedDirectory.getAbsolutePath());
                WorkerLauncher.getInstance().getLogger().info("New game directory set at: " + Path.of(selectedDirectory.getAbsolutePath()));
            } catch (Exception ex) {
                WorkerLauncher.getInstance().getLogger().warn("Failed finding new game directory file, aborting!");
            }
        });
    }

    static String displayPathReduced(File file) {
        String relativePath = String.valueOf(Path.of(file.getAbsolutePath()));
        if (relativePath.length() > 44) {
            String s = relativePath.substring(43);
            return relativePath.replace(s, "..");
        }
        return String.valueOf(file);
    }
}

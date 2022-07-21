package com.workerai.launcher.ui.panels.pages.settings;

import com.workerai.launcher.App;
import com.workerai.launcher.ui.panels.pages.Settings;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import fr.theshark34.openlauncherlib.util.Saver;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

import java.awt.*;
import java.io.File;
import java.io.IOException;

import static com.noideaindustry.jui.JuiInterface.JuiButton.createFontButton;
import static com.noideaindustry.jui.JuiInterface.JuiIcon;
import static com.noideaindustry.jui.JuiInterface.createLabel;

public class FolderCard extends Settings {
    public static void create(GridPane container, Saver saver, StackPane card) {
        container.getChildren().add(card);

        createLabel(0, 50d, "Launcher Folders", JuiIcon.createFontIcon(-4d, 0, FontAwesomeIcon.ARCHIVE, "25px", null, Color.WHITE, card), "afterLaunch-label", Pos.TOP_CENTER, card);
        createLabel(0, 80d, "Having trouble launching? Send us your logs!", null, "afterLaunch-subLabel", Pos.TOP_CENTER, card);

        FontAwesomeIconView logIcon = JuiIcon.createFontIcon(-4d, 0, FontAwesomeIcon.FILE_TEXT, "18px", null, Color.WHITE, card);
        Button logButton = createFontButton(85d, -60d, 155d, 30d, "Open Logs File", "directory-button", null, logIcon, Pos.BOTTOM_CENTER, card);
        logButton.setOnMouseClicked(e -> {
            Desktop desktop = Desktop.getDesktop();
            try {
                desktop.open(new File((App.getInstance().getLauncherDirectory().toFile().getAbsoluteFile() + "\\logs.log")));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        FontAwesomeIconView saveIcon = JuiIcon.createFontIcon(-4d, 0, FontAwesomeIcon.FLOPPY_ALT, "18px", null, Color.WHITE, card);
        Button saveButton = createFontButton(-85d, -60d, 155d, 30d, "Open Save File", "directory-button", null, saveIcon, Pos.BOTTOM_CENTER, card);
        saveButton.setOnMouseClicked(e -> {
            Desktop desktop = Desktop.getDesktop();
            try {
                desktop.open(new File((App.getInstance().getLauncherDirectory().toFile().getAbsoluteFile() + "\\settings.save")));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }
}

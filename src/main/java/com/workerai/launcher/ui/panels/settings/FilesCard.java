package com.workerai.launcher.ui.panels.settings;

import com.workerai.launcher.WorkerLauncher;
import com.workerai.launcher.ui.panels.pages.Settings;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import fr.theshark34.openlauncherlib.util.Saver;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.awt.*;
import java.io.File;
import java.io.IOException;

import static com.noideaindustry.jui.components.JuiButton.createFontButton;
import static com.noideaindustry.jui.components.JuiIcon.createAwesomeIcon;
import static com.noideaindustry.jui.components.JuiLabel.createLabel;
import static com.workerai.launcher.utils.LauncherInfos.WHITE;

public class FilesCard extends Settings {
    public static void create(Pane container, Saver saver, StackPane card) {
        container.getChildren().add(card);

        createLabel(0, 50d, "Launcher Files", createAwesomeIcon(-4d, 0, FontAwesomeIcon.ARCHIVE, "25px", WHITE, card), "afterLaunch-label", Pos.TOP_CENTER, card);
        createLabel(0, 80d, "Having trouble launching? Send us your logs!", null, "afterLaunch-subLabel", Pos.TOP_CENTER, card);

        FontAwesomeIconView logIcon = createAwesomeIcon(-4d, 0, FontAwesomeIcon.FILE_TEXT, "18px", WHITE, card);
        Button logButton = createFontButton(85d, -60d, 155d, 30d, "Open Logs File", "directory-button", null, logIcon, Pos.BOTTOM_CENTER, card);
        logButton.setOnMouseClicked(e -> {
            Desktop desktop = Desktop.getDesktop();
            try {
                desktop.open(new File((WorkerLauncher.getInstance().getLauncherDirectory().toFile().getAbsoluteFile() + "\\logs.log")));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        FontAwesomeIconView saveIcon = createAwesomeIcon(-4d, 0, FontAwesomeIcon.FLOPPY_ALT, "18px", WHITE, card);
        Button saveButton = createFontButton(-85d, -60d, 155d, 30d, "Open Save File", "directory-button", null, saveIcon, Pos.BOTTOM_CENTER, card);
        saveButton.setOnMouseClicked(e -> {
            Desktop desktop = Desktop.getDesktop();
            try {
                desktop.open(new File((WorkerLauncher.getInstance().getLauncherDirectory().toFile().getAbsoluteFile() + "\\settings.save")));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }
}

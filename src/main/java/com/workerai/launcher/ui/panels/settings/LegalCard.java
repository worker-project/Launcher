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
import java.net.URI;
import java.net.URISyntaxException;

import static com.noideaindustry.jui.components.JuiButton.createFontButton;
import static com.noideaindustry.jui.components.JuiIcon.createAwesomeIcon;
import static com.noideaindustry.jui.components.JuiLabel.createLabel;
import static com.workerai.launcher.utils.ColorManager.WHITE;

public class LegalCard extends Settings {
    public static void create(Pane container, Saver saver, StackPane card) {
        container.getChildren().add(card);

        createLabel(0, 50d, "Legal", createAwesomeIcon(-4d, 0d, FontAwesomeIcon.BOOK, "25px", WHITE, card), "afterLaunch-label", Pos.TOP_CENTER, card);
        createLabel(0, 80d, "Have a look at our terms of service & third-party licenses.", null, "afterLaunch-subLabel", Pos.TOP_CENTER, card);

        FontAwesomeIconView termsIcon = createAwesomeIcon(0, 0d, FontAwesomeIcon.BALANCE_SCALE, "16px", WHITE, card);
        Button termsButton = createFontButton(70d, -60d, 125d, 30d, "Terms", "directory-button", null, termsIcon, Pos.BOTTOM_CENTER, card);
        termsButton.setOnMouseClicked(e -> {
            try {
                Desktop.getDesktop().open(new File((WorkerLauncher.getInstance().getLegalFolder().toFile().getAbsoluteFile() + "\\TERMS")));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        FontAwesomeIconView licensesIcon = createAwesomeIcon(-2d, 0d, FontAwesomeIcon.SHARE_ALT, "16px", WHITE, card);
        Button licensesButton = createFontButton(-70d, -60d, 125d, 30d, "Licenses", "directory-button", null, licensesIcon, Pos.BOTTOM_CENTER, card);
        licensesButton.setOnMouseClicked(e -> {
            try {
                Desktop.getDesktop().open(new File((WorkerLauncher.getInstance().getLegalFolder().toFile().getAbsoluteFile() + "\\LICENSES")));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }
}

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

public class LegalCard extends Settings {
    public static void create(GridPane container, Saver saver, StackPane card) {
        container.getChildren().add(card);

        createLabel(0, 50d, "Legal", JuiIcon.createFontIcon(-4d, 0d, FontAwesomeIcon.BOOK, "25px", null, Color.WHITE, card), "afterLaunch-label", Pos.TOP_CENTER, card);
        createLabel(0, 80d, "Have a look at our terms of service & third-party licenses.", null, "afterLaunch-subLabel", Pos.TOP_CENTER, card);

        FontAwesomeIconView termsIcon = JuiIcon.createFontIcon(0, 0d, FontAwesomeIcon.BALANCE_SCALE, "16px", null, Color.WHITE, card);
        Button termsButton = createFontButton(70d, -60d, 125d, 30d, "Terms", "directory-button", null, termsIcon, Pos.BOTTOM_CENTER, card);
        termsButton.setOnMouseClicked(e -> {
            Desktop desktop = Desktop.getDesktop();
            try {
                desktop.open(new File((App.getInstance().getLauncherDirectory().toFile().getAbsoluteFile() + "\\launcher.log")));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        FontAwesomeIconView licensesIcon = JuiIcon.createFontIcon(-2d, 0d, FontAwesomeIcon.SHARE_ALT, "16px", null, Color.WHITE, card);
        Button licensesButton = createFontButton(-70d, -60d, 125d, 30d, "Licenses", "directory-button", null, licensesIcon, Pos.BOTTOM_CENTER, card);
        licensesButton.setOnMouseClicked(e -> {
            Desktop desktop = Desktop.getDesktop();
            try {
                desktop.open(new File((App.getInstance().getLauncherDirectory().toFile().getAbsoluteFile() + "\\settings.save")));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }
}

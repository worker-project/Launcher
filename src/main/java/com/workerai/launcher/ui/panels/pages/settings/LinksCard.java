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

import java.awt.*;
import java.io.File;
import java.io.IOException;

import static com.noideaindustry.jui.JuiInterface.JuiButton.createFontButton;
import static com.noideaindustry.jui.JuiInterface.JuiIcon;
import static com.noideaindustry.jui.JuiInterface.createLabel;
import static com.workerai.launcher.utils.LauncherInfos.WHITE;

public class LinksCard extends Settings {
    public static void create(GridPane container, Saver saver, StackPane card) {
        container.getChildren().add(card);

        createLabel(0, 50d, "Quick Links", JuiIcon.createFontIcon(-4d, 0, FontAwesomeIcon.SEND, "25px", null, WHITE, card), "afterLaunch-label", Pos.TOP_CENTER, card);
        createLabel(0, 80d, "Need help? Have a look at our support section.", null, "afterLaunch-subLabel", Pos.TOP_CENTER, card);

        FontAwesomeIconView supportIcon = JuiIcon.createFontIcon(-2d, 0, FontAwesomeIcon.LIFE_RING, "16px", null, WHITE, card);
        Button supportButton = createFontButton(70d, -60d, 125d, 30d, "Support", "directory-button", null, supportIcon, Pos.BOTTOM_CENTER, card);
        supportButton.setOnMouseClicked(e -> {
            Desktop desktop = Desktop.getDesktop();
            try {
                desktop.open(new File((App.getInstance().getLauncherDirectory().toFile().getAbsoluteFile() + "\\launcher.log")));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        FontAwesomeIconView faqIcon = JuiIcon.createFontIcon(-2d, 0d, FontAwesomeIcon.QUESTION, "20px", null, WHITE, card);
        Button faqButton = createFontButton(-70d, -60d, 125d, 30d, "FAQ", "directory-button", null, faqIcon, Pos.BOTTOM_CENTER, card);
        faqButton.setOnMouseClicked(e -> {
            Desktop desktop = Desktop.getDesktop();
            try {
                desktop.open(new File((App.getInstance().getLauncherDirectory().toFile().getAbsoluteFile() + "\\launcher.log")));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }
}

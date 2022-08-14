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

public class LinksCard extends Settings {
    public static void create(Pane container, Saver saver, StackPane card) {
        container.getChildren().add(card);

        createLabel(0, 50d, "Quick Links", createAwesomeIcon(-4d, 0, FontAwesomeIcon.SEND, "25px", WHITE, card), "afterLaunch-label", Pos.TOP_CENTER, card);
        createLabel(0, 80d, "Need help? Have a look at our support section.", null, "afterLaunch-subLabel", Pos.TOP_CENTER, card);

        FontAwesomeIconView supportIcon = createAwesomeIcon(-2d, 0, FontAwesomeIcon.LIFE_RING, "16px", WHITE, card);
        Button supportButton = createFontButton(70d, -60d, 125d, 30d, "Support", "directory-button", null, supportIcon, Pos.BOTTOM_CENTER, card);
        supportButton.setOnMouseClicked(e -> {
            try {
                Desktop.getDesktop().browse(new URI("https://discord.gg/DzmwCwmfZD"));
            } catch (IOException | URISyntaxException ex) {
                throw new RuntimeException(ex);
            }
        });

        FontAwesomeIconView faqIcon = createAwesomeIcon(-2d, 0d, FontAwesomeIcon.QUESTION, "20px", WHITE, card);
        Button faqButton = createFontButton(-70d, -60d, 125d, 30d, "FAQ", "directory-button", null, faqIcon, Pos.BOTTOM_CENTER, card);
        faqButton.setOnMouseClicked(e -> {
            Desktop desktop = Desktop.getDesktop();
            try {
                Desktop.getDesktop().browse(new URI("https://discord.gg/DzmwCwmfZD"));
            } catch (IOException | URISyntaxException ex) {
                throw new RuntimeException(ex);
            }
        });
    }
}

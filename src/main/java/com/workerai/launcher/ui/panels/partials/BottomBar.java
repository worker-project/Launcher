package com.workerai.launcher.ui.panels.partials;

import com.workerai.launcher.WorkerLauncher;
import com.workerai.launcher.savers.AccountManager;
import com.workerai.launcher.ui.panels.PanelManager;
import com.workerai.launcher.ui.panels.pages.Home;
import com.workerai.launcher.ui.panels.pages.Login;
import com.workerai.launcher.ui.utils.Panel;
import com.workerai.launcher.utils.ResourceManager;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import javafx.scene.Cursor;
import javafx.scene.layout.GridPane;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import static com.noideaindustry.jui.components.JuiIcon.createAwesomeIcon;
import static com.noideaindustry.jui.components.JuiIcon.createDesignIcon;
import static com.noideaindustry.jui.components.JuiLabel.createLabel;
import static com.workerai.launcher.utils.ColorManager.DARK_GRAY;
import static com.workerai.launcher.utils.ColorManager.YELLOW;

public class BottomBar extends Panel {
    private static BottomBar INSTANCE;

    public FontAwesomeIconView homeButton, debugButton, logoutButton;
    private MaterialDesignIconView websiteButton, twitterButton, discordButton;
    private FontAwesomeIconView shoppingButton;

    @Override
    public void init(PanelManager panelManager) {
        INSTANCE = this;
        super.init(panelManager);

        GridPane bottomBarPane = this.layout;
        bottomBarPane.getStyleClass().add("bottom-bar");
        setCanTakeAllWidth(bottomBarPane);

        createLabel(10d, 5d, "© NoIdeaIndustry, LLC 2022 • v1.0.0", "bottom-label", bottomBarPane);
        createLabel(this.panelManager.getStage().getWidth() - 220d - 10d, 5d, "Not affiliated with Mojang, AB.", "bottom-label", bottomBarPane);

        websiteButton = createDesignIcon(0d, 5d, MaterialDesignIcon.WEB, "25px", "buttons", DARK_GRAY, bottomBarPane, Cursor.HAND);
        websiteButton.setOnMouseClicked(e -> {
            try {
                Desktop.getDesktop().browse(new URI("https://github.com/NoIdeaIndustry"));
            } catch (IOException | URISyntaxException ex) {
                throw new RuntimeException(ex);
            }
        });

        twitterButton = createDesignIcon(0d, 5d, MaterialDesignIcon.TWITTER, "25px", "buttons", DARK_GRAY, bottomBarPane, Cursor.HAND);
        twitterButton.setOnMouseClicked(e -> {
            try {
                Desktop.getDesktop().browse(new URI("https://twitter.com/NoIdeaIndustry"));
            } catch (IOException | URISyntaxException ex) {
                throw new RuntimeException(ex);
            }
        });

        discordButton = createDesignIcon(0d, 5d, MaterialDesignIcon.DISCORD, "25px", "buttons", DARK_GRAY, bottomBarPane, Cursor.HAND);
        discordButton.setOnMouseClicked(e -> {
            try {
                Desktop.getDesktop().browse(new URI("https://discord.gg/MHRm4fyf9V"));
            } catch (IOException | URISyntaxException ex) {
                throw new RuntimeException(ex);
            }
        });

        shoppingButton = createAwesomeIcon(0d, 5d, FontAwesomeIcon.SHOPPING_CART, "25px", "buttons", DARK_GRAY, bottomBarPane, Cursor.HAND);
        shoppingButton.setOnMouseClicked(e -> {
            try {
                Desktop.getDesktop().browse(new URI("https://noideaindustry.com/shop"));
            } catch (IOException | URISyntaxException ex) {
                throw new RuntimeException(ex);
            }
        });

        debugButton = createAwesomeIcon(this.panelManager.getStage().getWidth() / 2, 5d, FontAwesomeIcon.BUG, "25px", "buttons", YELLOW, bottomBarPane, Cursor.HAND);
        debugButton.setOnMouseClicked(e -> {
            WorkerLauncher.setDebugMode(true);
            WorkerLauncher.getInstance().getLogger().warn("Entering debug session, online services will be unavailable!");
            this.panelManager.showPanel(new Home());
        });

        homeButton = createAwesomeIcon(0d, 5d, FontAwesomeIcon.HOME, "25px", "buttons", YELLOW, bottomBarPane, Cursor.HAND);
        homeButton.setOnMouseClicked(e -> {
            homeButton.setVisible(false);
            this.panelManager.showPanel(new Home());
        });

        logoutButton = createAwesomeIcon(0d, 5d, FontAwesomeIcon.SIGN_OUT, "25px", "buttons", YELLOW, bottomBarPane, Cursor.HAND);
        logoutButton.setOnMouseClicked(e -> {
            if (WorkerLauncher.isDebugMode()) {
                WorkerLauncher.setDebugMode(false);
                WorkerLauncher.getInstance().getLogger().warn("Leaving debug session, online services will be available once connected!");
            }
            AccountManager.setCurrentAccount(null);
            this.panelManager.showPanel(new Login(false));
        });

        setCanTakeAllWidth(websiteButton, twitterButton, discordButton, shoppingButton, homeButton, logoutButton);
    }

    public void setHomeIcons() {
        disableBottomButtons(false);

        homeButton.setVisible(false);
        logoutButton.setVisible(true);
        debugButton.setVisible(false);

        logoutButton.setTranslateX(debugButton.getTranslateX());

        websiteButton.setTranslateX(logoutButton.getTranslateX() - 120d);
        twitterButton.setTranslateX(logoutButton.getTranslateX() - 60d);
        discordButton.setTranslateX(logoutButton.getTranslateX() + 60d);
        shoppingButton.setTranslateX(logoutButton.getTranslateX() + 120d);
    }

    public void setLoginIcons() {
        disableBottomButtons(false);

        homeButton.setVisible(false);
        logoutButton.setVisible(false);
        debugButton.setVisible(true);

        websiteButton.setTranslateX(debugButton.getTranslateX() - 120d);
        twitterButton.setTranslateX(debugButton.getTranslateX() - 60d);
        discordButton.setTranslateX(debugButton.getTranslateX() + 60d);
        shoppingButton.setTranslateX(debugButton.getTranslateX() + 120d);
    }

    public void setDefaultIcons() {
        disableBottomButtons(false);

        homeButton.setVisible(true);
        logoutButton.setVisible(false);
        debugButton.setVisible(false);

        homeButton.setTranslateX(logoutButton.getTranslateX());

        websiteButton.setTranslateX(logoutButton.getTranslateX() - 120d);
        twitterButton.setTranslateX(logoutButton.getTranslateX() - 60d);
        discordButton.setTranslateX(logoutButton.getTranslateX() + 60d);
        shoppingButton.setTranslateX(logoutButton.getTranslateX() + 120d);
    }

    public void disableBottomButtons(boolean disabled) {
        debugButton.setDisable(disabled);

        websiteButton.setDisable(disabled);
        twitterButton.setDisable(disabled);
        discordButton.setDisable(disabled);
        shoppingButton.setDisable(disabled);
    }

    public static BottomBar getInstance() {
        return INSTANCE;
    }

    @Override
    public String getStylesheetPath() {
        return ResourceManager.getBottomDesign();
    }
}

package com.workerai.launcher.ui.panels.partials;

import com.workerai.launcher.App;
import com.workerai.launcher.database.Requests;
import com.workerai.launcher.savers.AccountSaver;
import com.workerai.launcher.ui.PanelManager;
import com.workerai.launcher.ui.panel.Panel;
import com.workerai.launcher.ui.panels.pages.Home;
import com.workerai.launcher.ui.panels.pages.Login;
import com.workerai.launcher.ui.panels.pages.Settings;
import com.workerai.launcher.utils.ResourceManager;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class BottomBar extends Panel {
    private static BottomBar INSTANCE;

    public static boolean DEBUG_MODE = false;

    public FontAwesomeIconView homeButton = new FontAwesomeIconView(FontAwesomeIcon.HOME);
    public FontAwesomeIconView settingsButton = new FontAwesomeIconView(FontAwesomeIcon.GEARS);
    public FontAwesomeIconView logoutButton = new FontAwesomeIconView(FontAwesomeIcon.SIGN_OUT);
    public FontAwesomeIconView debugButton = new FontAwesomeIconView(FontAwesomeIcon.BUG);

    private final MaterialDesignIconView websiteButton = new MaterialDesignIconView(MaterialDesignIcon.WEB);
    private final MaterialDesignIconView twitterButton = new MaterialDesignIconView(MaterialDesignIcon.TWITTER);
    private final MaterialDesignIconView discordButton = new MaterialDesignIconView(MaterialDesignIcon.DISCORD);
    private final FontAwesomeIconView shoppingButton = new FontAwesomeIconView(FontAwesomeIcon.SHOPPING_CART);

    @Override
    public void init(PanelManager panelManager) {
        INSTANCE = this;
        super.init(panelManager);

        GridPane bottomBarPane = this.layout;
        bottomBarPane.getStyleClass().add("bottom");
        setCanTakeAllWidth(bottomBarPane);

        //region [BottomBar : Information]
        Label copyrightLabel = new Label();
        copyrightLabel.setText("© NoIdeaIndustry, LLC 2022 • v1.0.0");
        copyrightLabel.getStyleClass().add("bottom-label");
        setCenterH(copyrightLabel);
        setLeft(copyrightLabel);
        copyrightLabel.setTranslateX(10d);
        copyrightLabel.setTranslateY(5d);
        bottomBarPane.getChildren().add(copyrightLabel);

        Label affiliationLabel = new Label();
        affiliationLabel.setText("Not affiliated with Mojang, AB.");
        affiliationLabel.getStyleClass().add("bottom-label");
        setCenterH(affiliationLabel);
        setRight(affiliationLabel);
        affiliationLabel.setTranslateX(-10d);
        affiliationLabel.setTranslateY(5d);
        bottomBarPane.getChildren().add(affiliationLabel);
        //endregion

        //region [BottomBar : Button Website]
        websiteButton.setFill(Color.rgb(67, 67, 67));
        websiteButton.setOpacity(.7f);
        websiteButton.getStyleClass().add("button");
        websiteButton.setSize("25px");
        setCenterH(websiteButton);
        websiteButton.setTranslateY(5f);
        bottomBarPane.getChildren().add(websiteButton);

        websiteButton.setOnMouseEntered(e -> this.panelManager.getStage().getScene().setCursor(Cursor.HAND));
        websiteButton.setOnMouseExited(e -> this.panelManager.getStage().getScene().setCursor(Cursor.DEFAULT));
        websiteButton.setOnMouseClicked(e -> {
            try {
                Desktop.getDesktop().browse(new URI("https://github.com/NoIdeaIndustry"));
            } catch (IOException | URISyntaxException ex) {
                throw new RuntimeException(ex);
            }
        });
        //endregion

        // region [BottomBar : Button Twitter]
        twitterButton.setFill(Color.rgb(67, 67, 67));
        twitterButton.setOpacity(.7f);
        twitterButton.getStyleClass().add("button");
        twitterButton.setSize("25px");
        setCenterH(twitterButton);
        twitterButton.setTranslateY(5f);
        bottomBarPane.getChildren().add(twitterButton);

        twitterButton.setOnMouseEntered(e -> this.panelManager.getStage().getScene().setCursor(Cursor.HAND));
        twitterButton.setOnMouseExited(e -> this.panelManager.getStage().getScene().setCursor(Cursor.DEFAULT));
        twitterButton.setOnMouseClicked(e -> {
            try {
                Desktop.getDesktop().browse(new URI("https://twitter.com/NoIdeaIndustry"));
            } catch (IOException | URISyntaxException ex) {
                throw new RuntimeException(ex);
            }
        });
        //endregion

        //region [BottomBar : Button Discord]
        discordButton.setFill(Color.rgb(67, 67, 67));
        discordButton.setOpacity(.7f);
        discordButton.getStyleClass().add("button");
        discordButton.setSize("25px");
        setCenterH(discordButton);
        discordButton.setTranslateY(5f);
        bottomBarPane.getChildren().add(discordButton);

        discordButton.setOnMouseEntered(e -> this.panelManager.getStage().getScene().setCursor(Cursor.HAND));
        discordButton.setOnMouseExited(e -> this.panelManager.getStage().getScene().setCursor(Cursor.DEFAULT));
        discordButton.setOnMouseClicked(e -> {
            try {
                Desktop.getDesktop().browse(new URI("https://discord.gg/MHRm4fyf9V"));
            } catch (IOException | URISyntaxException ex) {
                throw new RuntimeException(ex);
            }
        });
        //endregion

        //region [BottomBar : Button Shopping Cart]
        shoppingButton.setFill(Color.rgb(67, 67, 67));
        shoppingButton.setOpacity(.7f);
        shoppingButton.getStyleClass().add("button");
        shoppingButton.setSize("25px");
        setCenterH(shoppingButton);
        shoppingButton.setTranslateY(5f);
        bottomBarPane.getChildren().add(shoppingButton);

        shoppingButton.setOnMouseEntered(e -> this.panelManager.getStage().getScene().setCursor(Cursor.HAND));
        shoppingButton.setOnMouseExited(e -> this.panelManager.getStage().getScene().setCursor(Cursor.DEFAULT));
        shoppingButton.setOnMouseClicked(e -> {
            //setPage(new Settings(), settingButton);
        });
        //endregion

        //region [BottomBar : Button Settings/Home/Logout]
        homeButton.setFill(Color.rgb(67, 67, 67));
        homeButton.setOpacity(.7f);
        homeButton.getStyleClass().add("button");
        homeButton.setSize("25px");
        setCenterH(homeButton);
        homeButton.setTranslateY(5f);
        bottomBarPane.getChildren().add(homeButton);

        homeButton.setOnMouseEntered(e -> this.panelManager.getStage().getScene().setCursor(Cursor.HAND));
        homeButton.setOnMouseExited(e -> this.panelManager.getStage().getScene().setCursor(Cursor.DEFAULT));
        homeButton.setOnMouseClicked(e -> {
            settingsButton.setVisible(true);
            homeButton.setVisible(false);

            this.panelManager.showPanel(new Home());
        });

        logoutButton.setFill(Color.rgb(67, 67, 67));
        logoutButton.setOpacity(.7f);
        logoutButton.getStyleClass().add("button");
        logoutButton.setSize("25px");
        setCenterH(logoutButton);
        logoutButton.setTranslateY(5f);
        bottomBarPane.getChildren().add(logoutButton);

        logoutButton.setOnMouseEntered(e -> this.panelManager.getStage().getScene().setCursor(Cursor.HAND));
        logoutButton.setOnMouseExited(e -> this.panelManager.getStage().getScene().setCursor(Cursor.DEFAULT));
        logoutButton.setOnMouseClicked(e -> {
            DEBUG_MODE = false;
            if (AccountSaver.getCurrentAccount() != null)
                Requests.removeAccount(AccountSaver.getCurrentAccount().getUuid());
            this.panelManager.showPanel(new Login());
        });

        settingsButton.setFill(Color.rgb(67, 67, 67));
        settingsButton.setOpacity(.7f);
        settingsButton.getStyleClass().add("button");
        settingsButton.setSize("25px");
        setCenterH(settingsButton);
        settingsButton.setTranslateY(5f);
        bottomBarPane.getChildren().add(settingsButton);

        settingsButton.setOnMouseEntered(e -> this.panelManager.getStage().getScene().setCursor(Cursor.HAND));
        settingsButton.setOnMouseExited(e -> this.panelManager.getStage().getScene().setCursor(Cursor.DEFAULT));
        settingsButton.setOnMouseClicked(e -> {
            this.panelManager.showPanel(new Settings());
        });

        debugButton.setFill(Color.rgb(67, 67, 67));
        debugButton.setOpacity(.7f);
        debugButton.getStyleClass().add("button");
        debugButton.setSize("25px");
        setCenterH(debugButton);
        debugButton.setTranslateY(5f);
        bottomBarPane.getChildren().add(debugButton);

        debugButton.setOnMouseEntered(e -> this.panelManager.getStage().getScene().setCursor(Cursor.HAND));
        debugButton.setOnMouseExited(e -> this.panelManager.getStage().getScene().setCursor(Cursor.DEFAULT));
        debugButton.setOnMouseClicked(e -> {
            DEBUG_MODE = true;
            App.getInstance().getLogger().warn("Entering debug session, online services will be unavailable!");
            this.panelManager.showPanel(new Home());
        });
        //endregion

        setCanTakeAllWidth(websiteButton, twitterButton, discordButton, shoppingButton, settingsButton, homeButton, logoutButton);
    }

    public void setHomeIcons() {
        homeButton.setVisible(false);
        logoutButton.setVisible(true);
        settingsButton.setVisible(true);
        debugButton.setVisible(false);

        logoutButton.setTranslateX(-30d);
        settingsButton.setTranslateX(30d);

        websiteButton.setTranslateX(logoutButton.getTranslateX() - 120d);
        twitterButton.setTranslateX(logoutButton.getTranslateX() - 60d);
        discordButton.setTranslateX(settingsButton.getTranslateX() + 60d);
        shoppingButton.setTranslateX(settingsButton.getTranslateX() + 120d);
    }

    public void setLoginIcons() {
        homeButton.setVisible(false);
        logoutButton.setVisible(false);
        settingsButton.setVisible(false);
        debugButton.setVisible(true);

        websiteButton.setTranslateX(debugButton.getTranslateX() - 120d);
        twitterButton.setTranslateX(debugButton.getTranslateX() - 60d);
        discordButton.setTranslateX(debugButton.getTranslateX() + 60d);
        shoppingButton.setTranslateX(debugButton.getTranslateX() + 120d);
    }

    public void setElseIcons() {
        homeButton.setVisible(true);
        logoutButton.setVisible(true);
        settingsButton.setVisible(false);
        debugButton.setVisible(false);

        homeButton.setTranslateX(settingsButton.getTranslateX());

        websiteButton.setTranslateX(logoutButton.getTranslateX() - 120d);
        twitterButton.setTranslateX(logoutButton.getTranslateX() - 60d);
        discordButton.setTranslateX(settingsButton.getTranslateX() + 60d);
        shoppingButton.setTranslateX(settingsButton.getTranslateX() + 120d);
    }

    public static BottomBar getInstance() {
        return INSTANCE;
    }

    @Override
    public String getStylesheetPath() {
        return ResourceManager.getBottomDesign();
    }
}

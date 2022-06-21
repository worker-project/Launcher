package com.workerai.launcher.ui.panels.partials;

import com.workerai.launcher.App;
import com.workerai.launcher.ui.PanelManager;
import com.workerai.launcher.ui.panel.Panel;
import com.workerai.launcher.ui.panels.page.Home;
import com.workerai.launcher.ui.panels.page.Login;
import com.workerai.launcher.ui.panels.page.Settings;
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

    public FontAwesomeIconView homeButton = new FontAwesomeIconView(FontAwesomeIcon.HOME);
    public FontAwesomeIconView settingsButton = new FontAwesomeIconView(FontAwesomeIcon.GEARS);
    public FontAwesomeIconView logoutButton = new FontAwesomeIconView(FontAwesomeIcon.SIGN_OUT);
    public FontAwesomeIconView debugButton = new FontAwesomeIconView(FontAwesomeIcon.BUG);

    private MaterialDesignIconView websiteButton = new MaterialDesignIconView(MaterialDesignIcon.WEB);
    private MaterialDesignIconView twitterButton = new MaterialDesignIconView(MaterialDesignIcon.TWITTER);
    private MaterialDesignIconView discordButton = new MaterialDesignIconView(MaterialDesignIcon.DISCORD);
    private FontAwesomeIconView shoppingButton = new FontAwesomeIconView(FontAwesomeIcon.SHOPPING_CART);

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
        websiteButton.setFill(Color.rgb(50,50,50));
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
        twitterButton.setFill(Color.rgb(50,50,50));
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
        discordButton.setFill(Color.rgb(50,50,50));
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
        shoppingButton.setFill(Color.rgb(50,50,50));
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
        homeButton.setFill(Color.rgb(50,50,50));
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

        logoutButton.setFill(Color.rgb(50,50,50));
        logoutButton.setOpacity(.7f);
        logoutButton.getStyleClass().add("button");
        logoutButton.setSize("25px");
        setCenterH(logoutButton);
        logoutButton.setTranslateY(5f);
        bottomBarPane.getChildren().add(logoutButton);

        logoutButton.setOnMouseEntered(e -> this.panelManager.getStage().getScene().setCursor(Cursor.HAND));
        logoutButton.setOnMouseExited(e -> this.panelManager.getStage().getScene().setCursor(Cursor.DEFAULT));
        logoutButton.setOnMouseClicked(e -> {
            App.getInstance().getSettingsManager().getSaver().set("AutoAuth", String.valueOf(false));
            App.getInstance().getAccountManager().removeClientToken(App.getInstance().getAccountManager().getCurrClientToken());
            App.getInstance().getAccountManager().removeAccessToken(App.getInstance().getAccountManager().getCurrAccessToken());
            App.getInstance().getAccountManager().getSaver().save();
            App.getInstance().setAuthInfos(null);

            this.panelManager.showPanel(new Login());
        });

        settingsButton.setFill(Color.rgb(50,50,50));
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

        debugButton.setFill(Color.rgb(50,50,50));
        debugButton.setOpacity(.7f);
        debugButton.getStyleClass().add("button");
        debugButton.setSize("25px");
        setCenterH(debugButton);
        debugButton.setTranslateY(5f);
        bottomBarPane.getChildren().add(debugButton);

        debugButton.setOnMouseEntered(e -> this.panelManager.getStage().getScene().setCursor(Cursor.HAND));
        debugButton.setOnMouseExited(e -> this.panelManager.getStage().getScene().setCursor(Cursor.DEFAULT));
        debugButton.setOnMouseClicked(e -> {
            App.getInstance().getLogger().info("Entering debug session, online services will be unavailable!");
            this.panelManager.showPanel(new Home());
        });
        //endregion

        setCanTakeAllWidth(websiteButton, twitterButton, discordButton, shoppingButton, settingsButton, homeButton, logoutButton);
    }

    public void setIconPadding(double webPadding, double twitterPadding, double discordPadding, double shoppingPadding) {
        websiteButton.setTranslateX(webPadding);
        twitterButton.setTranslateX(twitterPadding);
        discordButton.setTranslateX(discordPadding);
        shoppingButton.setTranslateX(shoppingPadding);
    }

    public static BottomBar getInstance() { return INSTANCE; }
    @Override public String getStylesheetPath() { return ResourceManager.getBottomDesign(); }
}

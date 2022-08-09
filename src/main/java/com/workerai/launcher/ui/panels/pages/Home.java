package com.workerai.launcher.ui.panels.pages;

import com.workerai.launcher.WorkerLauncher;
import com.workerai.launcher.savers.AccountManager;
import com.workerai.launcher.ui.panels.PanelManager;
import com.workerai.launcher.ui.panels.partials.BottomBar;
import com.workerai.launcher.ui.utils.Panel;
import com.workerai.launcher.utils.PlayManager;
import com.workerai.launcher.utils.ResourceManager;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

import static com.noideaindustry.jui.components.JuiButton.createFontButton;
import static com.noideaindustry.jui.components.JuiButton.createMaterialButton;
import static com.noideaindustry.jui.components.JuiIcon.createAwesomeIcon;
import static com.noideaindustry.jui.components.JuiIcon.createDesignIcon;
import static com.noideaindustry.jui.components.JuiLabel.createLabel;
import static com.noideaindustry.jui.components.JuiPane.createGridPane;
import static com.noideaindustry.jui.components.JuiPane.createStackPane;
import static com.workerai.launcher.utils.DisplayManager.*;
import static com.workerai.launcher.utils.LauncherInfos.*;

public class Home extends Panel {
    @Override
    public void init(PanelManager panelManager) {
        super.init(panelManager);

        BottomBar.getInstance().setHomeIcons();

        GridPane backgroundPane = createGridPane("background-login");
        this.layout.getChildren().add(backgroundPane);
        setCanTakeAllSize(this.layout, backgroundPane);

        StackPane homePane = createStackPane(0d, 0d, 1200d, 600d, "home-panel", LIGHT_BLACK);
        this.layout.getChildren().add(homePane);

        FontAwesomeIconView titleIcon = createAwesomeIcon(-4d, 0, FontAwesomeIcon.FLASK, "25px", WHITE, homePane);
        createLabel(0d, 90d, "Ready to take advantage of WorkerAI?", titleIcon, "home-label", Pos.TOP_CENTER, homePane);
        createLabel(0d, 120d, "Start the game and have fun using our custom client!", null, "home-subLabel", Pos.TOP_CENTER, homePane);

        displayBanner(homePane, createStackPane(0d, -100d, 260d, 600d / 7, true));
        displayNews(homePane, createStackPane(0d, 112.5d, 1100d, 300d, true));

        FontAwesomeIconView playIcon = createAwesomeIcon(0d, 0d, FontAwesomeIcon.PLAY_CIRCLE, "40px", YELLOW, null);
        Button playButton = createFontButton(80d, -100d, 40d, 60d, null, "home-button", null, playIcon, null, homePane);
        playButton.setOnMouseClicked(e -> PlayManager.downloadAndPlay(homePane, true));

        FontAwesomeIconView settingsIcon = createAwesomeIcon(0d, 0d, FontAwesomeIcon.GEAR, "40px", YELLOW, homePane);
        Button settingsButton = createFontButton(0d, -100d, 40d, 60d, null, "home-button", null, settingsIcon, null, homePane);
        settingsButton.setOnMouseClicked(e -> this.panelManager.showPanel(new Settings()));

        MaterialDesignIconView accountsIcon = createDesignIcon(0d, 0d, MaterialDesignIcon.VIEW_LIST, "36px", YELLOW, homePane);
        Button accountsButton = createMaterialButton(-80d, -100d, 36d, 60d, true, "home-button", accountsIcon, homePane);
        accountsButton.setOnMouseClicked(e -> this.panelManager.showPanel(new Accounts()));

        displayCurrentSession(homePane, WorkerLauncher.isDebugMode() ? AccountManager.getDebugAccount() : AccountManager.getCurrentAccount(), createStackPane(-350d, -100d, 400d, 600d / 7, true));
        displayAccount(homePane, WorkerLauncher.isDebugMode() ? AccountManager.getDebugAccount() : AccountManager.getCurrentAccount(), createStackPane(350d, -100d, 400d, 600d / 7, true), true);
    }

    @Override
    public String getStylesheetPath() {
        return ResourceManager.getHomeDesign();
    }
}
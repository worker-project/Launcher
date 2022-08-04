package com.workerai.launcher.ui.panels.pages;

import com.workerai.launcher.savers.AccountSaver;
import com.workerai.launcher.ui.PanelManager;
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
import javafx.scene.paint.Color;

import static com.noideaindustry.jui.JuiInterface.JuiButton.createFontButton;
import static com.noideaindustry.jui.JuiInterface.JuiButton.createMaterialButton;
import static com.noideaindustry.jui.JuiInterface.JuiIcon.createDesignIcon;
import static com.noideaindustry.jui.JuiInterface.JuiIcon.createFontIcon;
import static com.noideaindustry.jui.JuiInterface.JuiPane.createGridPane;
import static com.noideaindustry.jui.JuiInterface.JuiPane.createStackPane;
import static com.noideaindustry.jui.JuiInterface.createLabel;
import static com.workerai.launcher.utils.DisplayManager.*;

public class Home extends Panel {
    @Override
    public void init(PanelManager panelManager) {
        super.init(panelManager);

        BottomBar.getInstance().setHomeIcons();

        GridPane backgroundPane = createGridPane(0d, 0d, 0d, 0d, 0d, 0d, "background-login", Color.TRANSPARENT);
        this.layout.getChildren().add(backgroundPane);
        setCanTakeAllSize(this.layout, backgroundPane);

        StackPane homePane = createStackPane(0d, 0d, 1200d, 600d, 15d, 15d, false, "home-panel", Pos.CENTER, Color.rgb(32, 31, 29));
        this.layout.getChildren().add(homePane);

        FontAwesomeIconView titleIcon = createFontIcon(-4d, 0, FontAwesomeIcon.FLASK, "25px", null, Color.WHITE, homePane);
        createLabel(0d, 90d, "Ready to take advantage of WorkerAI?", titleIcon, "home-label", Pos.TOP_CENTER, homePane);
        createLabel(0d, 120d, "Start the game and have fun using our custom client!", null, "home-subLabel", Pos.TOP_CENTER, homePane);

        displayBanner(homePane, createStackPane(0d, -100d, 260d, 600d / 7, 15d, 15d, true, null, null, Color.rgb(29, 29, 27)));
        displayNews(homePane, createStackPane(0d, 112.5d, 1100d, 300d, 15d, 15d, true, null, null, Color.rgb(29, 29, 27)));

        FontAwesomeIconView playIcon = createFontIcon(0d, 0d, FontAwesomeIcon.PLAY_CIRCLE, "40px", null, Color.rgb(210, 144, 52), null);
        Button playButton = createFontButton(80d, -100d, 40d, 60d, null, "home-button", null, playIcon, null, homePane);
        playButton.setOnMouseClicked(e -> PlayManager.downloadAndPlay(homePane, true));

        FontAwesomeIconView settingsIcon = createFontIcon(0d, 0d, FontAwesomeIcon.GEAR, "40px", null, Color.rgb(210, 144, 52), homePane);
        Button settingsButton = createFontButton(0d, -100d, 40d, 60d, null, "home-button", null, settingsIcon, null, homePane);
        settingsButton.setOnMouseClicked(e -> this.panelManager.showPanel(new Settings()));

        MaterialDesignIconView accountsIcon = createDesignIcon(0d, 0d, MaterialDesignIcon.VIEW_LIST, "36px", null, Color.rgb(210, 144, 52), homePane);
        Button accountsButton = createMaterialButton(-80d, -100d, 36d, 60d, null, "home-button", null, accountsIcon, null, homePane);
        accountsButton.setOnMouseClicked(e -> this.panelManager.showPanel(new Accounts()));

        displayReducedAccount(homePane, AccountSaver.getCurrentAccount(), createStackPane(-350d, -100d, 400d, 600d / 7, 15d, 15d, true, null, null, Color.rgb(29, 29, 27)));
        displayAccount(homePane, AccountSaver.getCurrentAccount(), createStackPane(350d, -100d, 400d, 600d / 7, 15d, 15d, true, null, null, Color.rgb(29, 29, 27)), true);
    }

    @Override
    public String getStylesheetPath() {
        return ResourceManager.getHomeDesign();
    }
}
package com.workerai.launcher.ui.panels.pages;

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
import static com.noideaindustry.jui.JuiInterface.JuiIcon;
import static com.noideaindustry.jui.JuiInterface.JuiPane.createGridPane;
import static com.noideaindustry.jui.JuiInterface.JuiPane.createStackPane;
import static com.noideaindustry.jui.JuiInterface.createLabel;

public class Home extends Panel {
    @Override
    public void init(PanelManager panelManager) {
        super.init(panelManager);

        BottomBar.getInstance().setHomeIcons();

        GridPane backgroundPane = createGridPane(0d, 0d, 0d, 0d, 0d, 0d, "background-login", Color.TRANSPARENT);
        this.layout.getChildren().add(backgroundPane);
        setCanTakeAllSize(this.layout, backgroundPane);

        StackPane homePane = createStackPane(0d, 0d, 1200d, 600d, 15d, 15d, "home-panel", Pos.CENTER, Color.rgb(32, 31, 29));
        this.layout.getChildren().add(homePane);

        FontAwesomeIconView titleIcon = JuiIcon.createFontIcon(-4d, 0, FontAwesomeIcon.FLASK, "25px", null, Color.WHITE, homePane);
        createLabel(0d, 90d, "Ready to take advantage of WorkerAI?", titleIcon, "home-label", Pos.TOP_CENTER, homePane);
        createLabel(0d, 120d, "Start the game and have fun using our custom client!", null, "home-subLabel", Pos.TOP_CENTER, homePane);

        FontAwesomeIconView playIcon = JuiIcon.createFontIcon(-5d, 0d, FontAwesomeIcon.PLAY_CIRCLE, "50px", null, Color.WHITE, homePane);
        Button playButton = createFontButton(200d, 0d, 350d, 80d, "LAUNCH CLIENT", "home-button", null, playIcon, Pos.CENTER, homePane);
        playButton.setOnMouseClicked(e -> PlayManager.downloadAndPlay(homePane));

        MaterialDesignIconView accountsIcon = JuiIcon.createDesignIcon(-5d, 0d, MaterialDesignIcon.VIEW_LIST, "50px", null, Color.WHITE, homePane);
        Button accountsButton = createMaterialButton(-200d, 0d, 350d, 80d, "ACCOUNT MANAGER", "home-button", null, accountsIcon, Pos.CENTER, homePane);
        accountsButton.setOnMouseClicked(e -> this.panelManager.showPanel(new Accounts()));
    }

    @Override
    public String getStylesheetPath() {
        return ResourceManager.getHomeDesign();
    }
}

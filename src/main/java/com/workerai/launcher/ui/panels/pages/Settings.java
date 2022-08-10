package com.workerai.launcher.ui.panels.pages;

import com.workerai.launcher.WorkerLauncher;
import com.workerai.launcher.ui.panels.PanelManager;
import com.workerai.launcher.ui.panels.partials.BottomBar;
import com.workerai.launcher.ui.panels.settings.*;
import com.workerai.launcher.ui.utils.Panel;
import com.workerai.launcher.utils.ResourceManager;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import fr.theshark34.openlauncherlib.util.Saver;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

import static com.noideaindustry.jui.components.JuiIcon.createAwesomeIcon;
import static com.noideaindustry.jui.components.JuiLabel.createLabel;
import static com.noideaindustry.jui.components.JuiPane.createGridPane;
import static com.noideaindustry.jui.components.JuiPane.createStackPane;
import static com.workerai.launcher.utils.LauncherInfos.*;

public class Settings extends Panel {
    private final Saver saver = WorkerLauncher.getInstance().getSettingsManager().getSaver();

    @Override
    public void init(PanelManager panelManager) {
        super.init(panelManager);

        BottomBar.getInstance().setDefaultIcons();

        GridPane backgroundPane = createGridPane("background-login");
        this.layout.getChildren().add(backgroundPane);
        setCanTakeAllSize(this.layout, backgroundPane);

        StackPane settingsPane = createStackPane(0d, 0d, 1200d, 600d, "settings-panel", LIGHT_BLACK);
        this.layout.getChildren().add(settingsPane);

        GridPane overlayPane = createGridPane("settings-panel", true);
        this.layout.getChildren().add(overlayPane);
        setCanTakeAllSize(overlayPane);

        FontAwesomeIconView titleIcon = createAwesomeIcon(-4d, 0, FontAwesomeIcon.GEARS, "25px", WHITE, settingsPane);
        createLabel(0d, 90d, "Launcher & Client Settings", titleIcon, "settings-label", Pos.TOP_CENTER, settingsPane);
        createLabel(0d, 120d, "Change your settings for a better personal fit.", null, "settings-subLabel", Pos.TOP_CENTER, settingsPane);

        GridPane cardHolder = new GridPane();
        settingsPane.getChildren().add(cardHolder);

        WindowCard.create(cardHolder, saver, createStackPane(70d, 185d, 350d, 200d, DARK_BLACK));
        DirectoryCard.create(cardHolder, saver, createStackPane(70d, 185d * 2 + 45d, 350d, 200d, DARK_BLACK));
        MemoryCard.create(cardHolder, saver, createStackPane(70d + 350d + 35d, 185d, 350d, 200d, DARK_BLACK));
        ResolutionCard.create(cardHolder, saver, createStackPane(70d + 350d + 35d, 185d * 2 + 45d, 350d, 200d, DARK_BLACK), overlayPane);

        FilesCard.create(cardHolder, saver, createStackPane(70d * 2 + 350d * 2, 185d - 37.5d, 350d, 125d, DARK_BLACK));
        LegalCard.create(cardHolder, saver, createStackPane(70d * 2 + 350d * 2, 185d - 37.5d + 152.2d, 350d, 125d, DARK_BLACK));
        LinksCard.create(cardHolder, saver, createStackPane(70d * 2 + 350d * 2, 185d - 37.5d + 152.2d * 2, 350d, 125d, DARK_BLACK));
    }

    @Override
    public String getStylesheetPath() {
        return ResourceManager.getSettingsDesign();
    }
}

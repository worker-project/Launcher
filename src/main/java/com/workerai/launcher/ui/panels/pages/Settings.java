package com.workerai.launcher.ui.panels.pages;

import com.workerai.launcher.App;
import com.workerai.launcher.ui.PanelManager;
import com.workerai.launcher.ui.utils.Panel;
import com.workerai.launcher.ui.panels.pages.settings.*;
import com.workerai.launcher.ui.panels.partials.BottomBar;
import com.workerai.launcher.utils.ResourceManager;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import fr.theshark34.openlauncherlib.util.Saver;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

import static com.noideaindustry.jui.JuiInterface.*;
import static com.noideaindustry.jui.JuiInterface.JuiPane.createStackPane;
import static com.noideaindustry.jui.JuiInterface.JuiPane.createGridPane;
public class Settings extends Panel {
    private final Saver saver = App.getInstance().getSettingsManager().getSaver();

    @Override
    public void init(PanelManager panelManager) {
        super.init(panelManager);

        BottomBar.getInstance().setElseIcons();

        GridPane backgroundPane = createGridPane(0d, 0d, 0d, 0d, 0d, 0d, "background-login", Color.TRANSPARENT);
        this.layout.getChildren().add(backgroundPane);
        setCanTakeAllSize(this.layout, backgroundPane);

        StackPane settingsPane = createStackPane(0d, 0d, 1200d, 600d, 15d, 15d, "settings-panel", Pos.CENTER, Color.rgb(32, 31, 29));
        this.layout.getChildren().add(settingsPane);

        FontAwesomeIconView titleIcon = JuiIcon.createFontIcon(-4d, 0, FontAwesomeIcon.GEARS, "25px", null,Color.WHITE, settingsPane);
        createLabel(0d, 90d, "Launcher & Client Settings", titleIcon, "settings-label", Pos.TOP_CENTER, settingsPane);
        createLabel(0d, 120d, "Change your settings for a better personal fit.", null, "settings-subLabel", Pos.TOP_CENTER, settingsPane);

        GridPane card = new GridPane();
        settingsPane.getChildren().add(card);

        WindowCard.create(card, saver, createStackPane(70d, 185d, 350d, 200d, 15d, 15d, null, null, Color.rgb(29, 29, 27)));
        DirectoryCard.create(card, saver, createStackPane(70d, 185d * 2 + 45d, 350d, 200d, 15d, 15d, null, null, Color.rgb(29, 29, 27)));
        MemoryCard.create(card, saver, createStackPane(70d + 350d + 35d, 185d, 350d, 200d, 15d, 15d, null, null, Color.rgb(29, 29, 27)));
        ResolutionCard.create(card, saver, createStackPane(70d + 350d + 35d, 185d * 2 + 45d, 350d, 200d, 15d, 15d, null, null, Color.rgb(29, 29, 27)));

        FolderCard.create(card, saver, createStackPane(70d * 2 + 350d * 2, 185d - 37.5d, 350d, 125d, 15d, 15d, null, null, Color.rgb(29, 29, 27)));
        LegalCard.create(card, saver, createStackPane(70d * 2 + 350d * 2, 185d - 37.5d + 152.2d, 350d, 125d, 15d, 15d, null, null, Color.rgb(29, 29, 27)));
        LinksCard.create(card, saver, createStackPane(70d * 2 + 350d * 2, 185d - 37.5d + 152.2d * 2, 350d, 125d, 15d, 15d, null, null, Color.rgb(29, 29, 27)));
    }

    @Override
    public String getStylesheetPath() {
        return ResourceManager.getSettingsDesign();
    }
}

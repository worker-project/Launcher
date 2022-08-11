package com.workerai.launcher.ui.panels.partials;

import com.workerai.launcher.ui.panels.PanelManager;
import com.workerai.launcher.ui.utils.Panel;
import com.workerai.launcher.utils.ResourceManager;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.scene.Cursor;
import javafx.scene.layout.GridPane;

import static com.noideaindustry.jui.components.JuiIcon.createAwesomeIcon;
import static com.noideaindustry.jui.components.JuiImageView.createImageView;
import static com.workerai.launcher.utils.ColorManager.WHITE;

public class TopBar extends Panel {

    @Override
    public void init(PanelManager panelManager) {
        super.init(panelManager);

        GridPane topBarPane = this.layout;
        this.layout.getStyleClass().add("top-bar");
        setCanTakeAllWidth(topBarPane);

        createImageView(5d, 3d, 0d, 28d, ResourceManager.getFavIcon(), topBarPane);

        FontAwesomeIconView closeButton = createAwesomeIcon(this.panelManager.getStage().getWidth() - 35d, 4d, FontAwesomeIcon.POWER_OFF, "25px", "buttons", WHITE, topBarPane, Cursor.HAND);
        closeButton.setOnMouseClicked(e -> System.exit(0));

        FontAwesomeIconView minimizeButton = createAwesomeIcon(this.panelManager.getStage().getWidth() - 70d, 5d, FontAwesomeIcon.MINUS, "25px", "buttons", WHITE, topBarPane, Cursor.HAND);
        minimizeButton.setOnMouseClicked(e -> this.panelManager.getStage().setIconified(true));

        setCanTakeAllWidth(closeButton, minimizeButton);
    }

    @Override
    public String getStylesheetPath() {
        return ResourceManager.getTopDesign();
    }
}

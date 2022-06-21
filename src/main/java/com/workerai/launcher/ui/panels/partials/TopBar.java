package com.workerai.launcher.ui.panels.partials;

import com.workerai.launcher.ui.PanelManager;
import com.workerai.launcher.ui.panel.Panel;
import com.workerai.launcher.utils.ResourceManager;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.scene.Cursor;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class TopBar extends Panel {

    @Override
    public void init(PanelManager panelManager) {
        super.init(panelManager);
        GridPane topBarPane = this.layout;
        this.layout.getStyleClass().add("top");
        setCanTakeAllWidth(topBarPane);

        //region [TopBar: Icon]
        ImageView favIcon = new ImageView(ResourceManager.getFavIcon());
        favIcon.setPreserveRatio(true);
        favIcon.setFitHeight(28d);
        favIcon.setTranslateX(5d);
        favIcon.setTranslateY(4d);
        setLeft(favIcon);
        topBarPane.getChildren().add(favIcon);
        //endregion

        //region [TopBar: Button Close]
        FontAwesomeIconView closeButton = new FontAwesomeIconView(FontAwesomeIcon.POWER_OFF);
        closeButton.setFill(Color.WHITE);
        closeButton.setOpacity(.7f);
        closeButton.getStyleClass().add("button");
        closeButton.setSize("25px");
        setRight(closeButton);
        closeButton.setTranslateX(-15f);
        closeButton.setTranslateY(4d);
        topBarPane.getChildren().add(closeButton);

        closeButton.setOnMouseEntered(e -> this.panelManager.getStage().getScene().setCursor(Cursor.HAND));
        closeButton.setOnMouseExited(e -> this.panelManager.getStage().getScene().setCursor(Cursor.DEFAULT));
        closeButton.setOnMouseClicked(e -> System.exit(0));
        //endregion

        //region [TopBar: Minimize Close]
        FontAwesomeIconView minimizeButton = new FontAwesomeIconView(FontAwesomeIcon.MINUS);
        minimizeButton.setFill(Color.WHITE);
        minimizeButton.setOpacity(.7f);
        minimizeButton.getStyleClass().add("button");
        minimizeButton.setSize("25px");
        setRight(minimizeButton);
        minimizeButton.setTranslateX(closeButton.getTranslateX() - 40d);
        minimizeButton.setTranslateY(5f);
        topBarPane.getChildren().add(minimizeButton);

        minimizeButton.setOnMouseEntered(e -> this.panelManager.getStage().getScene().setCursor(Cursor.HAND));
        minimizeButton.setOnMouseExited(e -> this.panelManager.getStage().getScene().setCursor(Cursor.DEFAULT));
        minimizeButton.setOnMouseClicked(e -> this.panelManager.getStage().setIconified(true));
        //endregion

        setCanTakeAllWidth(closeButton, minimizeButton);
    }

    @Override public String getStylesheetPath() { return ResourceManager.getTopDesign(); }
}

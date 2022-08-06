package com.workerai.launcher.ui.utils;

import com.workerai.launcher.ui.panels.PanelManager;
import javafx.animation.FadeTransition;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

public abstract class Panel implements IPanel, ITakePlace {
    protected GridPane layout = new GridPane();
    protected PanelManager panelManager;

    public void init(PanelManager panelManager) {
        this.panelManager = panelManager;
        setCanTakeAllWidth(this.layout);
    }

    @Override
    public void onShow() {
        FadeTransition transition = new FadeTransition(Duration.seconds(1), this.layout);
        transition.setFromValue(0.8);
        transition.setToValue(1);
        transition.setAutoReverse(true);
        transition.play();
    }

    @Override
    public GridPane getLayout() {
        return layout;
    }

    @Override
    public String getStylesheetPath() {
        return null;
    }
}

package com.workerai.launcher.ui.utils;

import com.workerai.launcher.ui.panels.PanelManager;
import javafx.scene.layout.GridPane;

public interface IPanel {

    void init(PanelManager panelManager);

    void onShow();

    GridPane getLayout();

    String getStylesheetPath();
}

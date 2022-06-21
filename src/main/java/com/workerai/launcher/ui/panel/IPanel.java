package com.workerai.launcher.ui.panel;

import com.workerai.launcher.ui.PanelManager;
import javafx.scene.layout.GridPane;

public interface IPanel {

    void init(PanelManager panelManager);
    void onShow();
    GridPane getLayout();
    String getStylesheetPath();
}

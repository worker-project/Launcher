package com.workerai.launcher.ui.panel;

import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

public interface ITakePlace {
    default void setCanTakeAllSize(Node ... nodes) {
        for(Node n : nodes) {
            GridPane.setHgrow(n, Priority.ALWAYS);
            GridPane.setVgrow(n, Priority.ALWAYS);
        }
    }

    default void setCanTakeAllWidth(Node ... nodes) {
        for(Node n : nodes) {
            GridPane.setHgrow(n , Priority.ALWAYS);
        }
    }
}

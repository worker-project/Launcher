package com.workerai.launcher.ui.utils;

import javafx.scene.Node;

public interface IMovable {
    void setLeft(Node node);

    void setRight(Node node);

    void setTop(Node node);

    void setBottom(Node node);

    void setCenterH(Node node);

    void setCenterV(Node node);
}
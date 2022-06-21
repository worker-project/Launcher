package com.workerai.launcher.ui.panels.page;

import com.workerai.launcher.ui.panel.Panel;
import javafx.animation.FadeTransition;
import javafx.util.Duration;

public abstract class Transition extends Panel {
    @Override
    public void onShow() {
        FadeTransition transition = new FadeTransition(Duration.seconds(1), this.layout);
        transition.setFromValue(0.8);
        transition.setToValue(1);
        transition.setAutoReverse(true);
        transition.play();
    }
}

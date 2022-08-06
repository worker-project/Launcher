package com.noideaindustry.jui;

import javafx.stage.Stage;

public class JuiInitialization {
    private static Stage fxStage;

    public static void setFxStage(Stage stage) {
        fxStage = stage;
    }

    public static Stage getFxStage() {
        return fxStage;
    }
}

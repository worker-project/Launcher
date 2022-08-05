package com.workerai.launcher.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DialogPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import static com.workerai.launcher.utils.ResourceManager.getAlertDesignPath;

public class AlertManager {
    public static void ShowError(Stage stage, String title, String content) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.initOwner(stage);
        alert.initStyle(StageStyle.UNDECORATED);
        alert.setHeaderText(title);
        alert.setContentText(content);

        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(getAlertDesignPath());
        dialogPane.getStyleClass().add("alert");

        alert.showAndWait();
    }

    public static void ShowWarning(Stage stage, String title, String content) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.initOwner(stage);
        alert.initStyle(StageStyle.UNDECORATED);
        alert.setHeaderText(title);
        alert.setContentText(content);

        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(getAlertDesignPath());
        dialogPane.getStyleClass().add("alert");

        alert.showAndWait();
    }
}

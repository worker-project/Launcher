package com.workerai.launcher.ui;

import com.goxr3plus.fxborderlessscene.borderless.BorderlessScene;
import com.workerai.launcher.ui.panel.IPanel;
import com.workerai.launcher.ui.panels.partials.BottomBar;
import com.workerai.launcher.ui.panels.partials.TopBar;
import com.workerai.launcher.utils.ResourceManager;
import fr.flowarg.flowcompat.Platform;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class PanelManager {
    private final Stage stage;
    private final GridPane panelContent = new GridPane();

    TopBar topBar = new TopBar();
    BottomBar bottomBar = new BottomBar();

    public PanelManager(Stage stage) {
        this.stage = stage;
    }

    public void init() {
        this.stage.setTitle("WorkerAI - Launcher");
        this.stage.centerOnScreen();

        this.stage.setWidth(1260d);
        this.stage.setHeight(750d);

        this.stage.getIcons().add(new Image(ResourceManager.getIcon()));

        GridPane layout = new GridPane();

        if (Platform.isOnLinux()) {
            Scene scene = new Scene(layout);
            this.stage.setScene(scene);
        } else {
            this.stage.initStyle(StageStyle.UNDECORATED);

            BorderlessScene scene = new BorderlessScene(this.stage, StageStyle.UNDECORATED, layout);
            scene.setMoveControl(topBar.getLayout());
            scene.removeDefaultCSS();
            scene.setResizable(false);

            this.stage.setScene(scene);

            RowConstraints topPaneConstraints = new RowConstraints();
            topPaneConstraints.setValignment(VPos.TOP);
            topPaneConstraints.setMinHeight(35);
            topPaneConstraints.setMaxHeight(35);
            layout.getRowConstraints().addAll(topPaneConstraints, new RowConstraints());
            layout.add(topBar.getLayout(), 0, 0);
            topBar.init(this);

            RowConstraints bottomPaneConstraints = new RowConstraints();
            bottomPaneConstraints.setValignment(VPos.BOTTOM);
            bottomPaneConstraints.setMinHeight(35);
            bottomPaneConstraints.setMaxHeight(35);
            layout.getRowConstraints().addAll(bottomPaneConstraints, new RowConstraints());
            layout.add(bottomBar.getLayout(), 0, 2);
            bottomBar.init(this);
        }
        layout.add(this.panelContent, 0, 1);
        GridPane.setVgrow(this.panelContent, Priority.ALWAYS);
        GridPane.setHgrow(this.panelContent, Priority.ALWAYS);

        this.stage.show();
    }

    public void showPanel(IPanel panel) {
        this.panelContent.getChildren().clear();
        this.panelContent.getChildren().add(panel.getLayout());

        if (panel.getStylesheetPath() != null) {
            this.stage.getScene().getStylesheets().addAll(panel.getStylesheetPath(), bottomBar.getStylesheetPath(), topBar.getStylesheetPath());
        }

        panel.init(this);
        panel.onShow();
    }

    public Stage getStage() {
        return stage;
    }
}
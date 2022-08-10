package com.workerai.launcher.ui.panels;

import com.goxr3plus.fxborderlessscene.borderless.BorderlessScene;
import com.noideaindustry.jui.JuiInitialization;
import com.workerai.launcher.ui.utils.IPanel;
import com.workerai.launcher.ui.panels.partials.BottomBar;
import com.workerai.launcher.ui.panels.partials.TopBar;
import com.workerai.launcher.utils.WindowMoveManager;
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
    public static final double WINDOW_WIDTH = 1260.d;
    public static final double WINDOW_HEIGHT = 750.d;
    public static final double TOP_BAR_HEIGHT = 35;

    public PanelManager(Stage stage) {
        this.stage = stage;
    }

    public void init() {
        JuiInitialization.setFxStage(this.stage);

        this.stage.setTitle("WorkerAI - Launcher");
        this.stage.centerOnScreen();
        this.stage.setWidth(WINDOW_WIDTH);
        this.stage.setHeight(WINDOW_HEIGHT);
        this.stage.getIcons().add(new Image(ResourceManager.getIcon()));

//        this.stage.heightProperty().addListener((observable, oldValue, newValue) -> {
//            if (newValue.doubleValue() != 750d)
//                this.stage.setHeight(750d);
//        });

        GridPane layout = new GridPane();
        if (Platform.isOnLinux()) {
            Scene scene = new Scene(layout);
            this.stage.setScene(scene);
        } else {
            this.stage.initStyle(StageStyle.UNDECORATED);

            BorderlessScene scene = new BorderlessScene(this.stage, StageStyle.UNDECORATED, layout);
//            scene.setMoveControl(topBar.getLayout());
            scene.removeDefaultCSS();
            scene.setResizable(false);

            this.stage.setScene(scene);
            WindowMoveManager.addResizeListener(this.stage);

            RowConstraints topPaneConstraints = new RowConstraints();
            topPaneConstraints.setValignment(VPos.TOP);
            topPaneConstraints.setMinHeight(TOP_BAR_HEIGHT);
            topPaneConstraints.setMaxHeight(TOP_BAR_HEIGHT);
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
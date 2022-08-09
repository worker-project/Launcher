package com.workerai.launcher.utils;

import com.workerai.launcher.ui.panels.PanelManager;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ScrollBar;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * Created by Arinonia on 07/03/2020 inside the package - fr.arinonia.arilibfx.ui.utils
 * @see <a href="https://github.com/Arinonia/AriLibFX/blob/master/src/main/java/fr/arinonia/arilibfx/ui/utils/ResizeHelper.java">...</a>
 */
public class MoveHelper {
    static boolean isScrollbar = false;

    public static void addResizeListener(Stage stage) {
        addResizeListener(stage, 1, 1, Double.MAX_VALUE, Double.MAX_VALUE);
    }

    public static void addResizeListener(Stage stage, double minWidth, double minHeight, double maxWidth, double maxHeight) {
        ResizeListener resizeListener = new ResizeListener(stage);

        stage.getScene().addEventHandler(MouseEvent.MOUSE_MOVED, resizeListener);
        stage.getScene().addEventHandler(MouseEvent.MOUSE_PRESSED, resizeListener);
        stage.getScene().addEventHandler(MouseEvent.MOUSE_DRAGGED, resizeListener);
        stage.getScene().addEventHandler(MouseEvent.MOUSE_EXITED, resizeListener);
        stage.getScene().addEventHandler(MouseEvent.MOUSE_EXITED_TARGET, resizeListener);

        ObservableList<Node> children = stage.getScene().getRoot().getChildrenUnmodifiable();
        for (Node child : children) {
            if (child instanceof ScrollBar) {
                isScrollbar = true;
            } else {
                isScrollbar = false;
                addListenerDeeply(child, resizeListener);
            }
        }
    }

    private static void addListenerDeeply(Node node, EventHandler<MouseEvent> listener) {
        node.addEventHandler(MouseEvent.MOUSE_MOVED, listener);
        node.addEventHandler(MouseEvent.MOUSE_PRESSED, listener);
        node.addEventHandler(MouseEvent.MOUSE_DRAGGED, listener);
        node.addEventHandler(MouseEvent.MOUSE_EXITED, listener);
        node.addEventHandler(MouseEvent.MOUSE_EXITED_TARGET, listener);
        if (node instanceof Parent) {
            Parent parent = (Parent) node;
            ObservableList<Node> children = parent.getChildrenUnmodifiable();
            for (Node child : children) {
                if (child instanceof ScrollBar) {
                    isScrollbar = true;
                } else if (!(child instanceof ScrollBar)) {
                    isScrollbar = false;
                    addListenerDeeply(child, listener);
                }
            }
        }
    }

    static class ResizeListener implements EventHandler<MouseEvent> {
        private final Stage stage;
        private double startX = 0, startY = 0;
        private double screenOffsetX = 0, screenOffsetY = 0;

        public ResizeListener(Stage stage) {
            this.stage = stage;
        }

        @Override
        public void handle(MouseEvent mouseEvent) {
            EventType<? extends MouseEvent> mouseEventType = mouseEvent.getEventType();

            if (MouseEvent.MOUSE_PRESSED.equals(mouseEventType)) {
                startX = stage.getWidth() - mouseEvent.getSceneX();
                startY = stage.getHeight() - mouseEvent.getSceneY();
            }

            if (MouseEvent.MOUSE_PRESSED.equals(mouseEventType)) {
                screenOffsetX = stage.getX() - mouseEvent.getScreenX();
                screenOffsetY = stage.getY() - mouseEvent.getScreenY();
            }

            if (MouseEvent.MOUSE_DRAGGED.equals(mouseEventType)) {
                if (startY > PanelManager.WINDOW_HEIGHT - PanelManager.TOP_BAR_HEIGHT) {
                    stage.setX(mouseEvent.getScreenX() + screenOffsetX);
                    stage.setY(mouseEvent.getScreenY() + screenOffsetY);
                }
            }
        }
    }
}

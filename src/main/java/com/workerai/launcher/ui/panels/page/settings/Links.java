package com.workerai.launcher.ui.panels.page.settings;

import com.workerai.launcher.App;
import com.workerai.launcher.ui.panels.page.Settings;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import fr.theshark34.openlauncherlib.util.Saver;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Links extends Settings {
    public void init(Rectangle linksContainer, GridPane pane, Saver saver) {
        Label linksLabel = new Label();
        linksLabel.getStyleClass().add("afterLaunch-label");
        linksLabel.setText("Quick Links");
        setCenterH(linksLabel);
        setTop(linksLabel);
        linksLabel.setTranslateX(linksContainer.getTranslateX() - 440);
        linksLabel.setTranslateY(linksContainer.getTranslateY() + 15d);
        pane.getChildren().add(linksLabel);

        FontAwesomeIconView linksIcon = new FontAwesomeIconView(FontAwesomeIcon.SEND);
        linksIcon.setFill(Color.WHITE);
        linksIcon.setSize("25px");
        setCenterH(linksIcon);
        setTop(linksIcon);
        linksIcon.setTranslateX(linksLabel.getTranslateX() - 85d);
        linksIcon.setTranslateY(linksLabel.getTranslateY() + 5d);
        pane.getChildren().add(linksIcon);

        Label linksSubLab = new Label();
        linksSubLab.getStyleClass().add("afterLaunch-subLabel");
        linksSubLab.setText("Need help? Have a look at our support section.");
        setTop(linksSubLab);
        setCenterH(linksSubLab);
        linksSubLab.setTranslateX(linksLabel.getTranslateX() - 15);
        linksSubLab.setTranslateY(linksLabel.getTranslateY() + 35d);
        pane.getChildren().add(linksSubLab);

        createContainer(200,200, 350, 430);

        Button supportButton = new Button();
        supportButton.getStyleClass().add("directory-button");
        supportButton.setText("Support");
        supportButton.setMaxWidth(125d);
        supportButton.setMaxHeight(30d);
        setTop(supportButton);
        setCenterH(supportButton);
        supportButton.setTranslateX(linksSubLab.getTranslateX() - 70d);
        supportButton.setTranslateY(linksSubLab.getTranslateY() + 27d);
        pane.getChildren().add(supportButton);

        supportButton.setOnMouseEntered(e -> App.getInstance().getPanelManager().getStage().getScene().setCursor(Cursor.HAND));
        supportButton.setOnMouseExited(e -> App.getInstance().getPanelManager().getStage().getScene().setCursor(Cursor.DEFAULT));
        supportButton.setOnMouseClicked(e -> {
            Desktop desktop = Desktop.getDesktop();
            try {
                desktop.open(new File((App.getInstance().getLauncherDirectory().toFile().getAbsoluteFile() + "\\launcher.log")));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        FontAwesomeIconView helpIcon = new FontAwesomeIconView(FontAwesomeIcon.LIFE_RING);
        helpIcon.setFill(Color.WHITE);
        helpIcon.setSize("16px");
        helpIcon.setTranslateX(-4d);
        supportButton.setGraphic(helpIcon);

        Button supportsButton = new Button();
        supportsButton.getStyleClass().add("directory-button");
        supportsButton.setText("FAQ");
        supportsButton.setMaxWidth(125d);
        supportsButton.setMaxHeight(30d);
        setTop(supportsButton);
        setCenterH(supportsButton);
        supportsButton.setTranslateX(linksSubLab.getTranslateX() + 70d);
        supportsButton.setTranslateY(linksSubLab.getTranslateY() + 27d);
        pane.getChildren().add(supportsButton);

        supportsButton.setOnMouseEntered(e -> App.getInstance().getPanelManager().getStage().getScene().setCursor(Cursor.HAND));
        supportsButton.setOnMouseExited(e -> App.getInstance().getPanelManager().getStage().getScene().setCursor(Cursor.DEFAULT));
        supportsButton.setOnMouseClicked(e -> {

        });

        FontAwesomeIconView questionIcon = new FontAwesomeIconView(FontAwesomeIcon.QUESTION);
        questionIcon.setFill(Color.WHITE);
        questionIcon.setSize("20px");
        questionIcon.setTranslateX(-4d);
        questionIcon.setTranslateY(-0.95d);
        supportsButton.setGraphic(questionIcon);
    }
}

package com.workerai.launcher.ui.panels.pages.settings;

import com.workerai.launcher.App;
import com.workerai.launcher.ui.panels.pages.Settings;
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

public class Legal extends Settings {
    public void init(Rectangle legalContainer, GridPane pane, Saver saver) {
        Label legalLabel = new Label();
        legalLabel.getStyleClass().add("afterLaunch-label");
        legalLabel.setText("Legal");
        setCenterH(legalLabel);
        setTop(legalLabel);
        legalLabel.setTranslateX(legalContainer.getTranslateX() - 440);
        legalLabel.setTranslateY(legalContainer.getTranslateY() + 15d);
        pane.getChildren().add(legalLabel);

        FontAwesomeIconView legalIcon = new FontAwesomeIconView(FontAwesomeIcon.BOOK);
        legalIcon.setFill(Color.WHITE);
        legalIcon.setSize("25px");
        setCenterH(legalIcon);
        setTop(legalIcon);
        legalIcon.setTranslateX(legalLabel.getTranslateX() - 50d);
        legalIcon.setTranslateY(legalLabel.getTranslateY() + 5d);
        pane.getChildren().add(legalIcon);

        Label legalSubLab = new Label();
        legalSubLab.getStyleClass().add("afterLaunch-subLabel");
        legalSubLab.setText("Have a look at our terms of service & third-party licenses.");
        setTop(legalSubLab);
        setCenterH(legalSubLab);
        legalSubLab.setTranslateX(legalLabel.getTranslateX() - 15);
        legalSubLab.setTranslateY(legalLabel.getTranslateY() + 35d);
        pane.getChildren().add(legalSubLab);

        createContainer(200,200, 350, 430);

        Button openTermsButton = new Button();
        openTermsButton.getStyleClass().add("directory-button");
        openTermsButton.setText("Terms");
        openTermsButton.setMaxWidth(125d);
        openTermsButton.setMaxHeight(30d);
        setTop(openTermsButton);
        setCenterH(openTermsButton);
        openTermsButton.setTranslateX(legalSubLab.getTranslateX() - 70d);
        openTermsButton.setTranslateY(legalSubLab.getTranslateY() + 27d);
        pane.getChildren().add(openTermsButton);

        openTermsButton.setOnMouseEntered(e -> App.getInstance().getPanelManager().getStage().getScene().setCursor(Cursor.HAND));
        openTermsButton.setOnMouseExited(e -> App.getInstance().getPanelManager().getStage().getScene().setCursor(Cursor.DEFAULT));
        openTermsButton.setOnMouseClicked(e -> {
            Desktop desktop = Desktop.getDesktop();
            try {
                desktop.open(new File((App.getInstance().getLauncherDirectory().toFile().getAbsoluteFile() + "\\launcher.log")));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        FontAwesomeIconView termsIcon = new FontAwesomeIconView(FontAwesomeIcon.BALANCE_SCALE);
        termsIcon.setFill(Color.WHITE);
        termsIcon.setSize("16px");
        termsIcon.setTranslateX(-2d);
        openTermsButton.setGraphic(termsIcon);

        Button openLicensesButton = new Button();
        openLicensesButton.getStyleClass().add("directory-button");
        openLicensesButton.setText("Licenses");
        openLicensesButton.setMaxWidth(125d);
        openLicensesButton.setMaxHeight(30d);
        setTop(openLicensesButton);
        setCenterH(openLicensesButton);
        openLicensesButton.setTranslateX(legalSubLab.getTranslateX() + 70d);
        openLicensesButton.setTranslateY(legalSubLab.getTranslateY() + 27d);
        pane.getChildren().add(openLicensesButton);

        openLicensesButton.setOnMouseEntered(e -> App.getInstance().getPanelManager().getStage().getScene().setCursor(Cursor.HAND));
        openLicensesButton.setOnMouseExited(e -> App.getInstance().getPanelManager().getStage().getScene().setCursor(Cursor.DEFAULT));
        openLicensesButton.setOnMouseClicked(e -> {

        });

        FontAwesomeIconView licenseIcon = new FontAwesomeIconView(FontAwesomeIcon.SHARE_ALT);
        licenseIcon.setFill(Color.WHITE);
        licenseIcon.setSize("16px");
        licenseIcon.setTranslateX(-4.5d);
        openLicensesButton.setGraphic(licenseIcon);
    }
}

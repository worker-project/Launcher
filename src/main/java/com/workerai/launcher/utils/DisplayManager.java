package com.workerai.launcher.utils;

import com.noideaindustry.jui.JuiInterface;
import com.workerai.launcher.App;
import com.workerai.launcher.database.Account;
import com.workerai.launcher.database.Requests;
import com.workerai.launcher.database.Response;
import com.workerai.launcher.savers.AccountSaver;
import com.workerai.launcher.ui.panels.pages.Accounts;
import com.workerai.launcher.ui.panels.pages.Login;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;

import java.util.Objects;

import static com.noideaindustry.jui.JuiGeometry.JuiCircle.createStrokeCircle;
import static com.noideaindustry.jui.JuiInterface.JuiButton.createFontButton;
import static com.noideaindustry.jui.JuiInterface.JuiPane.createScrollPane;
import static com.noideaindustry.jui.JuiInterface.createImageView;
import static com.noideaindustry.jui.JuiInterface.createLabel;
import static javafx.scene.control.ScrollPane.ScrollBarPolicy.ALWAYS;
import static javafx.scene.control.ScrollPane.ScrollBarPolicy.NEVER;

public class DisplayManager {
    public static void displayAccount(GridPane container, Account account, StackPane card) {
        container.getChildren().add(card);

        createLabel(0, 5d, "Account - " + account.getUsername(), null, "account-label", Pos.TOP_CENTER, card);
        createLabel(45d, 45d, "Available modules", null, "module-label", Pos.TOP_CENTER, card);

        FontAwesomeIconView iconRemove = JuiInterface.JuiIcon.createFontIcon(-4d, 0, FontAwesomeIcon.CROSSHAIRS, "20px", null, Color.WHITE, card);
        Button buttonRemove = createFontButton(15d, -10d, 150d, 30d, "Remove", "account-button-remove", null, iconRemove, Pos.BOTTOM_LEFT, card);
        buttonRemove.setOnMouseClicked(e -> {
            if (AccountSaver.getCurrentAccount() == null || !account.getUuid().equals(AccountSaver.getCurrentAccount().getUuid())) {
                Requests.removeAccount(account.getUuid());
                AccountSaver.removeAccount(account);
                App.getInstance().getPanelManager().showPanel(new Accounts());
            } else {
                Requests.removeAccount(account.getUuid());
                AccountSaver.removeAccount(account);
                App.getInstance().getPanelManager().showPanel(new Login());
            }
        });

        FontAwesomeIconView playIcon = JuiInterface.JuiIcon.createFontIcon(-4d, 0, FontAwesomeIcon.GAMEPAD, "18px", null, Color.WHITE, card);
        Button buttonPlay = createFontButton(-15d, -10d, 150d, 30d, "Play", "account-button-play", null, playIcon, Pos.BOTTOM_RIGHT, card);
        buttonPlay.setOnMouseClicked(e -> {

        });

        createImageView(20d, 0, 70d, 0d, true, "https://minotar.net/avatar/" + (account.getUuid() + ".png"), Pos.CENTER_LEFT, card);

        VBox moduleBoxContent = new VBox();
        moduleBoxContent.setSpacing(10);
        moduleBoxContent.setPadding(new Insets(10));

        createScrollPane(-16d, 6d, 230d, 60d, "scroll-pane", ALWAYS, NEVER, false, true, true, moduleBoxContent, Pos.CENTER_RIGHT, card);

        displayModules(moduleBoxContent, account, card);
    }

    public static void displayModules(VBox moduleBox, Account account, StackPane card) {
        card.getChildren().add(moduleBox);

        new Thread(() -> {
            Response response = Response.getResponse(account.getUuid());

            Platform.runLater(() -> {
                createModuleDisplay(new Label("Automine"), moduleBox, 11.5d, 7d * -1, response.hasAutomine());
                createModuleDisplay(new Label("Foraging"), moduleBox, 86.5d, 28.25d * -2, response.hasForage());
                createModuleDisplay(new Label("Fishing"), moduleBox, 156.5d, 26.25d * -4, false);

                createModuleDisplay(new Label("Farming"), moduleBox, 11.5d, 28.075d * 2 * -2.45, false);
                createModuleDisplay(new Label("DungeonHunting"), moduleBox, 78.5d, 25.75d * 2 * -3.625, false);

                createModuleDisplay(new Label("BazaarFlipping"), moduleBox, 11.5d, 26.75d * 3 * -2.725, false);
                createModuleDisplay(new Label("ZealotHunting"), moduleBox, 116.5d, 25.75d * 3 * -3.46, false);
            });
        }).start();
    }

    private static void createModuleDisplay(Label label, VBox moduleBox, double posX, double posY, boolean hasAccess) {
        label.getStyleClass().add("module-label");
        moduleBox.getChildren().add(label);

        label.setTranslateX(posX);
        label.setTranslateY(posY);

        Circle signalModule = createStrokeCircle(5d, 2, 10, StrokeType.CENTERED, Color.GRAY, Color.RED);
        if (hasAccess) signalModule = createStrokeCircle(5d, 2, 10, StrokeType.CENTERED, Color.GRAY, Color.GREEN);

        signalModule.setTranslateX(label.getTranslateX() - 15d);
        signalModule.setTranslateY(label.getTranslateY() - 24d);

        moduleBox.getChildren().add(signalModule);
    }
}

package com.workerai.launcher.utils;

import com.workerai.launcher.App;
import com.workerai.launcher.database.Account;
import com.workerai.launcher.database.Requests;
import com.workerai.launcher.database.Response;
import com.workerai.launcher.savers.AccountSaver;
import com.workerai.launcher.ui.panels.pages.Accounts;
import com.workerai.launcher.ui.panels.pages.Login;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import static com.noideaindustry.jui.JuiGeometry.JuiCircle.createStrokeCircle;
import static com.noideaindustry.jui.JuiInterface.JuiButton.createFontButton;
import static com.noideaindustry.jui.JuiInterface.JuiButton.createMaterialButton;
import static com.noideaindustry.jui.JuiInterface.JuiIcon.createDesignIcon;
import static com.noideaindustry.jui.JuiInterface.JuiIcon.createFontIcon;
import static com.noideaindustry.jui.JuiInterface.JuiPane.createScrollPane;
import static com.noideaindustry.jui.JuiInterface.JuiPane.createStackPane;
import static com.noideaindustry.jui.JuiInterface.createImageView;
import static com.noideaindustry.jui.JuiInterface.createLabel;
import static javafx.scene.control.ScrollPane.ScrollBarPolicy.ALWAYS;
import static javafx.scene.control.ScrollPane.ScrollBarPolicy.NEVER;

public class DisplayManager {

    public static void displayBanner(Pane container, Pane card) {
        container.getChildren().add(card);
    }

    public static void displayNews(Pane container, Pane card) {
        container.getChildren().add(card);

        displayNews(container, -360d, new NewsManager.News("WorkerAI News", "https://noideaindustry.com/Minecraft", "Looking to test our available modules?", "Have a look at our free trials on our website!", ResourceManager.getMinecraftIcon()));
        displayNews(container, 0d, new NewsManager.News("WorkerClient News", "https://noideaindustry.com/Mojang", "Want to control our modules via discord?", "Check our plans to get access to this feature.", ResourceManager.getMinecraftIcon()));
        displayNews(container, 360d, new NewsManager.News("WorkerAI News", "https://noideaindustry.com/Microsoft", "Becoming one of our partner?", "Apply on our website and get free advantages.", ResourceManager.getMinecraftIcon()));
    }

    public static void displayNews(Pane container, double posX, NewsManager.News news) {
        Pane card = createStackPane(posX, 112.5d, 340d, 245d, 15d, 15d, true, null, null, Color.rgb(32, 31, 29));
        displayBanner(container, card);

        FontAwesomeIconView newsIcon = createFontIcon(-4d, 0d, FontAwesomeIcon.NEWSPAPER_ALT, "25px", null, Color.WHITE, null);
        createLabel(0d, -100d, news.getName(), newsIcon, "news-Label", null, card);
        createImageView(0d, -5d, 340d, 350d, true, news.getPreview(), null, card);

        createLabel(15d, 85d, news.getDescription(), null, "news-desc-Label", Pos.CENTER_LEFT, card);
        createLabel(15d, 100d, news.getSubDescription(), null, "news-desc-Label", Pos.CENTER_LEFT, card);

        MaterialDesignIconView readIcon = createDesignIcon(0d, 0d, MaterialDesignIcon.BOOK_OPEN_PAGE_VARIANT, "15px", null, Color.WHITE, null);
        Button readNews = createMaterialButton(140d, 95d, 0d, 0d, null, "news-button", null, readIcon, null, card);
        readNews.setOnMouseClicked(e -> {
            try {
                Desktop.getDesktop().browse(new URI(news.getUrl()));
            } catch (IOException | URISyntaxException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    public static void displayAccount(GridPane container, Account account, StackPane card) {
        container.getChildren().add(card);

        createLabel(0, 5d, "Account - " + account.getUsername(), null, "account-label", Pos.TOP_CENTER, card);
        createLabel(45d, 45d, "Available modules", null, "module-label", Pos.TOP_CENTER, card);

        FontAwesomeIconView iconRemove = createFontIcon(-4d, 0, FontAwesomeIcon.CROSSHAIRS, "20px", null, Color.WHITE, card);
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

        FontAwesomeIconView playIcon = createFontIcon(-4d, 0, FontAwesomeIcon.GAMEPAD, "18px", null, Color.WHITE, card);
        Button buttonPlay = createFontButton(-15d, -10d, 150d, 30d, "Play", "account-button-play", null, playIcon, Pos.BOTTOM_RIGHT, card);
        buttonPlay.setOnMouseClicked(e -> {
            AccountSaver.setCurrentAccount(account);
            PlayManager.downloadAndPlay(card);
        });

        createImageView(20d, 0, 70d, 0d, true, "https://minotar.net/avatar/" + (account.getUuid() + ".png"), Pos.CENTER_LEFT, card);

        VBox moduleBoxContent = new VBox();
        moduleBoxContent.setSpacing(10);
        moduleBoxContent.setPadding(new Insets(10));

        createScrollPane(-16d, 6d, 230d, 60d, "scroll-pane", ALWAYS, NEVER, false, true, true, moduleBoxContent, Pos.CENTER_RIGHT, card);

        card.getChildren().add(moduleBoxContent);

        new Thread(() -> {
            Response response = Response.getResponse(account.getUuid());
            Platform.runLater(() -> {
                createModuleDisplay(new Label("Automine"), moduleBoxContent, 11.5d, 7d * -1, response.hasAutomine());
                createModuleDisplay(new Label("Foraging"), moduleBoxContent, 86.5d, 28.25d * -2, response.hasForage());
                createModuleDisplay(new Label("Fishing"), moduleBoxContent, 156.5d, 26.25d * -4, false);

                createModuleDisplay(new Label("Farming"), moduleBoxContent, 11.5d, 28.075d * 2 * -2.45, false);
                createModuleDisplay(new Label("DungeonHunting"), moduleBoxContent, 78.5d, 25.75d * 2 * -3.625, false);

                createModuleDisplay(new Label("BazaarFlipping"), moduleBoxContent, 11.5d, 26.75d * 3 * -2.725, false);
                createModuleDisplay(new Label("ZealotHunting"), moduleBoxContent, 116.5d, 25.75d * 3 * -3.46, false);
            });
        }).start();
    }

    static void createModuleDisplay(Label label, VBox moduleBox, double posX, double posY, boolean hasAccess) {
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

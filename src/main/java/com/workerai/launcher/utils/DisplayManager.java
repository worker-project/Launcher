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
import com.workerai.launcher.utils.NewsManager.News;
import javafx.scene.text.Text;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import static com.noideaindustry.jui.JuiGeometry.JuiCircle.createStrokeCircle;
import static com.noideaindustry.jui.JuiInterface.*;
import static com.noideaindustry.jui.JuiInterface.JuiButton.createFontButton;
import static com.noideaindustry.jui.JuiInterface.JuiButton.createMaterialButton;
import static com.noideaindustry.jui.JuiInterface.JuiIcon.createDesignIcon;
import static com.noideaindustry.jui.JuiInterface.JuiIcon.createFontIcon;
import static com.noideaindustry.jui.JuiInterface.JuiPane.createScrollPane;
import static com.noideaindustry.jui.JuiInterface.JuiPane.createStackPane;
import static com.workerai.launcher.utils.TextHelper.getTextWidth;
import static javafx.scene.control.ScrollPane.ScrollBarPolicy.ALWAYS;
import static javafx.scene.control.ScrollPane.ScrollBarPolicy.NEVER;

public class DisplayManager {

    public static void displayBanner(Pane container, Pane card) {
        container.getChildren().add(card);
    }

    public static void displayNews(Pane container, Pane card) {
        container.getChildren().add(card);

        displayNews(container, -360d, NewsManager.getNewsList(0));
        displayNews(container, 0d, NewsManager.getNewsList(1));
        displayNews(container, 360d, NewsManager.getNewsList(2));
    }

    static void displayNews(Pane container, double posX, News news) {
        Pane card = createStackPane(posX, 112.5d, 340d, 245d, 15d, 15d, true, null, null, Color.rgb(32, 31, 29));
        displayBanner(container, card);

        FontAwesomeIconView newsIcon = createFontIcon(-4d, 0d, FontAwesomeIcon.NEWSPAPER_ALT, "25px", null, Color.WHITE, null);
        createLabel(0d, -100d, news.getName(), newsIcon, "news-Label", null, card);
        createImageView(0d, -5d, 340d, 350d, true, news.getPreview(), null, card);

        createLabel(15d, 85d, news.getDescription(), null, "news-desc-Label", Pos.CENTER_LEFT, card);
        createLabel(15d, 100d, news.getSubDescription(), null, "news-desc-Label", Pos.CENTER_LEFT, card);

        MaterialDesignIconView readIcon = createDesignIcon(0d, -1d, MaterialDesignIcon.BOOK_OPEN_PAGE_VARIANT, "20px", null, Color.rgb(210,144,52), null);
        Button readNews = createMaterialButton(140d, 95d, 0d, 0d, null, "news-button", null, readIcon, null, card);
        readNews.setOnMouseClicked(e -> {
            try {
                Desktop.getDesktop().browse(new URI(news.getUrl()));
            } catch (IOException | URISyntaxException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    public static void displayFullAccount(Pane container, Account account, Pane card) {
        container.getChildren().add(card);

        createImageView(20d, 0, 70d, 0d, true, "https://minotar.net/avatar/" + (account.getUuid() + ".png"), Pos.CENTER_LEFT, card);

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

        displayFullModules(container, account, card);
    }

    public static void displayReducedAccount(Pane container, Account account, Pane card) {
        container.getChildren().add(card);

        createImageView(20d, 0d, 70d, 0d, true, !App.isDebugMode() ? "https://minotar.net/avatar/" + (account.getUuid() + ".png") : "https://minotar.net/avatar/MHF_Steve", Pos.CENTER_LEFT, card);
        createLabel(0d, 10d, "Current session", null, "account-label", Pos.TOP_CENTER, card);
        createLabel(0d, 30d, "Username - " + account.getUsername(), null, "account-label", Pos.TOP_CENTER, card);
    }

    public static void displayFullModules(Pane container, Account account, Pane card) {
        if(!container.getChildren().contains(card)) {
            container.getChildren().add(card);
        }

        VBox box = new VBox();
        box.setSpacing(10);
        box.setPadding(new Insets(10));

        new Thread(() -> {
            Response response = Response.getResponse(account.getUuid());
            Platform.runLater(() -> {
                /*createModuleDisplay(new Label("Automine"), box, 11.5d, 7d * -1, response.hasAutomine());
                createModuleDisplay(new Label("Foraging"), box, 86.5d, 28.25d * -2, response.hasForage());
                createModuleDisplay(new Label("Fishing"), box, 156.5d, 26.25d * -4, false);

                createModuleDisplay(new Label("Farming"), box, 11.5d, 28.075d * 2 * -2.45, false);
                createModuleDisplay(new Label("DungeonHunting"), box, 78.5d, 25.75d * 2 * -3.625, false);

                createModuleDisplay(new Label("BazaarFlipping"), box, 11.5d, 26.75d * 3 * -2.725, false);
                createModuleDisplay(new Label("ZealotHunting"), box, 116.5d, 25.75d * 3 * -3.46, false);*/

                createScrollPane(-16d, 6d, 230d, 60d, "scroll-pane", ALWAYS, NEVER, false, true, true, box, Pos.CENTER_RIGHT, card);
            });
        }).start();
    }
    public static void displayReducedModules(Pane container, Account account, Pane card) {
        if(!container.getChildren().contains(card)) {
            container.getChildren().add(card);
        }

        createLabel(0d, 10d, "Current modules", null, "account-label", Pos.TOP_CENTER, card);

        new Thread(() -> {
            Response response = Response.getResponse(account.getUuid());
            Platform.runLater(() -> {
                createModuleDisplay(-120d, 5d, new Label("AutomineAI"), card,response.hasAutomine());
                createModuleDisplay(-20d, 5d, new Label("ForagingAI"), card,response.hasForage());
                createModuleDisplay(80d, 5d, new Label("FishingAI"), card,false);

                createModuleDisplay(-120d, 25d, new Label("FarmingAI"), card,false);
                createModuleDisplay(-20d, 25d, new Label("DungeonAI"), card,false);
                createModuleDisplay(80d, 25d, new Label("BazaarAI"), card,false);

                createModuleDisplay(200d, 0d, new Label("ZealotAI"), card,false);
            });
        }).start();
    }

    static void createModuleDisplay(double posX, double posY, Label label, Pane card, boolean hasAccess) {
        label.getStyleClass().add("module-label");
        card.getChildren().add(label);

        label.setTranslateX(posX);
        label.setTranslateY(posY);

        final double textWidth = getTextWidth(label.getText());

        createRegion(posX, posY, textWidth + 15d, 18d, "modules-container", card);

        createStrokeCircle(label.getTranslateX() - (textWidth*0.8), label.getTranslateY(), 5d, 2, 10, StrokeType.CENTERED, Color.rgb(210,144,52), hasAccess ? Color.rgb(210,144,52) : Color.rgb(52,52,52), card);
    }
}

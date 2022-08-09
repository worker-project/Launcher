package com.workerai.launcher.utils;

import com.workerai.launcher.WorkerLauncher;
import com.workerai.launcher.database.Account;
import com.workerai.launcher.database.Requests;
import com.workerai.launcher.savers.AccountManager;
import com.workerai.launcher.ui.panels.pages.Accounts;
import com.workerai.launcher.ui.panels.pages.Login;
import com.workerai.launcher.utils.NewsManager.News;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeType;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import static com.noideaindustry.jui.geometry.JuiCircle.createStrokeCircle;
import static com.noideaindustry.jui.components.JuiButton.createFontButton;
import static com.noideaindustry.jui.components.JuiButton.createMaterialButton;
import static com.noideaindustry.jui.components.JuiIcon.createAwesomeIcon;
import static com.noideaindustry.jui.components.JuiIcon.createDesignIcon;
import static com.noideaindustry.jui.components.JuiImageView.createImageView;
import static com.noideaindustry.jui.components.JuiLabel.createLabel;
import static com.noideaindustry.jui.components.JuiPane.createScrollPane;
import static com.noideaindustry.jui.components.JuiPane.createStackPane;
import static com.workerai.launcher.utils.LauncherInfos.LIGHT_BLACK;
import static com.workerai.launcher.utils.LauncherInfos.WHITE;

public class DisplayManager {

    public static void displayBanner(Pane container, Pane card) {
        container.getChildren().add(card);
    }

    public static void displayNews(Pane container, Pane card) {
        container.getChildren().add(card);

        createDisplayNews(container, -360d, NewsManager.getNewsList(0));
        createDisplayNews(container, 0d, NewsManager.getNewsList(1));
        createDisplayNews(container, 360d, NewsManager.getNewsList(2));
    }

    static void createDisplayNews(Pane container, double posX, News news) {
        Pane card = createStackPane(posX, 112.5d, 340d, 245d, true, LIGHT_BLACK);
        displayBanner(container, card);

        FontAwesomeIconView newsIcon = createAwesomeIcon(-4d, 0d, FontAwesomeIcon.NEWSPAPER_ALT, "25px", WHITE, null);
        createLabel(0d, -100d, news.name(), newsIcon, "news-Label", null, card);
        createImageView(0d, -5d, 340d, 350d, news.preview(), card);

        createLabel(15d, 85d, news.description(), null, "news-desc-Label", Pos.CENTER_LEFT, card);
        createLabel(15d, 100d, news.subDescription(), null, "news-desc-Label", Pos.CENTER_LEFT, card);

        MaterialDesignIconView readIcon = createDesignIcon(0d, -1d, MaterialDesignIcon.BOOK_OPEN_PAGE_VARIANT, "20px", Color.rgb(210, 144, 52), null);
        Button readNews = createMaterialButton(140d, 95d, 0d, 0d, true, "news-button", readIcon, card);
        readNews.setOnMouseClicked(e -> {
            try {
                Desktop.getDesktop().browse(new URI(news.url()));
            } catch (IOException | URISyntaxException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    public static void displayAccount(Pane container, Account account, Pane card, boolean isReduced) {
        container.getChildren().add(card);

        if (!isReduced) {
            createImageView(20d, 0, 70d, 0d, "https://minotar.net/avatar/" + (account.getUuid() + ".png"), Pos.CENTER_LEFT, card);

            createLabel(0, 5d, "Account - " + account.getUsername(), null, "account-label", Pos.TOP_CENTER, card);
            createLabel(45d, 45d, "Available modules", null, "module-label", Pos.TOP_CENTER, card);

            FontAwesomeIconView iconRemove = createAwesomeIcon(-4d, 0, FontAwesomeIcon.CROSSHAIRS, "20px", WHITE, card);
            Button buttonRemove = createFontButton(15d, -10d, 150d, 30d, "Remove", "account-button-remove", null, iconRemove, Pos.BOTTOM_LEFT, card);
            buttonRemove.setOnMouseClicked(e -> {
                if (AccountManager.getCurrentAccount() == null || !account.getUuid().equals(AccountManager.getCurrentAccount().getUuid())) {
                    AccountManager.removeLocalAccount(account);
                    Requests.removeRemoteAccount(account.getUuid());
                    WorkerLauncher.getInstance().getPanelManager().showPanel(new Accounts());
                } else {
                    AccountManager.removeCurrentAccount();
                    AccountManager.removeLocalAccount(account);
                    Requests.removeRemoteAccount(account.getUuid());
                    WorkerLauncher.getInstance().getPanelManager().showPanel(new Login());
                }
            });

            FontAwesomeIconView playIcon = createAwesomeIcon(-4d, 0, FontAwesomeIcon.GAMEPAD, "18px", WHITE, card);
            Button buttonPlay = createFontButton(-15d, -10d, 150d, 30d, "Play", "account-button-play", null, playIcon, Pos.BOTTOM_RIGHT, card);
            buttonPlay.setOnMouseClicked(e -> {
                AccountManager.setCurrentAccount(account);
                PlayManager.downloadAndPlay(card, false);
            });
        }

        createDisplayModules(container, account, card, isReduced);
    }

    static void createDisplayModules(Pane container, Account account, Pane card, boolean isReduced) {
        if (!container.getChildren().contains(card)) container.getChildren().add(card);

        if (isReduced) createLabel(0d, 0d, "Current modules", null, "account-label", Pos.TOP_CENTER, card);

        new Thread(() -> Platform.runLater(() -> {
            GridPane scrollContent = new GridPane();
            createModule("AutomineAI", account.getResponse().hasAutomine(), scrollContent, isReduced);
            createModule("ForagingAI", account.getResponse().hasAutomine(), scrollContent, isReduced);
            createModule("FishingAI", false, scrollContent, isReduced);
            createModule("FarmingAI", false, scrollContent, isReduced);
            createModule("DungeonAI", false, scrollContent, isReduced);
            createModule("BazaarAI", false, scrollContent, isReduced);
            createModule("Soon", false, scrollContent, isReduced);

            createScrollPane(-16d, isReduced ? 10d : 6d, isReduced ? 370d : 230d, isReduced ? 50d : 60d, "scroll-pane", scrollContent, card);
        })).start();
    }

    static void createModule(String moduleName, boolean hasAccess, GridPane parent, boolean isReduced) {
        GridPane scrollContent = new GridPane();
        scrollContent.setPadding(isReduced ? new Insets(2.5d, 1d, 1d, 2.5d) : new Insets(5d, 1d, 5d, 6d));
        scrollContent.setHgap(isReduced ? 5d : 10d);

        scrollContent.add(createStrokeCircle(isReduced ? 2d : 5d, 0d, 5d, 2d, 10d, StrokeType.CENTERED, Color.rgb(210, 144, 52), hasAccess ? Color.rgb(210, 144, 52) : Color.rgb(52, 52, 52), null), 0, 0);
        scrollContent.add(createLabel(0d, 0d, moduleName, null, "module-scrollLabel", null, null), 1, 0);
        int modulo = isReduced ? 4 : 2;
        parent.add(scrollContent, parent.getChildren().size() % modulo, parent.getChildren().size() / modulo);
    }

    public static void displayCurrentSession(Pane container, Account account, Pane card) {
        container.getChildren().add(card);

        createLabel(0d, 0d, "Current session", null, "account-label", Pos.TOP_CENTER, card);

        GridPane scrollContent = new GridPane();
        scrollContent.add(createImageView(10d, 4d, 40d, 0d, !WorkerLauncher.isDebugMode() ? "https://minotar.net/avatar/" + (account.getUuid() + ".png") : "https://minotar.net/avatar/MHF_Steve.png", Pos.CENTER_LEFT, null), 0, 0);
        scrollContent.add(createLabel(20d, 4d - 8d, "Username - " + account.getUsername(), null, "account-scrollLabel", null, null), 1, 0);
        scrollContent.add(createLabel(20d, 4d + 8d, "Uuid - " + account.getUuid(), null, "account-scrollLabel", null, null), 1, 0);

        createScrollPane(-16d, 10d, 370d, 50d, "scroll-pane", scrollContent, card);
    }
}

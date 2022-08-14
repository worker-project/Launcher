package com.workerai.launcher.ui.panels.pages;

import com.workerai.launcher.WorkerLauncher;
import com.workerai.launcher.database.Account;
import com.workerai.launcher.database.Requests;
import com.workerai.launcher.database.authentication.ModuleResponse;
import com.workerai.launcher.savers.AccountManager;
import com.workerai.launcher.ui.panels.PanelManager;
import com.workerai.launcher.ui.panels.partials.BottomBar;
import com.workerai.launcher.ui.utils.Panel;
import com.workerai.launcher.utils.ErrorManager;
import com.workerai.launcher.utils.ResourceManager;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import fr.litarvan.openauth.microsoft.MicrosoftAuthResult;
import fr.litarvan.openauth.microsoft.MicrosoftAuthenticationException;
import fr.litarvan.openauth.microsoft.MicrosoftAuthenticator;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.atomic.AtomicBoolean;

import static com.noideaindustry.jui.components.JuiButton.createFontButton;
import static com.noideaindustry.jui.components.JuiButton.createMaterialButton;
import static com.noideaindustry.jui.components.JuiField.createPasswordField;
import static com.noideaindustry.jui.components.JuiField.createTextField;
import static com.noideaindustry.jui.components.JuiIcon.createAwesomeIcon;
import static com.noideaindustry.jui.components.JuiIcon.createDesignIcon;
import static com.noideaindustry.jui.components.JuiImageView.createImageView;
import static com.noideaindustry.jui.components.JuiPane.createGridPane;
import static com.noideaindustry.jui.components.JuiPane.createStackPane;
import static com.workerai.launcher.utils.ColorManager.LIGHT_GRAY;

public class Login extends Panel {
    final AtomicBoolean isTryingToSignIn = new AtomicBoolean(false);
    Button signInButton, reportBugButton;

    PasswordField passwordField;
    TextField emailField;

    final boolean autoAuthentication;

    public Login(boolean autoAuthentication) {
        this.autoAuthentication = autoAuthentication;
    }

    @Override
    public void init(PanelManager panelManager) {
        super.init(panelManager);

        BottomBar.getInstance().setLoginIcons();

        GridPane backgroundPane = createGridPane("background-login");
        this.layout.getChildren().add(backgroundPane);
        setCanTakeAllSize(this.layout, backgroundPane);

        StackPane loginPane = createStackPane(0d, 0d, 300d, 120d, 0d, 0d, false, "login-panel", Pos.CENTER, Color.TRANSPARENT);
        this.layout.getChildren().add(loginPane);

        MaterialDesignIconView emailIcon = createDesignIcon(0d, 0d, MaterialDesignIcon.EMAIL, "22px", "logo-rectangle", LIGHT_GRAY, null);
        createMaterialButton(-175d, -50d, false, "logo-rectangle", emailIcon, loginPane);

        MaterialDesignIconView lockIcon = createDesignIcon(0d, 0d, MaterialDesignIcon.LOCK, "22px", "logo-rectangle", LIGHT_GRAY, null);
        createMaterialButton(-175d, -5d, false, "logo-rectangle", lockIcon, loginPane);

        emailField = createTextField(0d, -50d, 300d, 0d, "Account E-mail", "login-input", null, loginPane);
        emailField.setText("compte1@nxgroupe.com");
        passwordField = createPasswordField(0d, -5d, 300d, 0d, "Account Password", "login-input", loginPane);
        passwordField.setText("^B=*_&8nB8ssp-q+");

        createImageView(0d, 180d, 0d, 250d, ResourceManager.getIcon(), Pos.CENTER, loginPane);

        FontAwesomeIconView signIcon = createAwesomeIcon(-5d, 0, FontAwesomeIcon.SIGN_IN, "22px", LIGHT_GRAY, loginPane);
        signInButton = createFontButton(-490d, -302.5d + 15d, 135d, 0d, "SIGN IN", "login-button", null, signIcon, Pos.BOTTOM_RIGHT, loginPane);
        signInButton.setOnMouseClicked(e -> {
            if (!isTryingToSignIn.get()) {
                this.tryManualAuthenticationProcess(emailField.getText(), passwordField.getText());
            }
        });

        FontAwesomeIconView bugIcon = createAwesomeIcon(-5d, 0, FontAwesomeIcon.PAPER_PLANE, "22px", LIGHT_GRAY, loginPane);
        reportBugButton = createFontButton(490d, -302.5d + 15d, 135d, 0d, "REPORT BUG", "login-button", null, bugIcon, Pos.BOTTOM_LEFT, loginPane);
        reportBugButton.setOnMouseClicked(e -> {
            try {
                Desktop.getDesktop().browse(new URI("https://github.com/NoIdeaIndustry"));
            } catch (IOException | URISyntaxException ex) {
                throw new RuntimeException(ex);
            }
        });

        emailField.textProperty().addListener((_a, oldValue, newValue) -> signInButton.setDisable(emailField.getText().length() <= 0 || passwordField.getText().length() <= 0));
        passwordField.textProperty().addListener((_a, oldValue, newValue) -> signInButton.setDisable(emailField.getText().length() <= 0 || passwordField.getText().length() <= 0));

        this.disableLoginComponents(false);

        if(autoAuthentication) {
            tryAutoAuthenticationProcess();
        }
    }

    void tryAutoAuthenticationProcess() {
        this.tryingConnecting();

        new Thread(this::tryAutoAuthentication).start();
    }

    void tryAutoAuthentication() {
        if (AccountManager.getLocalAccounts().isEmpty()) {
            this.failedConnecting();
            return;
        }

        MicrosoftAuthenticator authenticator = new MicrosoftAuthenticator();
        try {
            WorkerLauncher.getInstance().getLogger().info("MicrosoftAuth | Trying resolving account information.");
            authenticator.loginWithRefreshToken(AccountManager.getLocalAccounts().get(0).getClientToken());

            AccountManager.setCurrentAccount(AccountManager.getLocalAccounts().get(0));

            WorkerLauncher.getInstance().getLogger().info("MicrosoftAuth | Automatically logged as " + "\"" + AccountManager.getCurrentAccount().getUsername() + "\"");
            Platform.runLater(() -> this.panelManager.showPanel(new Home()));
            this.panelManager.getStage().getScene().setCursor(Cursor.DEFAULT);
        } catch (MicrosoftAuthenticationException e) {
            WorkerLauncher.getInstance().getLogger().warn("MicrosoftAuth | Automatic authentication failed...");
            Platform.runLater(() -> ErrorManager.ShowError(
                    this.panelManager.getStage(),
                    "Authentication Error",
                    e.getMessage()));
            this.failedConnecting();
        }
    }

    void tryManualAuthenticationProcess(String email, String password) {
        this.tryingConnecting();

        new Thread(() -> this.tryManualAuthentication(email, password)).start();
    }

    void tryManualAuthentication(String email, String password) {
            MicrosoftAuthenticator authenticator = new MicrosoftAuthenticator();
            try {
                WorkerLauncher.getInstance().getLogger().info("MicrosoftAuth | Trying resolving account information.");
                MicrosoftAuthResult result = authenticator.loginWithCredentials(email, password);

                ModuleResponse moduleResponse = new ModuleResponse().getUserFromUuid(result.getProfile().getId());
                if (moduleResponse == null) {
                    this.failedConnecting();
                    Platform.runLater(() -> ErrorManager.ShowError(
                            this.panelManager.getStage(),
                            "Authentication Error",
                            "Your account isn't registered in our records!\nFeel free to contact us if you think this is a mistake.")
                    );
                    return;
                }

                Account account = Account.createAccount(result.getProfile().getName(), result.getProfile().getId(), result.getRefreshToken(), result.getAccessToken(), moduleResponse);
                AccountManager.setCurrentAccount(account);
                Requests.addRemoteAccount(account);

                WorkerLauncher.getInstance().getLogger().info("MicrosoftAuth | Manually logged as " + "\"" + AccountManager.getCurrentAccount().getUsername() + "\"");
                Platform.runLater(() -> this.panelManager.showPanel(new Home()));
                this.panelManager.getStage().getScene().setCursor(Cursor.DEFAULT);
            } catch (MicrosoftAuthenticationException e) {
                WorkerLauncher.getInstance().getLogger().warn("MicrosoftAuth | Failed resolving account information.");
                Platform.runLater(() -> ErrorManager.ShowError(
                        this.panelManager.getStage(),
                        "Authentication Error",
                        e.getMessage()));
                this.failedConnecting();
            }
    }

    void tryingConnecting() {
        this.disableLoginComponents(true);
        isTryingToSignIn.set(true);
        BottomBar.getInstance().disableBottomButtons(true);
        signInButton.getStyleClass().add("login-button-active");

        this.panelManager.getStage().getScene().setCursor(Cursor.WAIT);
    }

    void failedConnecting() {
        this.disableLoginComponents(false);
        isTryingToSignIn.set(false);
        BottomBar.getInstance().disableBottomButtons(false);
        signInButton.getStyleClass().remove("login-button-active");

        this.panelManager.getStage().getScene().setCursor(Cursor.DEFAULT);
    }

    void disableLoginComponents(boolean disabled) {
        reportBugButton.setDisable(disabled);
        signInButton.setDisable(disabled);

        emailField.setDisable(disabled);
        passwordField.setDisable(disabled);
    }

    @Override
    public String getStylesheetPath() {
        return ResourceManager.getLoginDesign();
    }
}
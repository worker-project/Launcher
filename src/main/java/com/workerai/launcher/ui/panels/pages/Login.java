package com.workerai.launcher.ui.panels.pages;

import com.workerai.launcher.database.Account;
import com.workerai.launcher.database.Requests;
import com.workerai.launcher.database.Response;
import com.workerai.launcher.savers.AccountManager;
import com.workerai.launcher.ui.PanelManager;
import com.workerai.launcher.ui.panels.partials.BottomBar;
import com.workerai.launcher.ui.utils.Panel;
import com.workerai.launcher.utils.AlertManager;
import com.workerai.launcher.utils.ResourceManager;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import fr.litarvan.openauth.AuthPoints;
import fr.litarvan.openauth.AuthenticationException;
import fr.litarvan.openauth.Authenticator;
import fr.litarvan.openauth.microsoft.MicrosoftAuthResult;
import fr.litarvan.openauth.microsoft.MicrosoftAuthenticationException;
import fr.litarvan.openauth.microsoft.MicrosoftAuthenticator;
import fr.litarvan.openauth.model.AuthAgent;
import fr.litarvan.openauth.model.response.AuthResponse;
import javafx.application.Platform;
import javafx.geometry.Pos;
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

import static com.noideaindustry.jui.JuiInterface.JuiButton.createFontButton;
import static com.noideaindustry.jui.JuiInterface.JuiButton.createMaterialButton;
import static com.noideaindustry.jui.JuiInterface.JuiField.createPasswordField;
import static com.noideaindustry.jui.JuiInterface.JuiField.createTextField;
import static com.noideaindustry.jui.JuiInterface.JuiIcon.createDesignIcon;
import static com.noideaindustry.jui.JuiInterface.JuiIcon.createFontIcon;
import static com.noideaindustry.jui.JuiInterface.JuiPane.createGridPane;
import static com.noideaindustry.jui.JuiInterface.JuiPane.createStackPane;
import static com.noideaindustry.jui.JuiInterface.createImageView;
import static com.workerai.launcher.utils.LauncherInfos.LIGHT_GRAY;

public class Login extends Panel {
    private final AtomicBoolean isTryingToSignIn = new AtomicBoolean(false);
    private Response response;
    private Button signIn;

    @Override
    public void init(PanelManager panelManager) {
        super.init(panelManager);

        BottomBar.getInstance().setLoginIcons();

        if (checkAuthentication()) {
            logger.info("Logged as " + "\"" + AccountManager.getCurrentAccount().getUsername() + "\"");
            this.panelManager.showPanel(new Home());
        } else {
            BottomBar.getInstance().enableDebugAccess();

            GridPane backgroundPane = createGridPane(0d, 0d, 0d, 0d, 0d, 0d, "background-login", Color.TRANSPARENT);
            this.layout.getChildren().add(backgroundPane);
            setCanTakeAllSize(this.layout, backgroundPane);

            StackPane loginPane = createStackPane(0d, 0d, 300d, 120d, 0d, 0d, false, "login-panel", Pos.CENTER, Color.TRANSPARENT);
            this.layout.getChildren().add(loginPane);

            MaterialDesignIconView emailIcon = createDesignIcon(0d, 0d, MaterialDesignIcon.EMAIL, "22px", "logo-rectangle", LIGHT_GRAY, null);
            ;
            createMaterialButton(-175d, -50d, 0d, 0d, false, null, "logo-rectangle", null, emailIcon, null, loginPane);

            MaterialDesignIconView lockIcon = createDesignIcon(0d, 0d, MaterialDesignIcon.LOCK, "22px", "logo-rectangle", LIGHT_GRAY, null);
            ;
            createMaterialButton(-175d, -5d, 0d, 0d, false, null, "logo-rectangle", null, lockIcon, null, loginPane);

            TextField userField = createTextField(0d, -50d, 300d, 0d, "Account E-mail", "login-input", null, loginPane);
            userField.setText("compte1@nxgroupe.com");
            PasswordField passwordField = createPasswordField(0d, -5d, 300d, 0d, "Account Password", "login-input", loginPane);
            passwordField.setText("^B=*_&8nB8ssp-q+");

            createImageView(0d, 180d, 0d, 250d, true, ResourceManager.getIcon(), Pos.CENTER, loginPane);

            FontAwesomeIconView signIcon = createFontIcon(-5d, 0, FontAwesomeIcon.SIGN_IN, "22px", null, Color.rgb(150, 150, 150), loginPane);
            signIn = createFontButton(-490d, -302.5d + 15d, 135d, 0d, "SIGN IN", "login-button", null, signIcon, Pos.BOTTOM_RIGHT, loginPane);
            signIn.setOnMouseClicked(e -> {
                if (!isTryingToSignIn.get()) {
                    tryingConnecting();
                    this.authenticateMicrosoft(userField.getText(), passwordField.getText());
                }
            });

            FontAwesomeIconView bugIcon = createFontIcon(-5d, 0, FontAwesomeIcon.PAPER_PLANE, "22px", null, Color.rgb(150, 150, 150), loginPane);
            Button reportBug = createFontButton(490d, -302.5d + 15d, 135d, 0d, "REPORT BUG", "login-button", null, bugIcon, Pos.BOTTOM_LEFT, loginPane);
            reportBug.setOnMouseClicked(e -> {
                try {
                    Desktop.getDesktop().browse(new URI("https://github.com/NoIdeaIndustry"));
                } catch (IOException | URISyntaxException ex) {
                    throw new RuntimeException(ex);
                }
            });

            userField.textProperty().addListener((_a, oldValue, newValue) -> signIn.setDisable(userField.getText().length() <= 0 || passwordField.getText().length() <= 0));
            passwordField.textProperty().addListener((_a, oldValue, newValue) -> signIn.setDisable(userField.getText().length() <= 0 || passwordField.getText().length() <= 0));
        }
    }

    private boolean checkAuthentication() {
        /*if(App.getInstance().getSettingsManager().getSaver().get("AutoAuth").equals("true")) {
            try {
                MicrosoftAuthenticator authenticator = new MicrosoftAuthenticator();
                MicrosoftAuthResult response = authenticator.loginWithRefreshToken(App.getInstance().getAccountManager().getDatabase().getStatement("CLIENT_TOKEN"));
                App.getInstance().getAccountManager().getDatabase().createStatement(
                        response.getProfile().getName(),
                        response.getProfile().getId(),
                        response.getRefreshToken(),
                        response.getAccessToken()
                );

                App.getInstance().setAuthInfos(new AuthInfos(
                        response.getProfile().getName(),
                        response.getAccessToken(),
                        response.getProfile().getId()
                ));
                return true;
            } catch (MicrosoftAuthenticationException err) {
                try {
                    Authenticator authenticator = new Authenticator(Authenticator.MOJANG_AUTH_URL, AuthPoints.NORMAL_AUTH_POINTS);
                    RefreshResponse response = authenticator.refresh(
                            App.getInstance().getAccountManager().getDatabase().getStatement("CLIENT_TOKEN"),
                            App.getInstance().getAccountManager().getDatabase().getStatement("ACCESS_TOKEN")
                    );

                    App.getInstance().getAccountManager().getDatabase().createStatement(
                            response.getSelectedProfile().getName(),
                            response.getSelectedProfile().getId(),
                            response.getClientToken(),
                            response.getAccessToken()
                    );

                    App.getInstance().setAuthInfos(new AuthInfos(
                            response.getSelectedProfile().getName(),
                            response.getAccessToken(),
                            response.getClientToken(),
                            response.getSelectedProfile().getId()
                    ));
                    return true;
                } catch (AuthenticationException er) {
                    App.getInstance().getSettingsManager().getSaver().set("AutoAuth", String.valueOf(false));
                    App.getInstance().getAccountManager().getDatabase().createStatement(
                            null,
                            null,
                            null,
                            null
                        );
                }
            }
        }*/
        return false;
    }

    private void authenticateMicrosoft(String email, String password) {
        new Thread(() -> {
            MicrosoftAuthenticator authenticator = new MicrosoftAuthenticator();
            try {
                this.logger.info("MicrosoftAuth | Trying resolving account information.");
                MicrosoftAuthResult result = authenticator.loginWithCredentials(email, password);

                response = Response.getUser(result.getProfile().getId());

                if (response == null) {
                    failedConnecting();
                    Platform.runLater(() -> AlertManager.ShowError(
                            this.panelManager.getStage(),
                            "Authentication Error",
                            "Your account isn't registered in our records!\nFeel free to contact us if you think this is a mistake."));
                } else {
                    Account account = Account.createAccount(result.getProfile().getName(), result.getProfile().getId(), result.getRefreshToken(), result.getAccessToken(), response);
                    AccountManager.setCurrentAccount(account);
                    Requests.addRemoteAccount(account);

                    this.logger.info("MicrosoftAuth | Successfully connected to " + "\"" + AccountManager.getCurrentAccount().getUsername() + "\"");
                    Platform.runLater(() -> panelManager.showPanel(new Home()));
                }
            } catch (MicrosoftAuthenticationException e) {
                this.logger.warn("MicrosoftAuth | Failed resolving account information.");
                authenticateMojang(email, password);
            }
        }).start();
    }

    private void authenticateMojang(String email, String password) {
        new Thread(() -> {
            Authenticator authenticator = new Authenticator(Authenticator.MOJANG_AUTH_URL, AuthPoints.NORMAL_AUTH_POINTS);
            try {
                this.logger.info("MojangAuth | Trying resolving account information.");
                AuthResponse result = authenticator.authenticate(AuthAgent.MINECRAFT, email, password, null);

                response = Response.getUser(result.getSelectedProfile().getId());

                if (response == null) {
                    failedConnecting();
                    Platform.runLater(() -> AlertManager.ShowError(
                            this.panelManager.getStage(),
                            "Authentication Error",
                            "Your account isn't registered in our records!\nFeel free to contact us if you think this is a mistake."));
                } else {
                    Account account = Account.createAccount(result.getSelectedProfile().getName(), result.getSelectedProfile().getId(), result.getClientToken(), result.getAccessToken(), response);
                    AccountManager.setCurrentAccount(account);
                    Requests.addRemoteAccount(account);

                    this.logger.info("MojangAuth | Successfully connected to " + "\"" + AccountManager.getCurrentAccount().getUsername() + "\"");
                    Platform.runLater(() -> panelManager.showPanel(new Home()));
                }
            } catch (AuthenticationException e) {
                this.logger.warn("MojangAuth | Failed resolving account information.");
                Platform.runLater(() -> AlertManager.ShowError(
                        this.panelManager.getStage(),
                        "Authentication Error",
                        e.getMessage()));
                failedConnecting();
            }
        }).start();
    }

    void tryingConnecting() {
        isTryingToSignIn.set(true);
        BottomBar.getInstance().disableDebugAccess();
        signIn.getStyleClass().add("login-button-active");
    }

    void failedConnecting() {
        isTryingToSignIn.set(false);
        BottomBar.getInstance().enableDebugAccess();
        signIn.getStyleClass().remove("login-button-active");
    }

    @Override
    public String getStylesheetPath() {
        return ResourceManager.getLoginDesign();
    }
}
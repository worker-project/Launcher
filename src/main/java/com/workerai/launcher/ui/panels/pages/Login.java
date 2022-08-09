package com.workerai.launcher.ui.panels.pages;

import com.workerai.launcher.WorkerLauncher;
import com.workerai.launcher.database.Account;
import com.workerai.launcher.database.authentication.ModuleResponse;
import com.workerai.launcher.database.Requests;
import com.workerai.launcher.savers.AccountManager;
import com.workerai.launcher.ui.panels.PanelManager;
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

import static com.noideaindustry.jui.components.JuiButton.createFontButton;
import static com.noideaindustry.jui.components.JuiButton.createMaterialButton;
import static com.noideaindustry.jui.components.JuiField.createPasswordField;
import static com.noideaindustry.jui.components.JuiField.createTextField;
import static com.noideaindustry.jui.components.JuiIcon.createAwesomeIcon;
import static com.noideaindustry.jui.components.JuiIcon.createDesignIcon;
import static com.noideaindustry.jui.components.JuiImageView.createImageView;
import static com.noideaindustry.jui.components.JuiPane.createGridPane;
import static com.noideaindustry.jui.components.JuiPane.createStackPane;
import static com.workerai.launcher.utils.LauncherInfos.LIGHT_GRAY;

public class Login extends Panel {
    private final AtomicBoolean isTryingToSignIn = new AtomicBoolean(false);
    private ModuleResponse moduleResponse;
    private Button signIn;

    @Override
    public void init(PanelManager panelManager) {
        super.init(panelManager);

        BottomBar.getInstance().setLoginIcons();

        if (checkAuthentication()) {
            WorkerLauncher.getInstance().getLogger().info("Logged as " + "\"" + AccountManager.getCurrentAccount().getUsername() + "\"");
            this.panelManager.showPanel(new Home());
        } else {
            BottomBar.getInstance().enableDebugAccess();

            GridPane backgroundPane = createGridPane("background-login");
            this.layout.getChildren().add(backgroundPane);
            setCanTakeAllSize(this.layout, backgroundPane);

            StackPane loginPane = createStackPane(0d, 0d, 300d, 120d, 0d, 0d, false, "login-panel", Pos.CENTER, Color.TRANSPARENT);
            this.layout.getChildren().add(loginPane);

            MaterialDesignIconView emailIcon = createDesignIcon(0d, 0d, MaterialDesignIcon.EMAIL, "22px", "logo-rectangle", LIGHT_GRAY, null);
            createMaterialButton(-175d, -50d, false, "logo-rectangle", emailIcon, loginPane);

            MaterialDesignIconView lockIcon = createDesignIcon(0d, 0d, MaterialDesignIcon.LOCK, "22px", "logo-rectangle", LIGHT_GRAY, null);
            createMaterialButton(-175d, -5d, false, "logo-rectangle", lockIcon, loginPane);

            TextField userField = createTextField(0d, -50d, 300d, 0d, "Account E-mail", "login-input", null, loginPane);
            userField.setText("compte1@nxgroupe.com");
            PasswordField passwordField = createPasswordField(0d, -5d, 300d, 0d, "Account Password", "login-input", loginPane);
            passwordField.setText("^B=*_&8nB8ssp-q+");

            createImageView(0d, 180d, 0d, 250d, ResourceManager.getIcon(), Pos.CENTER, loginPane);

            FontAwesomeIconView signIcon = createAwesomeIcon(-5d, 0, FontAwesomeIcon.SIGN_IN, "22px", LIGHT_GRAY, loginPane);
            signIn = createFontButton(-490d, -302.5d + 15d, 135d, 0d, "SIGN IN", "login-button", null, signIcon, Pos.BOTTOM_RIGHT, loginPane);
            signIn.setOnMouseClicked(e -> {
                if (!isTryingToSignIn.get()) {
                    tryingConnecting();
                    this.authenticateMicrosoft(userField.getText(), passwordField.getText());
                }
            });

            FontAwesomeIconView bugIcon = createAwesomeIcon(-5d, 0, FontAwesomeIcon.PAPER_PLANE, "22px", LIGHT_GRAY, loginPane);
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
        /*if(WorkerLauncher.getInstance().getSettingsManager().getSaver().get("AutoAuth").equals("true")) {
            try {
                MicrosoftAuthenticator authenticator = new MicrosoftAuthenticator();
                MicrosoftAuthResult moduleResponse = authenticator.loginWithRefreshToken(WorkerLauncher.getInstance().getAccountManager().getDatabase().getStatement("CLIENT_TOKEN"));
                WorkerLauncher.getInstance().getAccountManager().getDatabase().createStatement(
                        moduleResponse.getProfile().getName(),
                        moduleResponse.getProfile().getId(),
                        moduleResponse.getRefreshToken(),
                        moduleResponse.getAccessToken()
                );

                WorkerLauncher.getInstance().setAuthInfos(new AuthInfos(
                        moduleResponse.getProfile().getName(),
                        moduleResponse.getAccessToken(),
                        moduleResponse.getProfile().getId()
                ));
                return true;
            } catch (MicrosoftAuthenticationException err) {
                try {
                    Authenticator authenticator = new Authenticator(Authenticator.MOJANG_AUTH_URL, AuthPoints.NORMAL_AUTH_POINTS);
                    RefreshResponse moduleResponse = authenticator.refresh(
                            WorkerLauncher.getInstance().getAccountManager().getDatabase().getStatement("CLIENT_TOKEN"),
                            WorkerLauncher.getInstance().getAccountManager().getDatabase().getStatement("ACCESS_TOKEN")
                    );

                    WorkerLauncher.getInstance().getAccountManager().getDatabase().createStatement(
                            moduleResponse.getSelectedProfile().getName(),
                            moduleResponse.getSelectedProfile().getId(),
                            moduleResponse.getClientToken(),
                            moduleResponse.getAccessToken()
                    );

                    WorkerLauncher.getInstance().setAuthInfos(new AuthInfos(
                            moduleResponse.getSelectedProfile().getName(),
                            moduleResponse.getAccessToken(),
                            moduleResponse.getClientToken(),
                            moduleResponse.getSelectedProfile().getId()
                    ));
                    return true;
                } catch (AuthenticationException er) {
                    WorkerLauncher.getInstance().getSettingsManager().getSaver().set("AutoAuth", String.valueOf(false));
                    WorkerLauncher.getInstance().getAccountManager().getDatabase().createStatement(
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
                WorkerLauncher.getInstance().getLogger().info("MicrosoftAuth | Trying resolving account information.");
                MicrosoftAuthResult result = authenticator.loginWithCredentials(email, password);

                moduleResponse = ModuleResponse.getUserFromUuid(result.getProfile().getId());

                if (moduleResponse == null) {
                    failedConnecting();
                    Platform.runLater(() -> AlertManager.ShowError(
                            this.panelManager.getStage(),
                            "Authentication Error",
                            "Your account isn't registered in our records!\nFeel free to contact us if you think this is a mistake."));
                } else {
                    Account account = Account.createAccount(result.getProfile().getName(), result.getProfile().getId(), result.getRefreshToken(), result.getAccessToken(), moduleResponse);
                    AccountManager.setCurrentAccount(account);
                    Requests.addRemoteAccount(account);

                    WorkerLauncher.getInstance().getLogger().info("MicrosoftAuth | Successfully connected to " + "\"" + AccountManager.getCurrentAccount().getUsername() + "\"");
                    Platform.runLater(() -> panelManager.showPanel(new Home()));
                }
            } catch (MicrosoftAuthenticationException e) {
                WorkerLauncher.getInstance().getLogger().warn("MicrosoftAuth | Failed resolving account information.");
                authenticateMojang(email, password);
            }
        }).start();
    }

    private void authenticateMojang(String email, String password) {
        new Thread(() -> {
            Authenticator authenticator = new Authenticator(Authenticator.MOJANG_AUTH_URL, AuthPoints.NORMAL_AUTH_POINTS);
            try {
                WorkerLauncher.getInstance().getLogger().info("MojangAuth | Trying resolving account information.");
                AuthResponse result = authenticator.authenticate(AuthAgent.MINECRAFT, email, password, null);

                moduleResponse = ModuleResponse.getUserFromUuid(result.getSelectedProfile().getId());

                if (moduleResponse == null) {
                    failedConnecting();
                    Platform.runLater(() -> AlertManager.ShowError(
                            this.panelManager.getStage(),
                            "Authentication Error",
                            "Your account isn't registered in our records!\nFeel free to contact us if you think this is a mistake."));
                } else {
                    Account account = Account.createAccount(result.getSelectedProfile().getName(), result.getSelectedProfile().getId(), result.getClientToken(), result.getAccessToken(), moduleResponse);
                    AccountManager.setCurrentAccount(account);
                    Requests.addRemoteAccount(account);

                    WorkerLauncher.getInstance().getLogger().info("MojangAuth | Successfully connected to " + "\"" + AccountManager.getCurrentAccount().getUsername() + "\"");
                    Platform.runLater(() -> panelManager.showPanel(new Home()));
                }
            } catch (AuthenticationException e) {
                WorkerLauncher.getInstance().getLogger().warn("MojangAuth | Failed resolving account information.");
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
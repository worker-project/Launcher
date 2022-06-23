package com.workerai.launcher.ui.panels.pages;

import com.workerai.launcher.App;
import com.workerai.launcher.ui.PanelManager;
import com.workerai.launcher.ui.panel.Panel;
import com.workerai.launcher.ui.panels.partials.BottomBar;
import com.workerai.launcher.utils.AlertManager;
import com.workerai.launcher.utils.ResourceManager;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import fr.litarvan.openauth.AuthPoints;
import fr.litarvan.openauth.AuthenticationException;
import fr.litarvan.openauth.Authenticator;
import fr.litarvan.openauth.microsoft.MicrosoftAuthResult;
import fr.litarvan.openauth.microsoft.MicrosoftAuthenticationException;
import fr.litarvan.openauth.microsoft.MicrosoftAuthenticator;
import fr.litarvan.openauth.model.AuthAgent;
import fr.litarvan.openauth.model.response.AuthResponse;
import fr.theshark34.openlauncherlib.minecraft.AuthInfos;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class Login extends Panel {
    private final GridPane loginPanel = new GridPane();

    private final Button buttonLoginMicrosoft = new Button();
    private final TextField userField = new TextField();
    private final PasswordField passwordField = new PasswordField();

    private Boolean rememberAccount = false;

    @Override
    public void init(PanelManager panelManager) {
        super.init(panelManager);

        BottomBar.getInstance().homeButton.setVisible(false);
        BottomBar.getInstance().logoutButton.setVisible(false);
        BottomBar.getInstance().settingsButton.setVisible(false);
        BottomBar.getInstance().debugButton.setVisible(true);

        BottomBar.getInstance().setIconPadding(
                BottomBar.getInstance().debugButton.getTranslateX() - 120d,
                BottomBar.getInstance().debugButton.getTranslateX() - 60d,
                BottomBar.getInstance().debugButton.getTranslateX() + 60d,
                BottomBar.getInstance().debugButton.getTranslateX() + 120d
        );

        if(checkAuthentication()) {
            logger.info("Logged as " + "\"" + App.getInstance().getAuthInfos().getUsername() + "\"");
            this.panelManager.showPanel(new Home());
        } else {
            // Background
            GridPane backgroundPane = new GridPane();
            setCanTakeAllSize(backgroundPane);
            backgroundPane.getStyleClass().add("background-login");
            this.layout.getChildren().add(backgroundPane);
            setCanTakeAllSize(this.layout);

            // Logo
            ImageView logo = new ImageView(ResourceManager.getIcon());
            logo.setPreserveRatio(true);
            logo.setFitHeight(250d);
            setCenterH(logo);
            setCenterV(logo);
            logo.setTranslateY(+180d);
            this.layout.getChildren().add(logo);

            //region [Login Container]
            this.layout.getChildren().add(loginPanel);
            loginPanel.getStyleClass().add("login-panel");
            setLeft(loginPanel);
            setCenterH(loginPanel);
            setCenterV(loginPanel);

            // [E-Mail Field]
            setCenterV(userField);
            setCenterH(userField);
            userField.setPromptText("Account E-mail");
            userField.setText("compte1@nxgroupe.com");
            userField.setMaxWidth(300);
            userField.setTranslateY(-70d);
            userField.getStyleClass().add("login-input");
            loginPanel.getChildren().add(userField);

            userField.textProperty().addListener((_a, oldValue, newValue) -> {
                if(userField.getText().length() > 0 && passwordField.getText().length() > 0) {
                    buttonLoginMicrosoft.setDisable(false);
                    buttonLoginMicrosoft.setOpacity(.7f);
                } else {
                    buttonLoginMicrosoft.setDisable(true);
                }
            });

            // [Password Field]
            setCenterV(passwordField);
            setCenterH(passwordField);
            passwordField.setPromptText("Account Password");
            passwordField.setText("^B=*_&8nB8ssp-q+");
            passwordField.setTranslateY(-15d);
            passwordField.setMaxWidth(300);
            passwordField.getStyleClass().add("login-input");
            loginPanel.getChildren().add(passwordField);

            passwordField.textProperty().addListener((_a, oldValue, newValue) -> {
                if(userField.getText().length() > 0 && passwordField.getText().length() > 0) {
                    buttonLoginMicrosoft.setDisable(false);
                    buttonLoginMicrosoft.setOpacity(.7f);
                } else {
                    buttonLoginMicrosoft.setDisable(true);
                }
            });

            FontAwesomeIconView loginIcon = new FontAwesomeIconView(FontAwesomeIcon.SIGN_IN);
            loginIcon.setFill(Color.rgb(150, 150, 150));
            loginIcon.setSize("25px");
            loginIcon.setTranslateX(-5d);

            // [Login Microsoft Button]
            buttonLoginMicrosoft.setGraphic(loginIcon);
            setCenterV(buttonLoginMicrosoft);
            setCenterH(buttonLoginMicrosoft);
            buttonLoginMicrosoft.getStyleClass().add("login-button");
            buttonLoginMicrosoft.setMaxWidth(150);
            buttonLoginMicrosoft.setTranslateX(75d);
            buttonLoginMicrosoft.setTranslateY(40d);
            buttonLoginMicrosoft.setText("SIGN IN");
            loginPanel.getChildren().add(buttonLoginMicrosoft);

            buttonLoginMicrosoft.setOnMouseEntered(e -> this.panelManager.getStage().getScene().setCursor(Cursor.HAND));
            buttonLoginMicrosoft.setOnMouseExited(e -> this.panelManager.getStage().getScene().setCursor(Cursor.DEFAULT));
            buttonLoginMicrosoft.setOnMouseClicked(e -> this.authenticateMicrosoft(userField.getText(), passwordField.getText()));
            //buttonLoginMicrosoft.setDisable(true);

            // RememberMe Checkbox
            CheckBox rememberCheckbox = new CheckBox();
            rememberCheckbox.getStyleClass().add("remember-checkbox");
            setCenterH(rememberCheckbox);
            rememberCheckbox.setScaleX(1.5);
            rememberCheckbox.setScaleY(1.5);
            rememberCheckbox.setTranslateX(-130d);
            rememberCheckbox.setTranslateY(buttonLoginMicrosoft.getTranslateY());
            loginPanel.getChildren().add(rememberCheckbox);

            rememberCheckbox.setOnMouseEntered(e -> this.panelManager.getStage().getScene().setCursor(Cursor.HAND));
            rememberCheckbox.setOnMouseExited(e -> this.panelManager.getStage().getScene().setCursor(Cursor.DEFAULT));
            rememberCheckbox.selectedProperty().addListener((e, oldValue, newValue) -> {
                rememberAccount = true;
            });

            Label rememberLabel = new Label();
            rememberLabel.setText("REMEMBER ME");
            rememberLabel.getStyleClass().add("remember-label");
            setCenterH(rememberLabel);
            rememberLabel.setTranslateX(-63d);
            rememberLabel.setTranslateY(rememberCheckbox.getTranslateY());
            loginPanel.getChildren().add(rememberLabel);
            //endregion

            setCanTakeAllSize(loginPanel, userField, passwordField, buttonLoginMicrosoft);
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

    private boolean authenticateMojang(String email, String password) {
        Authenticator authenticator = new Authenticator(Authenticator.MOJANG_AUTH_URL, AuthPoints.NORMAL_AUTH_POINTS);

        try {
            this.logger.info("MojangAuth | Trying resolving account information.");
            AuthResponse response = authenticator.authenticate(AuthAgent.MINECRAFT, email, password, null);
            App.getInstance().getAccountManager().getDatabase().createStatement(
                    response.getSelectedProfile().getName(),
                    response.getSelectedProfile().getId(),
                    response.getClientToken(),
                    response.getAccessToken(),
                    rememberAccount

            );

            AuthInfos infos = new AuthInfos(
                    response.getSelectedProfile().getName(),
                    response.getAccessToken(),
                    response.getClientToken(),
                    response.getSelectedProfile().getId()
            );

            App.getInstance().setAuthInfos(infos);
            this.logger.info("MojangAuth | Successfully connected to " + "\"" + infos.getUsername() + "\"");
            panelManager.showPanel(new Home());
        } catch (AuthenticationException e) {
            return false;
        }
        return true;
    }

    private void authenticateMicrosoft(String email, String password) {
        MicrosoftAuthenticator authenticator = new MicrosoftAuthenticator();
        this.logger.info("MicrosoftAuth | Trying resolving account information.");
        try {
            MicrosoftAuthResult response = authenticator.loginWithCredentials(email, password);
            App.getInstance().getAccountManager().getDatabase().createStatement(
                    response.getProfile().getName(),
                    response.getProfile().getId(),
                    response.getRefreshToken(),
                    response.getAccessToken(),
                    rememberAccount
            );

            AuthInfos infos = new AuthInfos(
                    response.getProfile().getName(),
                    response.getAccessToken(),
                    response.getProfile().getId()
            );

            App.getInstance().setAuthInfos(infos);
            this.logger.info("MicrosoftAuth | Successfully connected to " + "\"" + infos.getUsername() + "\"");
            panelManager.showPanel(new Home());
        } catch (MicrosoftAuthenticationException e) {
            this.logger.warn("MicrosoftAuth | Failed resolving account information.");

            if(!authenticateMojang(email, password)) {
                this.logger.warn("MojangAuth | Failed resolving account information.");
                AlertManager.ShowError(
                    this.panelManager.getStage(),
                    "Authentication Error",
                    e.getMessage());
            }
        }
    }

    @Override public String getStylesheetPath() { return ResourceManager.getLoginDesign(); }
}
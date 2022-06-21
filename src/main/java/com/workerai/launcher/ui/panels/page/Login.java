package com.workerai.launcher.ui.panels.page;

import com.workerai.launcher.App;
import com.workerai.launcher.ui.PanelManager;
import com.workerai.launcher.ui.panel.Panel;
import com.workerai.launcher.ui.panels.partials.BottomBar;
import com.workerai.launcher.utils.AlertManager;
import com.workerai.launcher.utils.ResourceManager;
import fr.litarvan.openauth.AuthPoints;
import fr.litarvan.openauth.AuthenticationException;
import fr.litarvan.openauth.Authenticator;
import fr.litarvan.openauth.microsoft.MicrosoftAuthResult;
import fr.litarvan.openauth.microsoft.MicrosoftAuthenticationException;
import fr.litarvan.openauth.microsoft.MicrosoftAuthenticator;
import fr.litarvan.openauth.model.AuthAgent;
import fr.litarvan.openauth.model.response.AuthResponse;
import fr.litarvan.openauth.model.response.RefreshResponse;
import fr.theshark34.openlauncherlib.minecraft.AuthInfos;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

public class Login extends Panel {
    private final GridPane loginPanel = new GridPane();

    private final Button buttonLoginMicrosoft = new Button();
    private final TextField userField = new TextField();
    private final PasswordField passwordField = new PasswordField();

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

            // [Login Microsoft Button]
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
            buttonLoginMicrosoft.setDisable(true);

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
            rememberCheckbox.selectedProperty().addListener((e, oldvalue, newValue) -> {
                App.getInstance().getSettingsManager().getSaver().set("AutoAuth", String.valueOf(newValue));
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
        if(App.getInstance().getSettingsManager().getSaver().get("AutoAuth").equals("true")) {
            try {
                MicrosoftAuthenticator authenticator = new MicrosoftAuthenticator();
                MicrosoftAuthResult response = authenticator.loginWithRefreshToken(App.getInstance().getAccountManager().getCurrClientToken());
                App.getInstance().getAccountManager().updateClientToken(response.getRefreshToken());
                App.getInstance().getAccountManager().updateAccessToken(response.getAccessToken());
                App.getInstance().getAccountManager().getSaver().save();

                App.getInstance().setAuthInfos(new AuthInfos(
                        response.getProfile().getName(),
                        response.getAccessToken(),
                        response.getProfile().getId()
                ));
                return true;
            } catch (MicrosoftAuthenticationException err) {
                try {
                    Authenticator authenticator = new Authenticator(Authenticator.MOJANG_AUTH_URL, AuthPoints.NORMAL_AUTH_POINTS);
                    RefreshResponse response = authenticator.refresh(App.getInstance().getAccountManager().getCurrAccessToken(), App.getInstance().getAccountManager().getCurrClientToken());
                    App.getInstance().getAccountManager().updateClientToken(response.getClientToken());
                    App.getInstance().getAccountManager().updateAccessToken(response.getAccessToken());
                    App.getInstance().getAccountManager().getSaver().save();

                    App.getInstance().setAuthInfos(new AuthInfos(
                            response.getSelectedProfile().getName(),
                            response.getAccessToken(),
                            response.getClientToken(),
                            response.getSelectedProfile().getId()
                    ));
                    return true;
                } catch (AuthenticationException er) {
                    App.getInstance().getAccountManager().getSaver().set("AutoAuth", String.valueOf(false));
                    App.getInstance().getAccountManager().getSaver().set("CurrentClientToken", "null");
                    App.getInstance().getAccountManager().getSaver().set("CurrentAccessToken", "null");
                    App.getInstance().getAccountManager().getSaver().save();
                }
            }
        }
        return false;
    }

    private boolean authenticateMojang(String email, String password) {
        Authenticator authenticator = new Authenticator(Authenticator.MOJANG_AUTH_URL, AuthPoints.NORMAL_AUTH_POINTS);

        try {
            this.logger.info("MojangAuth | Trying resolving account information.");
            AuthResponse response = authenticator.authenticate(AuthAgent.MINECRAFT, email, password, null);
            App.getInstance().getAccountManager().updateClientToken(response.getClientToken());
            App.getInstance().getAccountManager().updateAccessToken(response.getAccessToken());
            App.getInstance().getAccountManager().getSaver().save();

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
            App.getInstance().getAccountManager().updateClientToken(response.getRefreshToken());
            App.getInstance().getAccountManager().updateAccessToken(response.getAccessToken());
            App.getInstance().getAccountManager().getSaver().save();

            AuthInfos infos = new AuthInfos(
                    response.getProfile().getName(),
                    response.getAccessToken(),
                    response.getProfile().getId()
            );

            App.getInstance().setAuthInfos(infos);
            this.logger.info("MicrosoftAuth | Successfully connected to " + "\"" + infos.getUsername() + "\"");
            panelManager.showPanel(new Home());
        } catch (MicrosoftAuthenticationException e) {
            this.logger.info("MicrosoftAuth | Failed resolving account information.");

            if(!authenticateMojang(email, password)) {
                AlertManager.ShowError(
                    this.panelManager.getStage(),
                    "Authentication Error",
                    e.getMessage());
            }
        }
    }

    @Override public String getStylesheetPath() { return ResourceManager.getLoginDesign(); }
}
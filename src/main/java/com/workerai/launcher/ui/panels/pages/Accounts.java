package com.workerai.launcher.ui.panels.pages;

import com.workerai.launcher.App;
import com.workerai.launcher.database.Account;
import com.workerai.launcher.savers.AccountSaver;
import com.workerai.launcher.ui.PanelManager;
import com.workerai.launcher.ui.panel.Panel;
import com.workerai.launcher.ui.panels.partials.BottomBar;
import com.workerai.launcher.utils.ResourceManager;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Accounts extends Panel {
    private final GridPane accountPanel = new GridPane();

    @Override
    public void init(PanelManager panelManager) {
        super.init(panelManager);
        BottomBar.getInstance().settingsButton.setVisible(false);
        BottomBar.getInstance().homeButton.setVisible(true);
        BottomBar.getInstance().homeButton.setTranslateX(BottomBar.getInstance().settingsButton.getTranslateX());

        // Background
        GridPane backgroundPane = new GridPane();
        setCanTakeAllSize(backgroundPane);
        backgroundPane.getStyleClass().add("background-accounts");
        this.layout.getChildren().add(backgroundPane);
        setCanTakeAllSize(this.layout);

        //region [Accounts Container]
        this.layout.getChildren().add(accountPanel);
        accountPanel.getStyleClass().add("accounts-panel");
        setCanTakeAllSize(accountPanel);
        setCenterH(accountPanel);
        setCenterV(accountPanel);

        // Accounts Container
        Rectangle container = new Rectangle(1200,600);
        container.setFill(Color.rgb(32,31,29));
        setCenterH(container);
        setCenterV(container);
        container.setArcHeight(15d);
        container.setArcWidth(15d);
        accountPanel.getChildren().add(container);

        // Accounts Label Container
        Region settingsContainer = new Region();
        settingsContainer.setMaxWidth(1200d);
        settingsContainer.setMaxHeight(90d);
        settingsContainer.getStyleClass().add("rectangle-border");
        setCenterH(settingsContainer);
        setTop(settingsContainer);
        settingsContainer.setTranslateY(40d);
        accountPanel.getChildren().add(settingsContainer);

        // Accounts Label
        Label settingsLabel = new Label("Settings");
        settingsLabel.getStyleClass().add("accounts-label");
        settingsLabel.setText("Account Manager & Licenses");
        setCenterH(settingsLabel);
        setTop(settingsLabel);
        settingsLabel.setTranslateY(settingsContainer.getTranslateY() + 15d);
        accountPanel.getChildren().add(settingsLabel);

        // Accounts SubLabel
        Label settingsSubLabel = new Label();
        settingsSubLabel.getStyleClass().add("accounts-subLabel");
        settingsSubLabel.setText("Save your accounts here to connect faster.");
        setTop(settingsSubLabel);
        setCenterH(settingsSubLabel);
        settingsSubLabel.setTranslateY(settingsLabel.getTranslateY() + 30d);
        accountPanel.getChildren().add(settingsSubLabel);

        FontAwesomeIconView settingsIcon = new FontAwesomeIconView(FontAwesomeIcon.GEARS);
        settingsIcon.setFill(Color.WHITE);
        settingsIcon.setSize("25px");
        settingsIcon.setTranslateX(-4d);
        settingsLabel.setGraphic(settingsIcon);
        //endregion

        for(Account account : App.getInstance().getAccountManager().getAccounts()) {
            Rectangle displayContainer = createContainer(70d, 170d, 350, 180);
            display(displayContainer, account);
        }
    }

    private void display(Rectangle displayContainer, Account account) {
        Label usernameLabel = new Label();
        if(BottomBar.DEBUG_MODE) usernameLabel.setText("Default Name");
        else usernameLabel.setText(account.getUsername());

        usernameLabel.getStyleClass().add("account-label");
        setTop(usernameLabel);
        usernameLabel.setTranslateX(displayContainer.getTranslateX() * 2 + 15d);
        usernameLabel.setTranslateY(displayContainer.getTranslateY() + 15d);
        setCanTakeAllWidth(usernameLabel);
        accountPanel.getChildren().add(usernameLabel);

        //region [Remove Button]
        Button removeButton = new Button();
        removeButton.getStyleClass().add("account-button-remove");
        removeButton.setText("Remove");
        removeButton.setMaxWidth(150d);
        removeButton.setMaxHeight(30d);
        setTop(removeButton);
        removeButton.setTranslateX(displayContainer.getTranslateX() + 20d);
        removeButton.setTranslateY(displayContainer.getTranslateY() + 130d);
        accountPanel.getChildren().add(removeButton);

        FontAwesomeIconView removeIcon = new FontAwesomeIconView(FontAwesomeIcon.CROSSHAIRS);
        removeIcon.setFill(Color.WHITE);
        removeIcon.setSize("20px");
        removeIcon.setTranslateX(-4d);
        removeButton.setGraphic(removeIcon);

        removeButton.setOnMouseEntered(e -> App.getInstance().getPanelManager().getStage().getScene().setCursor(Cursor.HAND));
        removeButton.setOnMouseExited(e -> App.getInstance().getPanelManager().getStage().getScene().setCursor(Cursor.DEFAULT));
        removeButton.setOnMouseClicked(e -> {

        });
        //endregion

        //region [Play Button]
        Button playAccount = new Button();
        playAccount.getStyleClass().add("account-button-play");
        playAccount.setText("Play");
        playAccount.setMaxWidth(150d);
        playAccount.setMaxHeight(30d);
        setTop(playAccount);
        playAccount.setTranslateX(displayContainer.getTranslateX() + 20d + 150d + 10d);
        playAccount.setTranslateY(displayContainer.getTranslateY() + 130d);
        accountPanel.getChildren().add(playAccount);

        FontAwesomeIconView playIcon = new FontAwesomeIconView(FontAwesomeIcon.GAMEPAD);
        playIcon.setFill(Color.WHITE);
        playIcon.setSize("18px");
        playIcon.setTranslateX(-4d);
        playAccount.setGraphic(playIcon);

        playAccount.setOnMouseEntered(e -> App.getInstance().getPanelManager().getStage().getScene().setCursor(Cursor.HAND));
        playAccount.setOnMouseExited(e -> App.getInstance().getPanelManager().getStage().getScene().setCursor(Cursor.DEFAULT));
        playAccount.setOnMouseClicked(e -> {

        });
        //endregion

        //region [Avatar Container]
        String avatarUrl;
        if(BottomBar.DEBUG_MODE) {
            avatarUrl = "https://minotar.net/avatar/" + "MHF_Steve.png";
        } else {
            avatarUrl = "https://minotar.net/avatar/" + (
                    account.getUuid() + ".png"
            );
        }

        ImageView avatarView = new ImageView();
        avatarView.setImage(new Image(avatarUrl));
        avatarView.setPreserveRatio(true);
        avatarView.setFitHeight(70d);
        setTop(avatarView);
        avatarView.setTranslateX(displayContainer.getTranslateX() + 20d);
        avatarView.setTranslateY(displayContainer.getTranslateY() + 50d);
        accountPanel.getChildren().add(avatarView);
        //endregion
    }

    private Rectangle createContainer(double X, double Y, double SIZEX, double SIZEY) {
        Rectangle rectangle = new Rectangle(SIZEX,SIZEY);
        rectangle.setFill(Color.rgb(29,29,27));
        setCenterH(rectangle);
        setLeft(rectangle);
        setTop(rectangle);
        rectangle.setArcHeight(15d);
        rectangle.setArcWidth(15d);
        rectangle.setTranslateX(X);
        rectangle.setTranslateY(Y);
        accountPanel.getChildren().add(rectangle);

        return rectangle;
    }

    @Override public String getStylesheetPath() { return ResourceManager.getAccountDesign(); }
}

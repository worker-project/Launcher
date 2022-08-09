package com.workerai.launcher.ui.panels.pages;

import com.workerai.launcher.database.Account;
import com.workerai.launcher.savers.AccountManager;
import com.workerai.launcher.ui.panels.PanelManager;
import com.workerai.launcher.ui.panels.partials.BottomBar;
import com.workerai.launcher.ui.utils.Panel;
import com.workerai.launcher.utils.ResourceManager;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

import static com.noideaindustry.jui.components.JuiLabel.createLabel;
import static com.noideaindustry.jui.components.JuiIcon.createAwesomeIcon;
import static com.noideaindustry.jui.components.JuiPane.createGridPane;
import static com.noideaindustry.jui.components.JuiPane.createStackPane;
import static com.workerai.launcher.utils.DisplayManager.displayAccount;
import static com.workerai.launcher.utils.LauncherInfos.*;

public class Accounts extends Panel {
    @Override
    public void init(PanelManager panelManager) {
        super.init(panelManager);

        BottomBar.getInstance().setDefaultIcons();

        GridPane backgroundPane = createGridPane("background-login");
        this.layout.getChildren().add(backgroundPane);
        setCanTakeAllSize(this.layout, backgroundPane);

        StackPane accountsPane = createStackPane(0d, 0d, 1200d, 600d, "accounts-panel", LIGHT_BLACK);
        this.layout.getChildren().add(accountsPane);

        FontAwesomeIconView titleIcon = createAwesomeIcon(-4d, 0, FontAwesomeIcon.GEARS, "25px", WHITE, accountsPane);
        createLabel(0d, 90d, "Account Manager & Licenses", titleIcon, "accounts-label", Pos.TOP_CENTER, accountsPane);
        createLabel(0d, 120d, "Save your accounts here to connect faster.", "accounts-subLabel", accountsPane);

        GridPane accountCard = new GridPane();
        accountsPane.getChildren().add(accountCard);

        double startX = 60d, startY = 200d;
        int accountNum = 0;
        for (Account account : AccountManager.getLocalAccounts()) {
            accountNum += 1;
            account.setResponse(AccountManager.getLocalAccount(account).getResponse());
            displayAccount(accountCard, account, createStackPane(startX, startY, 350d, 180d, DARK_BLACK), false);
            if (accountNum % 3 != 0) {
                startX += 45d + 350d;
            } else {
                startX = 60d;
                startY += 50d + 170d;
            }
        }
    }

    @Override
    public String getStylesheetPath() {
        return ResourceManager.getAccountDesign();
    }
}

package com.workerai.launcher.ui.panels.pages;

import com.noideaindustry.jui.JuiInterface;
import com.workerai.launcher.database.Account;
import com.workerai.launcher.savers.AccountSaver;
import com.workerai.launcher.ui.PanelManager;
import com.workerai.launcher.ui.panels.partials.BottomBar;
import com.workerai.launcher.ui.utils.Panel;
import com.workerai.launcher.utils.ResourceManager;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

import static com.noideaindustry.jui.JuiInterface.JuiPane.createGridPane;
import static com.noideaindustry.jui.JuiInterface.JuiPane.createStackPane;
import static com.noideaindustry.jui.JuiInterface.createLabel;
import static com.workerai.launcher.utils.DisplayManager.displayFullAccount;

public class Accounts extends Panel {
    @Override
    public void init(PanelManager panelManager) {
        super.init(panelManager);

        BottomBar.getInstance().setDefaultIcons();

        GridPane backgroundPane = createGridPane(0d, 0d, 0d, 0d, 0d, 0d, "background-login", Color.TRANSPARENT);
        this.layout.getChildren().add(backgroundPane);
        setCanTakeAllSize(this.layout, backgroundPane);

        StackPane accountsPane = createStackPane(0d, 0d, 1200d, 600d, 15d, 15d, false, "accounts-panel", Pos.CENTER, Color.rgb(32, 31, 29));
        this.layout.getChildren().add(accountsPane);

        FontAwesomeIconView titleIcon = JuiInterface.JuiIcon.createFontIcon(-4d, 0, FontAwesomeIcon.GEARS, "25px", null, Color.WHITE, accountsPane);
        createLabel(0d, 90d, "Account Manager & Licenses", titleIcon, "accounts-label", Pos.TOP_CENTER, accountsPane);
        createLabel(0d, 120d, "Save your accounts here to connect faster.", null, "accounts-subLabel", Pos.TOP_CENTER, accountsPane);

        GridPane accountCard = new GridPane();
        accountsPane.getChildren().add(accountCard);

        double startX = 60d, startY = 200d;
        int accountNum = 0;
        for (Account account : AccountSaver.getAccounts()) {
            accountNum += 1;
            displayFullAccount(accountCard, account, createStackPane(startX, startY, 350d, 180d, 15d, 15d, false, null, null, Color.rgb(29, 29, 27)));
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

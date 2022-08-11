package com.workerai.launcher.ui.panels.pages;

import com.workerai.launcher.WorkerLauncher;
import com.workerai.launcher.database.Account;
import com.workerai.launcher.database.authentication.ModuleResponse;
import com.workerai.launcher.savers.AccountManager;
import com.workerai.launcher.ui.panels.PanelManager;
import com.workerai.launcher.ui.panels.partials.BottomBar;
import com.workerai.launcher.ui.utils.Panel;
import com.workerai.launcher.utils.ResourceManager;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

import static com.noideaindustry.jui.components.JuiIcon.createAwesomeIcon;
import static com.noideaindustry.jui.components.JuiLabel.createLabel;
import static com.noideaindustry.jui.components.JuiPane.*;
import static com.workerai.launcher.utils.DisplayManager.displayAccount;
import static com.workerai.launcher.utils.ColorManager.*;

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

        GridPane scrollContent = new GridPane();
        final int MAX_COLUMN = 3;
        ColumnConstraints constraintThird = new ColumnConstraints();
        constraintThird.setPercentWidth(100.d / MAX_COLUMN);
        scrollContent.getColumnConstraints().addAll(constraintThird, constraintThird, constraintThird);

        scrollContent.setAlignment(Pos.TOP_CENTER);
        scrollContent.setPadding(new Insets(20));
        scrollContent.setHgap(15);
        scrollContent.setVgap(30);

        int cardX = 0, cardY = 0;
        int accountNum = 0;
        for (Account account : AccountManager.getLocalAccounts()) {
            accountNum += 1;
            if (!WorkerLauncher.isDebugMode()) {
                account.setResponse(AccountManager.getLocalAccount(account).getResponse());
            } else {
                account = new Account();
                account.setResponse(new ModuleResponse(false,false));
            }
            final StackPane card = createStackPane(cardX, cardY, 350d, 180d, DARK_BLACK);
            scrollContent.add(card, cardX, cardY);
            displayAccount(scrollContent, account, card, false);

            if (accountNum % MAX_COLUMN != 0) {
                cardX++;
            } else {
                cardX = 0;
                cardY++;
            }
        }

        createScrollPane(-60d, 60d, 1140d, 435d, "scroll-bar", scrollContent, accountsPane);
    }

    @Override
    public String getStylesheetPath() {
        return ResourceManager.getAccountDesign();
    }
}

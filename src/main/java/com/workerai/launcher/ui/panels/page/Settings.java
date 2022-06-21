package com.workerai.launcher.ui.panels.page;

import com.workerai.launcher.App;
import com.workerai.launcher.ui.PanelManager;
import com.workerai.launcher.ui.panel.Panel;
import com.workerai.launcher.ui.panels.page.settings.*;
import com.workerai.launcher.ui.panels.partials.BottomBar;
import com.workerai.launcher.utils.ResourceManager;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import fr.theshark34.openlauncherlib.util.Saver;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Settings extends Panel {
    private final GridPane settingsPanel = new GridPane();
    private final Saver saver = App.getInstance().getSettingsManager().getSaver();

    @Override
    public void init(PanelManager panelManager) {
        super.init(panelManager);
        BottomBar.getInstance().settingsButton.setVisible(false);
        BottomBar.getInstance().homeButton.setVisible(true);
        BottomBar.getInstance().homeButton.setTranslateX(BottomBar.getInstance().settingsButton.getTranslateX());

        // Background
        GridPane backgroundPane = new GridPane();
        setCanTakeAllSize(backgroundPane);
        backgroundPane.getStyleClass().add("background-settings");
        this.layout.getChildren().add(backgroundPane);
        setCanTakeAllSize(this.layout);

        //region [Settings Container]
        this.layout.getChildren().add(settingsPanel);
        settingsPanel.getStyleClass().add("settings-panel");
        setCanTakeAllSize(settingsPanel);
        setLeft(settingsPanel);
        setCenterH(settingsPanel);
        setCenterV(settingsPanel);

        // Settings Container
        Rectangle container = new Rectangle(1200,600);
        container.setFill(Color.rgb(32,31,29));
        setCenterH(container);
        setCenterV(container);
        container.setArcHeight(15d);
        container.setArcWidth(15d);
        settingsPanel.getChildren().add(container);

        // Settings Container
        Region settingsContainer = new Region();
        settingsContainer.setMaxWidth(1200d);
        settingsContainer.setMaxHeight(90d);
        settingsContainer.getStyleClass().add("rectangle-border");
        setCenterH(settingsContainer);
        setTop(settingsContainer);
        settingsContainer.setTranslateY(40d);
        settingsPanel.getChildren().add(settingsContainer);

        // Settings Label
        Label settingsLabel = new Label("Settings");
        settingsLabel.getStyleClass().add("settings-label");
        settingsLabel.setText("Launcher & Client Settings");
        setCenterH(settingsLabel);
        setTop(settingsLabel);
        settingsLabel.setTranslateY(settingsContainer.getTranslateY() + 15d);
        settingsPanel.getChildren().add(settingsLabel);

        // Settings SubLabel
        Label settingsSubLabel = new Label();
        settingsSubLabel.getStyleClass().add("settings-subLabel");
        settingsSubLabel.setText("Memory allocation & Launcher preferences");
        setTop(settingsSubLabel);
        setCenterH(settingsSubLabel);
        settingsSubLabel.setTranslateY(settingsLabel.getTranslateY() + 30d);
        settingsPanel.getChildren().add(settingsSubLabel);

        FontAwesomeIconView settingsIcon = new FontAwesomeIconView(FontAwesomeIcon.GEARS);
        settingsIcon.setFill(Color.WHITE);
        settingsIcon.setSize("25px");
        settingsIcon.setTranslateX(-4d);
        settingsLabel.setGraphic(settingsIcon);

        //endregion

        Display display = new Display();
        Rectangle displayContainer = createContainer(70d, 170d, 350, 200);
        display.init(displayContainer, settingsPanel, saver);

        Memory memory = new Memory();
        Rectangle memoryContainer = createContainer(displayContainer.getTranslateX() + 385d, displayContainer.getTranslateY(), 350, 200);
        memory.init(memoryContainer, settingsPanel, saver);

        Directory directory = new Directory();
        Rectangle directoryContainer = createContainer(displayContainer.getTranslateX(), displayContainer.getTranslateY() + 230d, 350, 200);
        directory.init(directoryContainer, settingsPanel, saver);

        Resolution resolution = new Resolution();
        Rectangle resolutionContainer = createContainer(directoryContainer.getTranslateX() + 385d, directoryContainer.getTranslateY(), 350, 200);
        resolution.init(resolutionContainer, settingsPanel, saver);

        Folder folder = new Folder();
        Rectangle folderContainer = createContainer(840d, 170d, 350, 125);
        folder.init(folderContainer, settingsPanel, saver);

        Legal legal = new Legal();
        Rectangle legalContainer = createContainer(folderContainer.getTranslateX(), folderContainer.getTranslateY() + 152.2d, 350, 125);
        legal.init(legalContainer, settingsPanel, saver);

        Links links = new Links();
        Rectangle linksContainer = createContainer(legalContainer.getTranslateX(), legalContainer.getTranslateY() + 152.1d, 350, 125);
        links.init(linksContainer, settingsPanel, saver);
    }

    protected Rectangle createContainer(double X, double Y, double SIZEX, double SIZEY) {
        Rectangle rectangle = new Rectangle(SIZEX,SIZEY);
        rectangle.setFill(Color.rgb(29,29,27));
        setCenterH(rectangle);
        setLeft(rectangle);
        setTop(rectangle);
        rectangle.setArcHeight(15d);
        rectangle.setArcWidth(15d);
        rectangle.setTranslateX(X);
        rectangle.setTranslateY(Y);
        settingsPanel.getChildren().add(rectangle);

        return rectangle;
    }

    @Override public String getStylesheetPath() { return ResourceManager.getSettingsDesign(); }
}

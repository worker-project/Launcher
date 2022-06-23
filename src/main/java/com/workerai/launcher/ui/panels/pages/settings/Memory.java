package com.workerai.launcher.ui.panels.pages.settings;

import com.workerai.launcher.ui.panels.pages.Settings;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import fr.theshark34.openlauncherlib.util.Saver;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import oshi.SystemInfo;
import oshi.hardware.GlobalMemory;

public class Memory extends Settings {
    public void init(Rectangle memoryContainer, GridPane pane, Saver saver) {
        //region [Allocated Memory Sub Container]
        GridPane allocateMemoryPane = new GridPane();
        setTop(allocateMemoryPane);
        setCenterH(allocateMemoryPane);
        setCenterV(allocateMemoryPane);
        allocateMemoryPane.setTranslateY(-80d);
        allocateMemoryPane.setMaxWidth(300d);
        allocateMemoryPane.setMaxHeight(20d);
        pane.getChildren().add(allocateMemoryPane);

        Label allocatedMemoryLabel = new Label();
        allocatedMemoryLabel.getStyleClass().add("afterLaunch-label");
        allocatedMemoryLabel.setText("Allocated Memory");
        setCenterH(allocatedMemoryLabel);
        setTop(allocatedMemoryLabel);
        allocatedMemoryLabel.setTranslateX(memoryContainer.getTranslateX() - 440);
        allocatedMemoryLabel.setTranslateY(memoryContainer.getTranslateY() + 15d);
        pane.getChildren().add(allocatedMemoryLabel);

        FontAwesomeIconView allocatedMemoryIcon = new FontAwesomeIconView(FontAwesomeIcon.SLIDERS);
        allocatedMemoryIcon.setFill(Color.WHITE);
        allocatedMemoryIcon.setSize("25px");
        setCenterH(allocatedMemoryIcon);
        setTop(allocatedMemoryIcon);
        allocatedMemoryIcon.setTranslateX(allocatedMemoryLabel.getTranslateX() - 120d);
        allocatedMemoryIcon.setTranslateY(allocatedMemoryLabel.getTranslateY() + 5d);
        pane.getChildren().add(allocatedMemoryIcon);

        Label allocatedMemorySubLabel = new Label();
        allocatedMemorySubLabel.getStyleClass().add("afterLaunch-subLabel");
        allocatedMemorySubLabel.setText("How much memory should we allocate to the game");
        setTop(allocatedMemorySubLabel);
        setCenterH(allocatedMemorySubLabel);
        allocatedMemorySubLabel.setTranslateX(allocatedMemoryLabel.getTranslateX() - 15);
        allocatedMemorySubLabel.setTranslateY(allocatedMemoryLabel.getTranslateY() + 35d);
        pane.getChildren().add(allocatedMemorySubLabel);

        SystemInfo systemInfo = new SystemInfo();
        GlobalMemory memory = systemInfo.getHardware().getMemory();
        double maxMemoryAvailable = (Math.ceil(memory.getTotal() / Math.pow(1024, 2))/1024.0);

        Slider ramSlider = new Slider(0.5, Math.round(maxMemoryAvailable), Double.parseDouble(saver.get("AllocatedRAM")));
        ramSlider.getStyleClass().add("slider");
        ramSlider.setBlockIncrement(0.5);
        allocateMemoryPane.getChildren().add(ramSlider);

        Label currAllocatedMemoryLabel = new Label();
        currAllocatedMemoryLabel.getStyleClass().add("memory-allocated-label");
        currAllocatedMemoryLabel.setText(saver.get("AllocatedRAM") + "GB ALLOCATED");
        setTop(currAllocatedMemoryLabel);
        setCenterH(currAllocatedMemoryLabel);
        currAllocatedMemoryLabel.setTranslateX(ramSlider.getTranslateX());
        currAllocatedMemoryLabel.setTranslateY(ramSlider.getTranslateY() + 280d);
        pane.getChildren().add(currAllocatedMemoryLabel);

        Label currAllocatedMemorySubLabel = new Label();
        currAllocatedMemorySubLabel.getStyleClass().add("directory-subLabel");
        currAllocatedMemorySubLabel.setText("YOU HAVE " + String.format("%.1f",(maxMemoryAvailable - Double.parseDouble(saver.get("AllocatedRAM")))) + " LEFT TO ALLOCATE");
        setTop(currAllocatedMemorySubLabel);
        setCenterH(currAllocatedMemorySubLabel);
        currAllocatedMemorySubLabel.setTranslateX(currAllocatedMemoryLabel.getTranslateX());
        currAllocatedMemorySubLabel.setTranslateY(currAllocatedMemoryLabel.getTranslateY() + 30d);
        pane.getChildren().add(currAllocatedMemorySubLabel);

        ramSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            currAllocatedMemoryLabel.setText(String.format("%.1f", newValue.floatValue()) + "GB ALLOCATED");
            saver.set("AllocatedRAM", String.format("%.1f", newValue.floatValue()));
            currAllocatedMemorySubLabel.setText("YOU HAVE " + String.format("%.1f",(Math.round(maxMemoryAvailable) - Double.parseDouble(saver.get("AllocatedRAM")))) + " LEFT TO ALLOCATE");
        });
    }
}

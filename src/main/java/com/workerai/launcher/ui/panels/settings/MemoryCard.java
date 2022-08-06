package com.workerai.launcher.ui.panels.settings;

import com.workerai.launcher.ui.panels.pages.Settings;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import fr.theshark34.openlauncherlib.util.Saver;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import oshi.SystemInfo;
import oshi.hardware.GlobalMemory;

import static com.noideaindustry.jui.JuiUtils.bytesFormater;
import static com.noideaindustry.jui.interfaces.JuiBox.createHBox;
import static com.noideaindustry.jui.interfaces.JuiIcon.createAwesomeIcon;
import static com.noideaindustry.jui.interfaces.JuiLabel.createLabel;
import static com.noideaindustry.jui.interfaces.JuiSlider.createSlider;
import static com.workerai.launcher.utils.LauncherInfos.WHITE;

public class MemoryCard extends Settings {
    public static void create(GridPane container, Saver saver, StackPane card) {
        container.getChildren().add(card);

        createLabel(0, 20d, "Allocated Memory", createAwesomeIcon(-4d, 0, FontAwesomeIcon.SLIDERS, "25px", WHITE, card), "afterLaunch-label", Pos.TOP_CENTER, card);
        createLabel(0, 50d, "How much memory should we allocate to the game", null, "afterLaunch-subLabel", Pos.TOP_CENTER, card);

        SystemInfo systemInfo = new SystemInfo();
        GlobalMemory memory = systemInfo.getHardware().getMemory();
        double maxMemoryGB = bytesFormater(memory.getTotal());

        Label memoryAllocated = createLabel(0, -45d, String.format("%.1f", (Double.parseDouble(saver.get("AllocatedRAM")) / 1024d)) + " GB ALLOCATED", null, "memory-allocated-label", Pos.BOTTOM_CENTER, card);
        Label memoryLeft = createLabel(0, -25d, "YOU HAVE " + String.format("%.1f", (maxMemoryGB - (Double.parseDouble(saver.get("AllocatedRAM")) / 1024d))) + " GB LEFT TO ALLOCATE", null, "directory-subLabel", Pos.BOTTOM_CENTER, card);

        Slider ramSlider = createSlider(5d, 15d, 180d, 0d, 0.5 * 1024, maxMemoryGB * 1024, (Double.parseDouble(saver.get("AllocatedRAM"))), "slider", createHBox(50, 50, 10, 50, 10, card));
        ramSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            saver.set("AllocatedRAM", String.format("%.0f", newValue.floatValue()));
            memoryAllocated.setText(String.format("%.1f", newValue.floatValue() / 1024d) + " GB ALLOCATED");
            memoryLeft.setText("YOU HAVE " + String.format("%.1f", (maxMemoryGB - (Double.parseDouble(saver.get("AllocatedRAM")) / 1024d))) + " GB LEFT TO ALLOCATE");
        });
    }
}

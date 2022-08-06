package com.noideaindustry.jui.interfaces;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.scene.control.Tooltip;
import javafx.util.Duration;

public abstract class JuiToolTip {
    public static Tooltip createTooltip(String displayText, FontAwesomeIconView displayIcon) {
        return createTooltip(displayText, displayIcon, Duration.ZERO, Duration.ZERO);
    }

    public static Tooltip createTooltip(String displayText, FontAwesomeIconView displayIcon, Duration showDelay, Duration hideDelay) {
        Tooltip tooltip = new Tooltip(displayText);

        tooltip.setGraphic(displayIcon);

        tooltip.setShowDelay(showDelay);
        tooltip.setHideDelay(hideDelay);

        return tooltip;
    }
}

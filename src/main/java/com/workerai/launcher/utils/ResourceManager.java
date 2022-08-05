package com.workerai.launcher.utils;

public class ResourceManager {
    private static final String ICON_PATH = "/resources/graphics/icons/icon.png";
    private static final String FAVICON_PATH = "/resources/graphics/icons/favicon.png";

    public static String getIcon() {
        return ICON_PATH;
    }
    public static String getFavIcon() {
        return FAVICON_PATH;
    }

    private static final String MINECRAFT_PATH = "/resources/graphics/minecraft.png";
    public static String getMinecraftIcon() {
        return MINECRAFT_PATH;
    }

    private static final String LOGIN_DESIGN_PATH = "/resources/css/ui/panels/pages/login.css";
    private static final String SETTINGS_DESIGN_PATH = "/resources/css/ui/panels/pages/settings.css";
    private static final String HOME_DESIGN_PATH = "/resources/css/ui/panels/pages/home.css";
    private static final String ACCOUNT_DESIGN_PATH = "/resources/css/ui/panels/pages/account.css";

    private static final String ALERT_DESIGN_PATH = "/resources/css/ui/alert.css";
    public static String getLoginDesign() {
        return LOGIN_DESIGN_PATH;
    }
    public static String getSettingsDesign() {
        return SETTINGS_DESIGN_PATH;
    }
    public static String getHomeDesign() {
        return HOME_DESIGN_PATH;
    }
    public static String getAccountDesign() {
        return ACCOUNT_DESIGN_PATH;
    }
    public static String getAlertDesignPath() {
        return ALERT_DESIGN_PATH;
    }
    private static final String BOTTOM_DESIGN_PATH = "/resources/css/ui/panels/pages/partials/bottom.css";
    private static final String TOP_DESIGN_PATH = "/resources/css/ui/panels/pages/partials/top.css";

    public static String getBottomDesign() {
        return BOTTOM_DESIGN_PATH;
    }
    public static String getTopDesign() {
        return TOP_DESIGN_PATH;
    }
}
package com.workerai.launcher;

import com.workerai.launcher.database.Requests;
import com.workerai.launcher.savers.AccountManager;
import com.workerai.launcher.savers.SettingsManager;
import com.workerai.launcher.ui.panels.PanelManager;
import com.workerai.launcher.ui.panels.pages.Login;
import com.workerai.launcher.utils.NewsManager;
import com.workerai.launcher.utils.PlatformHandler;
import fr.flowarg.flowlogger.ILogger;
import fr.flowarg.flowlogger.Logger;
import fr.theshark34.openlauncherlib.util.Saver;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class WorkerLauncher extends Application {
    private static WorkerLauncher INSTANCE;
    private PanelManager panelManager = null;

    private final ILogger LOGGER;
    private final SettingsManager settingsManager;

    private static boolean DEBUG = false;
    public static String JAVA_PATH;

    private final Path launcherDirectory = PlatformHandler.createFolder(".WorkerAI");

    public WorkerLauncher() throws IOException {
        INSTANCE = this;
        this.LOGGER = new Logger("[WorkerAI]", launcherDirectory.resolve("logs.log"));
        if (!this.launcherDirectory.toFile().exists()) {
            if (!this.launcherDirectory.toFile().mkdir()) {
                this.LOGGER.err("Unable to create launcher folder");
            }
        }

        settingsManager = new SettingsManager(new Saver(launcherDirectory.resolve("settings.save").toFile()));
    }

    @Override
    public void start(Stage stage) {
        this.LOGGER.info("Starting Launcher....");
        this.panelManager = new PanelManager(stage);
        this.panelManager.init();

        Requests.initRequests();
        AccountManager.initLocalAccounts();
        NewsManager.initNews();

        this.panelManager.showPanel(new Login());

        JAVA_PATH = getJavaArg();
    }

    @Override
    public void stop() {
        Platform.exit();
        System.exit(0);
    }

    public static WorkerLauncher getInstance() {
        return INSTANCE;
    }

    public ILogger getLogger() {
        return LOGGER;
    }

    public SettingsManager getSettingsManager() {
        return settingsManager;
    }

    public PanelManager getPanelManager() {
        return panelManager;
    }

    public Path getLauncherDirectory() {
        return launcherDirectory;
    }

    public static boolean isDebugMode() {
        return DEBUG;
    }

    public static void setDebugMode(boolean isDebug) {
        DEBUG = isDebug;
    }

    String getJavaArg() {
        Parameters params = getParameters();
        List<String> list = params.getRaw();
        if (list.size() == 0) return null;
        return list.get(1);
    }
}
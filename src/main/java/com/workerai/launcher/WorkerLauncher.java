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

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class WorkerLauncher extends Application {
    private static WorkerLauncher INSTANCE;
    private PanelManager panelManager = null;

    private final ILogger LOGGER;
    private final SettingsManager settingsManager;

    private static boolean DEBUG = false;
    private String JAVA_PATH;

    private final Path launcherFolder = PlatformHandler.createFolder(".WorkerAI");
    private final Path legalFolder = PlatformHandler.createFolder(".WorkerAI", "legal");

    public WorkerLauncher() {
        INSTANCE = this;
        this.LOGGER = new Logger("[WorkerAI]", launcherFolder.resolve("logs"));
        this.createLauncherFolder();

        this.settingsManager = new SettingsManager(new Saver(launcherFolder.resolve("settings").toFile()));
    }

    @Override
    public void start(Stage stage) {
        this.LOGGER.info("Starting Launcher....");
        this.panelManager = new PanelManager(stage);
        this.panelManager.init();

        Requests.initRequests();
        AccountManager.initLocalAccounts();
        NewsManager.initNews();

        this.panelManager.showPanel(new Login(true));

        JAVA_PATH = getJavaArg();
    }

    @Override
    public void stop() {
        Platform.exit();
        System.exit(0);
    }

    void createLauncherFolder() {
        if (!this.launcherFolder.toFile().exists()) {
            if (!this.launcherFolder.toFile().mkdir()) {
                this.LOGGER.err("Unable to create launcher folder");
            }
        }

        if (!this.legalFolder.toFile().exists()) {
            if (!this.legalFolder.toFile().mkdir()) {
                this.LOGGER.err("Unable to create legal folder");
            }
        }

        createLegalFiles();
    }

    void createLegalFiles() {
        File licensesFile = new File(legalFolder.toFile(), "LICENSES");
        File termsFile = new File(legalFolder.toFile(), "TERMS");
        try {
            if(!licensesFile.exists()) { licensesFile.createNewFile();}
            if(!termsFile.exists()) { termsFile.createNewFile();}
        } catch (IOException ex) {
            this.LOGGER.err("Unable to create legal files!");
        }
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

    public Path getLauncherFolder() {
        return launcherFolder;
    }

    public Path getLegalFolder() { return legalFolder; }

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

    public String getJavaPath() {
        return JAVA_PATH;
    }
}
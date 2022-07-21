package com.workerai.launcher;

import com.workerai.launcher.database.Requests;
import com.workerai.launcher.savers.SettingsSaver;
import com.workerai.launcher.ui.PanelManager;
import com.workerai.launcher.ui.panels.pages.Login;
import com.workerai.launcher.utils.PlatformManager;
import fr.flowarg.flowlogger.ILogger;
import fr.flowarg.flowlogger.Logger;
import fr.theshark34.openlauncherlib.util.Saver;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Path;

public class App extends Application {
    private static App INSTANCE;
    private PanelManager panelManager = null;

    private final ILogger LOGGER;
    private final SettingsSaver settingsSaver;

    private final Path launcherDirectory = PlatformManager.createAppFolder(".WorkerAI");

    public App() throws IOException {
        INSTANCE = this;
        this.LOGGER = new Logger("[WorkerAI]", launcherDirectory.resolve("logs.log"));
        if (!this.launcherDirectory.toFile().exists()) {
            if (!this.launcherDirectory.toFile().mkdir()) {
                this.LOGGER.err("Unable to create launcher folder");
            }
        }

        settingsSaver = new SettingsSaver(new Saver(launcherDirectory.resolve("settings.save").toFile()));
    }

    @Override
    public void start(Stage stage) {
        this.LOGGER.info("Starting Launcher....");
        this.panelManager = new PanelManager(stage);
        this.panelManager.init();

        Requests.initRequests();

        this.panelManager.showPanel(new Login());
    }

    @Override
    public void stop() {
        Platform.exit();
        System.exit(0);
    }

    public ILogger getLogger() {
        return LOGGER;
    }

    public static App getInstance() {
        return INSTANCE;
    }

    public SettingsSaver getSettingsManager() {
        return settingsSaver;
    }

    public PanelManager getPanelManager() {
        return panelManager;
    }

    public Path getLauncherDirectory() {
        return launcherDirectory;
    }
}
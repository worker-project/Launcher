package com.workerai.launcher;

import com.workerai.launcher.ui.PanelManager;
import com.workerai.launcher.ui.panels.page.Login;
import com.workerai.launcher.utils.AccountManager;
import com.workerai.launcher.utils.PlatformManager;
import com.workerai.launcher.utils.SettingsManager;
import fr.flowarg.flowlogger.ILogger;
import fr.flowarg.flowlogger.Logger;
import fr.theshark34.openlauncherlib.minecraft.AuthInfos;
import fr.theshark34.openlauncherlib.util.Saver;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Path;

public class App extends Application {
    private static App INSTANCE;

    private PanelManager panelManager = null;
    private AuthInfos authInfos = null;

    private final ILogger LOGGER;

    private final AccountManager accountManager;
    private final SettingsManager settingsManager;

    private final Path launcherDirectory = PlatformManager.createAppFolder(".WorkerAI");

    public App() throws IOException {
        INSTANCE = this;
        this.LOGGER = new Logger("[WorkerAI]", launcherDirectory.resolve("launcher.log"));
        if(!this.launcherDirectory.toFile().exists()) {
            if(!this.launcherDirectory.toFile().mkdir()) {
                this.LOGGER.err("Unable to create launcher folder");
            }
        }

        settingsManager = new SettingsManager(new Saver(launcherDirectory.resolve("settings.save").toFile()));
        accountManager = new AccountManager(new Saver(launcherDirectory.resolve("accounts.save").toFile()));
    }

    @Override
    public void start(Stage stage) {
        this.LOGGER.info("Starting Launcher....");
        this.panelManager = new PanelManager(stage);
        this.panelManager.init();

        this.panelManager.showPanel(new Login());
    }

    @Override
    public void stop() {
        Platform.exit();
        System.exit(0);
    }

    public AuthInfos getAuthInfos() { return this.authInfos; }
    public void setAuthInfos(AuthInfos authInfos) { this.authInfos = authInfos; }
    public ILogger getLogger() { return LOGGER; }
    public static App getInstance() { return INSTANCE; }

    public SettingsManager getSettingsManager() { return settingsManager; }
    public PanelManager getPanelManager() { return panelManager; }
    public AccountManager getAccountManager() { return accountManager; }
    public Path getLauncherDirectory() { return launcherDirectory; }
}
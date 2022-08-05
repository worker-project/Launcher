package com.workerai.launcher.utils;

import com.workerai.launcher.App;
import com.workerai.launcher.savers.AccountManager;
import fr.flowarg.flowupdater.FlowUpdater;
import fr.flowarg.flowupdater.download.DownloadList;
import fr.flowarg.flowupdater.download.IProgressCallback;
import fr.flowarg.flowupdater.versions.VanillaVersion;
import fr.theshark34.openlauncherlib.external.ExternalLaunchProfile;
import fr.theshark34.openlauncherlib.external.ExternalLauncher;
import fr.theshark34.openlauncherlib.minecraft.*;
import javafx.application.Platform;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.Pane;

public class PlayManager {
    private static final ProgressBar progressBar = new ProgressBar();

    private static Pane parentPane;

    public static void downloadAndPlay(Pane pane, boolean isHome) {
        if (App.isDebugMode()) {
            App.getInstance().getLogger().err("You are in debug session, no online services available!");
            AlertManager.ShowWarning(App.getInstance().getPanelManager().getStage(),
                    "Launching Error" ,
                    "You are in debug session, no online services available!\nPlease connect to your account in order to play.");
            return;
        }

        parentPane = pane;
        progressBar.getStyleClass().add("download-progress");
        progressBar.setTranslateY(-160d);

        if(!isHome) progressBar.setTranslateY(-105d);

        PlayManager.setProgress(0, 0);
        PlayManager.addComponents();
        Platform.runLater(() -> new Thread(PlayManager::update).start());
    }

    static void addComponents() {
        parentPane.getChildren().add(progressBar);
    }

    static void removeComponents() {
        parentPane.getChildren().remove(progressBar);
    }

    static void update() {
        IProgressCallback callback = new IProgressCallback() {
            @Override
            public void update(DownloadList.DownloadInfo info) {
                Platform.runLater(() -> {
                    PlayManager.setProgress(info.getDownloadedBytes(), info.getTotalToDownloadBytes());

                    if (info.getTotalToDownloadFiles() == 0) {
                        PlayManager.setProgress(1, 1);
                    }
                });
            }
        };

        try {
            final VanillaVersion vanillaVersion = new VanillaVersion.VanillaVersionBuilder()
                    .withName("1.12.2")
                    .build();
            final FlowUpdater updater = new FlowUpdater.FlowUpdaterBuilder()
                    .withVanillaVersion(vanillaVersion)
                    .withLogger(App.getInstance().getLogger())
                    .withProgressCallback(callback)
                    .build();

            updater.update(App.getInstance().getSettingsManager().getGameDirectory());
            PlayManager.startGame(updater.getVanillaVersion().getName());
        } catch (Exception exception) {
            App.getInstance().getLogger().err(exception.toString());
            AlertManager.ShowError(
                    App.getInstance().getPanelManager().getStage(),
                    "Error",
                    exception.getMessage());
        }
    }

    static void startGame(String gameVersion) {
        GameInfos gInfos = new GameInfos(
                "",
                App.getInstance().getSettingsManager().getGameDirectory(),
                new GameVersion(gameVersion, GameType.V1_8_HIGHER),
                new GameTweak[]{}
        );

        try {
            AuthInfos aInfos = new AuthInfos(AccountManager.getCurrentAccount().getUsername(),
                    AccountManager.getCurrentAccount().getAccessToken(),
                    AccountManager.getCurrentAccount().getClientToken(),
                    AccountManager.getCurrentAccount().getUuid()
            );

            ExternalLaunchProfile profile = MinecraftLauncher.createExternalProfile(gInfos, GameFolder.FLOW_UPDATER, aInfos);
            profile.getVmArgs().add(PlayManager.getRamArgs());
            ExternalLauncher launcher = new ExternalLauncher(profile);

            Process p = launcher.launch();
            Platform.runLater(() -> {
                if (App.getInstance().getSettingsManager().getSaver().get("HideAfterLaunch").equals("true")) {
                    App.getInstance().getPanelManager().getStage().setIconified(true);
                    try {
                        p.waitFor();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                App.getInstance().getPanelManager().getStage().setIconified(false);
            });
        } catch (Exception exception) {
            exception.printStackTrace();
            App.getInstance().getLogger().err(exception.toString());
        }
    }

    static String getRamArgs() {
        int defaultRam = 1024;
        try {
            if (App.getInstance().getSettingsManager().getSaver().get("AllocatedRAM") != null) {
                defaultRam = Integer.parseInt(App.getInstance().getSettingsManager().getSaver().get("AllocatedRAM"));
                System.out.println(defaultRam);
            } else {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException error) {
            App.getInstance().getSettingsManager().getSaver().set("AllocatedRAM", String.valueOf(defaultRam));
            App.getInstance().getSettingsManager().getSaver().save();
        }
        return "-Xmx" + defaultRam + "M";
    }

    static String getHeightArgs() {
        int defaultHeight = 854;
        try {
            if (App.getInstance().getSettingsManager().getSaver().get("LaunchHeight") != null) {
                defaultHeight = Integer.parseInt(App.getInstance().getSettingsManager().getSaver().get("LaunchHeight"));
            } else {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException error) {
            App.getInstance().getSettingsManager().getSaver().set("LaunchHeight", String.valueOf(defaultHeight));
            App.getInstance().getSettingsManager().getSaver().save();
        }
        return "--height" + defaultHeight;
    }

    static String getWidthArgs() {
        int defaultWidth = 480;

        try {
            if (App.getInstance().getSettingsManager().getSaver().get("LaunchWidth") != null) {
                defaultWidth = Integer.parseInt(App.getInstance().getSettingsManager().getSaver().get("LaunchWidth"));
            } else {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException error) {
            App.getInstance().getSettingsManager().getSaver().set("LaunchWidth", String.valueOf(defaultWidth));
            App.getInstance().getSettingsManager().getSaver().save();
        }

        return "--width" + defaultWidth;
    }

    enum StepInfo {
        READ("Reading json file..."),
        DL_LIBS("Downloading libraries..."),
        DL_ASSETS("Downloading resources..."),
        EXTRACT_NATIVES("Extracting Natives..."),
        FORGE("Installing Forge..."),
        FABRIC("Installing Fabric..."),
        MODS("Downloading mods..."),
        EXTERNAL_FILES("Downloading external files..."),
        POST_EVACUATIONS("Executing post-installation..."),
        END("Done!");
        final String details;

        StepInfo(String details) {
            this.details = details;
        }

        public String getDetails() {
            return details;
        }
    }

    static void setProgress(double current, double max) {
        progressBar.setProgress(current / max);
    }
}

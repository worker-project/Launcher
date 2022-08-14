package com.workerai.launcher.utils;

import com.workerai.launcher.WorkerLauncher;
import com.workerai.launcher.database.authentication.TokenResponse;
import com.workerai.launcher.savers.AccountManager;
import fr.flowarg.flowupdater.FlowUpdater;
import fr.flowarg.flowupdater.download.DownloadList;
import fr.flowarg.flowupdater.download.IProgressCallback;
import fr.flowarg.flowupdater.download.json.ExternalFile;
import fr.flowarg.flowupdater.download.json.MCP;
import fr.flowarg.flowupdater.utils.ExternalFileDeleter;
import fr.flowarg.flowupdater.utils.UpdaterOptions;
import fr.flowarg.flowupdater.versions.VanillaVersion;
import fr.theshark34.openlauncherlib.external.ExternalLaunchProfile;
import fr.theshark34.openlauncherlib.external.ExternalLauncher;
import fr.theshark34.openlauncherlib.minecraft.*;
import javafx.application.Platform;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.Pane;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PlayManager {
    private static final ProgressBar progressBar = new ProgressBar();

    private static Pane parentPane;

    static GameFolder gameFolder = new GameFolder("assets", "libraries", "natives", "WorkerClient.jar");

    public static void downloadAndPlay(Pane pane, boolean isHome) {
        if (WorkerLauncher.isDebugMode()) {
            WorkerLauncher.getInstance().getLogger().err("You are in debug session, no online services available!");
            ErrorManager.ShowWarning(WorkerLauncher.getInstance().getPanelManager().getStage(),
                    "Launching Error",
                    "You are in debug session, no online services available!\nPlease connect to your account in order to play.");
            return;
        }

        parentPane = pane;
        progressBar.getStyleClass().add("download-progress");
        progressBar.setTranslateY(-160d);

        if (!isHome) progressBar.setTranslateY(-105d);

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

        List<ExternalFile> files_launcher = ExternalFile.getExternalFilesFromJson("http://185.245.183.191/public/files/WorkerLauncher/");
        if (files_launcher.size() == 0) {
            WorkerLauncher.getInstance().getLogger().err("Could not download MCP files!");
            ErrorManager.ShowWarning(WorkerLauncher.getInstance().getPanelManager().getStage(),
                    "Launching Error",
                    "Could not download MCP files!");
            return;
        }

        ExternalFile launcher = files_launcher.get(0);


        try {
            final UpdaterOptions options = new UpdaterOptions.UpdaterOptionsBuilder()
                    .withExternalFileDeleter(new ExternalFileDeleter())
                    .build();

            final VanillaVersion vanillaVersion = new VanillaVersion.VanillaVersionBuilder()
                    .withName("1.18.2")
                    .withMCP(new MCP(launcher.getDownloadURL(), launcher.getSha1(), launcher.getSize()))
                    .build();
            final FlowUpdater updater = new FlowUpdater.FlowUpdaterBuilder()
                    .withVanillaVersion(vanillaVersion)
                    .withLogger(WorkerLauncher.getInstance().getLogger())
                    .withProgressCallback(callback)
                    .withPostExecutions(Collections.singletonList(PlayManager::startGame))
                    .withUpdaterOptions(options)
                    .build();

            updater.update(WorkerLauncher.getInstance().getSettingsManager().getGameDirectory());
        } catch (Exception exception) {
            WorkerLauncher.getInstance().getLogger().err(exception.toString());
            ErrorManager.ShowError(
                    WorkerLauncher.getInstance().getPanelManager().getStage(),
                    "Error",
                    exception.getMessage());
        }
    }

    static void startGame() {
        GameInfos gInfos = new GameInfos(
                "",
                WorkerLauncher.getInstance().getSettingsManager().getGameDirectory(),
                new GameVersion("1.18.2", GameType.V1_13_HIGHER_VANILLA),
                new GameTweak[]{}
        );

        try {
            AuthInfos aInfos = new AuthInfos(AccountManager.getCurrentAccount().getUsername(),
                    AccountManager.getCurrentAccount().getAccessToken(),
                    AccountManager.getCurrentAccount().getClientToken(),
                    AccountManager.getCurrentAccount().getUuid()
            );

            System.setProperty("java.home", WorkerLauncher.getInstance().getJavaPath());
            ExternalLaunchProfile profile = MinecraftLauncher.createExternalProfile(gInfos, gameFolder, aInfos);

            profile.getVmArgs().add(PlayManager.getRamArgs());
            profile.getArgs().addAll(getMinecraftSizeArgs());

            profile.getArgs().addAll(Arrays.asList("--token", new TokenResponse().getTokenFromUuid(AccountManager.getCurrentAccount().getUuid())));

            ExternalLauncher launcher = new ExternalLauncher(profile);
            Process p = launcher.launch();
            Platform.runLater(() -> {
                if (WorkerLauncher.getInstance().getSettingsManager().getSaver().get("HideAfterLaunch").equals("true")) {
                    WorkerLauncher.getInstance().getPanelManager().getStage().setIconified(true);
                    try {
                        p.waitFor();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                WorkerLauncher.getInstance().getPanelManager().getStage().setIconified(false);
                removeComponents();
            });
        } catch (Exception exception) {
            exception.printStackTrace();
            WorkerLauncher.getInstance().getLogger().err(exception.toString());
        }
    }

    static String getRamArgs() {
        int defaultRam = 1024;
        try {
            if (WorkerLauncher.getInstance().getSettingsManager().getSaver().get("AllocatedRAM") != null) {
                defaultRam = Integer.parseInt(WorkerLauncher.getInstance().getSettingsManager().getSaver().get("AllocatedRAM"));
            } else {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException error) {
            WorkerLauncher.getInstance().getSettingsManager().getSaver().set("AllocatedRAM", String.valueOf(defaultRam));
            WorkerLauncher.getInstance().getSettingsManager().getSaver().save();
        }
        return "-Xmx" + defaultRam + "M";
    }

    static List<String> getMinecraftSizeArgs() {
        int defaultHeight = 854;
        int defaultWidth = 480;

        try {
            defaultHeight = Integer.parseInt(WorkerLauncher.getInstance().getSettingsManager().getSaver().get("LaunchHeight"));
            defaultWidth = Integer.parseInt(WorkerLauncher.getInstance().getSettingsManager().getSaver().get("LaunchWidth"));
        } catch (NumberFormatException error) {
            WorkerLauncher.getInstance().getSettingsManager().getSaver().set("LaunchHeight", String.valueOf(defaultHeight));
            WorkerLauncher.getInstance().getSettingsManager().getSaver().save();

            WorkerLauncher.getInstance().getSettingsManager().getSaver().set("LaunchWidth", String.valueOf(defaultWidth));
            WorkerLauncher.getInstance().getSettingsManager().getSaver().save();
        }
        return Arrays.asList("--width", String.valueOf(defaultWidth), "--height", String.valueOf(defaultHeight));
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

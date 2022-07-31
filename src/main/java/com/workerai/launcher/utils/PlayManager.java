package com.workerai.launcher.utils;

import com.workerai.launcher.App;
import com.workerai.launcher.savers.AccountSaver;
import com.workerai.launcher.ui.panels.partials.BottomBar;
import fr.flowarg.flowupdater.FlowUpdater;
import fr.flowarg.flowupdater.download.DownloadList;
import fr.flowarg.flowupdater.download.IProgressCallback;
import fr.flowarg.flowupdater.download.Step;
import fr.flowarg.flowupdater.versions.VanillaVersion;
import fr.theshark34.openlauncherlib.external.ExternalLaunchProfile;
import fr.theshark34.openlauncherlib.external.ExternalLauncher;
import fr.theshark34.openlauncherlib.minecraft.*;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.StackPane;

import java.nio.file.Path;
import java.text.DecimalFormat;

public class PlayManager {
    private final ProgressBar progressBar = new ProgressBar();
    private final Label stepLabel = new Label();
    private final Label fileLabel = new Label();
    private boolean isDownloading = false;

    private final StackPane homePane;

    public PlayManager(StackPane pane) {
        progressBar.getStyleClass().add("download-progress");
        stepLabel.getStyleClass().add("download-status");
        fileLabel.getStyleClass().add("download-status");

        progressBar.setTranslateY(-15);

        stepLabel.setTranslateY(5);

        fileLabel.setTranslateY(20);

        homePane = pane;
    }

    public void downloadAndPlay() {
        if (BottomBar.DEBUG_MODE) {
            App.getInstance().getLogger().err("You are in debug session, no online services available!");
            return;
        }

        isDownloading = true;
        setProgress(0, 0);
        addComponents();

        Platform.runLater(() -> new Thread(this::update).start());
    }

    void addComponents() {
        homePane.getChildren().addAll(progressBar, stepLabel, fileLabel);
    }

    void removeComponents() {
        homePane.getChildren().removeAll(progressBar, stepLabel, fileLabel);
    }

    void update() {
        IProgressCallback callback = new IProgressCallback() {
            private final DecimalFormat decimalFormat = new DecimalFormat("#.#");
            private String stepTxt = "";
            private String percentTxt = "0.0%";

            @Override
            public void step(Step step) {
                Platform.runLater(() -> {
                    stepTxt = StepInfo.valueOf(step.name()).getDetails();
                    setStatus(String.format("%s, (%s)", stepTxt, percentTxt));
                });
            }

            @Override
            public void update(DownloadList.DownloadInfo info) {
                Platform.runLater(() -> {
                    percentTxt = decimalFormat.format(info.getDownloadedBytes() * 100.d / info.getTotalToDownloadBytes()) + "%";
                    if (!stepTxt.contains("Done!")) String.format("%s, (%s)", stepTxt, percentTxt);
                    setProgress(info.getDownloadedBytes(), info.getTotalToDownloadBytes());
                });
            }

            @Override
            public void onFileDownloaded(Path path) {
                Platform.runLater(() -> {
                    String p = path.toString();
                    fileLabel.setText("..." + p.replace(App.getInstance().getSettingsManager().getGameDirectory().toFile().getAbsolutePath(), ""));
                });
            }
        };

        try {
            final VanillaVersion vanillaVersion = new VanillaVersion.VanillaVersionBuilder()
                    .withName("1.8.8")
                    .build();
            final FlowUpdater updater = new FlowUpdater.FlowUpdaterBuilder()
                    .withVanillaVersion(vanillaVersion)
                    .withLogger(App.getInstance().getLogger())
                    .withProgressCallback(callback)
                    .build();

            updater.update(App.getInstance().getSettingsManager().getGameDirectory());
            this.startGame(updater.getVanillaVersion().getName());
        } catch (Exception exception) {
            App.getInstance().getLogger().err(exception.toString());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("An error occurred when updating files...");
            alert.setContentText(exception.getMessage());
            alert.show();
        }
    }

    void startGame(String gameVersion) {
        GameInfos gInfos = new GameInfos(
                "",
                App.getInstance().getSettingsManager().getGameDirectory(),
                new GameVersion(gameVersion, GameType.V1_8_HIGHER),
                new GameTweak[]{}
        );

        try {
            AuthInfos aInfos = new AuthInfos(AccountSaver.getCurrentAccount().getUsername(),
                    AccountSaver.getCurrentAccount().getAccessToken(),
                    AccountSaver.getCurrentAccount().getClientToken(),
                    AccountSaver.getCurrentAccount().getUuid()
            );

            ExternalLaunchProfile profile = MinecraftLauncher.createExternalProfile(gInfos, GameFolder.FLOW_UPDATER, aInfos);
            profile.getVmArgs().add(this.getRamArgs());
            ExternalLauncher launcher = new ExternalLauncher(profile);

            Process p = launcher.launch();
            Platform.runLater(() -> {
                if (App.getInstance().getSettingsManager().getSaver().get("HideAfterLaunch").equals("true"))
                    App.getInstance().getPanelManager().getStage().setIconified(true);
                try {
                    p.waitFor();
                    App.getInstance().getPanelManager().getStage().setIconified(false);
                    removeComponents();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        } catch (Exception exception) {
            exception.printStackTrace();
            App.getInstance().getLogger().err(exception.toString());
        }
    }

    String getRamArgs() {
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

    String getHeightArgs() {
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

    String getWidthArgs() {
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

    void setProgress(double current, double max) {
        this.progressBar.setProgress(current / max);
    }

    void setStatus(String status) {
        this.stepLabel.setText(status);
    }
}
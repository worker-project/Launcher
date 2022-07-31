package com.workerai.launcher.ui.panels.pages;

import com.workerai.launcher.App;
import com.workerai.launcher.savers.AccountSaver;
import com.workerai.launcher.ui.PanelManager;
import com.workerai.launcher.ui.utils.Panel;
import com.workerai.launcher.ui.panels.partials.BottomBar;
import com.workerai.launcher.utils.ResourceManager;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import fr.flowarg.flowupdater.FlowUpdater;
import fr.flowarg.flowupdater.download.DownloadList;
import fr.flowarg.flowupdater.download.IProgressCallback;
import fr.flowarg.flowupdater.download.Step;
import fr.flowarg.flowupdater.versions.VanillaVersion;
import fr.theshark34.openlauncherlib.external.ExternalLaunchProfile;
import fr.theshark34.openlauncherlib.external.ExternalLauncher;
import fr.theshark34.openlauncherlib.minecraft.*;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

import java.nio.file.Path;
import java.text.DecimalFormat;

import static com.noideaindustry.jui.JuiInterface.JuiButton.createFontButton;
import static com.noideaindustry.jui.JuiInterface.JuiButton.createMaterialButton;
import static com.noideaindustry.jui.JuiInterface.JuiIcon;
import static com.noideaindustry.jui.JuiInterface.JuiPane.createGridPane;
import static com.noideaindustry.jui.JuiInterface.JuiPane.createStackPane;
import static com.noideaindustry.jui.JuiInterface.createLabel;

public class Home extends Panel {
    ProgressBar progressBar = new ProgressBar();
    Label stepLabel = new Label();
    Label fileLabel = new Label();
    boolean isDownloading = false;

    @Override
    public void init(PanelManager panelManager) {
        super.init(panelManager);

        BottomBar.getInstance().setHomeIcons();

        GridPane backgroundPane = createGridPane(0d, 0d, 0d, 0d, 0d, 0d, "background-login", Color.TRANSPARENT);
        this.layout.getChildren().add(backgroundPane);
        setCanTakeAllSize(this.layout, backgroundPane);

        StackPane homePane = createStackPane(0d, 0d, 1200d, 600d, 15d, 15d, "home-panel", Pos.CENTER, Color.rgb(32, 31, 29));
        this.layout.getChildren().add(homePane);

        FontAwesomeIconView titleIcon = JuiIcon.createFontIcon(-4d, 0, FontAwesomeIcon.GEARS, "25px", null,Color.WHITE, homePane);
        createLabel(0d, 90d, "Launcher & Client Settings", titleIcon, "home-label", Pos.TOP_CENTER, homePane);
        createLabel(0d, 120d, "Memory allocation & Launcher preferences", null, "home-subLabel", Pos.TOP_CENTER, homePane);

        FontAwesomeIconView playIcon = JuiIcon.createFontIcon(-5d, 0d, FontAwesomeIcon.PLAY_CIRCLE, "50px", null, Color.WHITE, homePane);
        Button playButton = createFontButton(200d, 0d, 350d, 80d, "LAUNCH CLIENT", "home-button", null, playIcon, Pos.CENTER, homePane);
        playButton.setOnMouseClicked(e -> downloadAndplay(homePane));

        progressBar.getStyleClass().add("download-progress");
        stepLabel.getStyleClass().add("download-status");
        fileLabel.getStyleClass().add("download-status");

        progressBar.setTranslateY(-15);
        setCenterH(progressBar);
        setCanTakeAllWidth(progressBar);

        stepLabel.setTranslateY(5);
        setCenterH(stepLabel);
        setCanTakeAllSize(stepLabel);

        fileLabel.setTranslateY(20);
        setCenterH(fileLabel);
        setCanTakeAllSize(fileLabel);

        MaterialDesignIconView list = new MaterialDesignIconView(MaterialDesignIcon.VIEW_LIST);
        list.setFill(Color.WHITE);
        list.setSize("25px");
        list.setTranslateX(-5d);

        MaterialDesignIconView accountsIcon = JuiIcon.createDesignIcon(-5d, 0d, MaterialDesignIcon.VIEW_LIST, "50px", null, Color.WHITE, homePane);
        Button accountsButton = createMaterialButton(-200d, 0d, 350d, 80d, "ACCOUNT MANAGER", "home-button", null, accountsIcon, Pos.CENTER, homePane);
        accountsButton.setOnMouseClicked(e -> this.panelManager.showPanel(new Accounts()));
    }

    private void downloadAndplay(StackPane pane) {
        if (BottomBar.DEBUG_MODE) {
            App.getInstance().getLogger().err("You are in debug session, no online services available!");
            return;
        }

        isDownloading = true;
        setProgress(0, 0);
        pane.getChildren().addAll(progressBar, stepLabel, fileLabel);

        Platform.runLater(() -> new Thread(this::update).start());
    }

    public void update() {
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

    public void startGame(String gameVersion) {
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
            /*profile.getVmArgs().add(this.getHeightArgs());
            profile.getVmArgs().add(this.getWidthArgs());*/
            ExternalLauncher launcher = new ExternalLauncher(profile);

            Process p = launcher.launch();
            Platform.runLater(() -> {
                if (App.getInstance().getSettingsManager().getSaver().get("HideAfterLaunch").equals("true"))
                    panelManager.getStage().setIconified(true);
                try {
                    p.waitFor();
                    panelManager.getStage().setIconified(false);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        } catch (Exception exception) {
            exception.printStackTrace();
            App.getInstance().getLogger().err(exception.toString());
        }
    }

    public String getRamArgs() {
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

    public String getHeightArgs() {
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

    public String getWidthArgs() {
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

    public enum StepInfo {
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

    public void setProgress(double current, double max) {
        this.progressBar.setProgress(current / max);
    }

    public void setStatus(String status) {
        this.stepLabel.setText(status);
    }

    @Override
    public String getStylesheetPath() {
        return ResourceManager.getHomeDesign();
    }
}

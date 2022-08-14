package com.workerai.launcher.savers;

import com.workerai.launcher.WorkerLauncher;
import fr.theshark34.openlauncherlib.util.Saver;

import java.nio.file.Files;
import java.nio.file.Path;

public class SettingsManager {
    private final Saver saver;
    private Path gameDirectory;

    public SettingsManager(Saver saver) {
        this.saver = saver;
        this.saver.load();

        checkSettings();
        checkGameDirectory();
    }

    private void checkSettings() {
        if (saver.get("AllocatedRAM") == null) {
            WorkerLauncher.getInstance().getLogger().info("Creating \"AllocatedRAM\" in setting file!");
            saver.set("AllocatedRAM", String.valueOf(512));
        }

        if (saver.get("GameDirectory") == null) {
            WorkerLauncher.getInstance().getLogger().info("Creating \"AllocatedRAM\" in setting file!");
            saver.set("GameDirectory", String.valueOf(WorkerLauncher.getInstance().getLauncherFolder()));
        }

        if (saver.get("HideAfterLaunch") == null) {
            WorkerLauncher.getInstance().getLogger().info("Creating \"HideAfterLaunch\" in setting file!");
            saver.set("HideAfterLaunch", String.valueOf(false));
        }

        if (saver.get("LaunchHeight") == null) {
            WorkerLauncher.getInstance().getLogger().info("Creating \"LaunchHeight\" in setting file!");
            saver.set("LaunchHeight", String.valueOf(480));
        }

        if (saver.get("KeepAfterLaunch") == null) {
            WorkerLauncher.getInstance().getLogger().info("Creating \"KeepAfterLaunch\" in setting file!");
            saver.set("KeepAfterLaunch", String.valueOf(true));
        }

        if (saver.get("LaunchWidth") == null) {
            WorkerLauncher.getInstance().getLogger().info("Creating \"LaunchWidth\" in setting file!");
            saver.set("LaunchWidth", String.valueOf(854));
        }
    }

    public void checkGameDirectory() {
        if (Files.notExists(Path.of(saver.get("GameDirectory")))) {
            WorkerLauncher.getInstance().getLogger().info("No game directory found, setting default directory at:" + WorkerLauncher.getInstance().getLauncherFolder());
            saver.set("GameDirectory", String.valueOf(WorkerLauncher.getInstance().getLauncherFolder()));
            setGameDirectory(Path.of(String.valueOf(WorkerLauncher.getInstance().getLauncherFolder())));
        } else {
            WorkerLauncher.getInstance().getLogger().info("Found game directory at:" + saver.get("GameDirectory"));
            setGameDirectory(Path.of(saver.get("GameDirectory")));
        }
    }

    public Saver getSaver() {
        return this.saver;
    }

    public void setGameDirectory(Path path) {
        gameDirectory = path;
        saver.set("GameDirectory", String.valueOf(path));
    }

    public Path getGameDirectory() {
        return gameDirectory;
    }
}

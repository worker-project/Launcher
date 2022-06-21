package com.workerai.launcher.utils;

import com.workerai.launcher.App;
import fr.theshark34.openlauncherlib.util.Saver;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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
        if(saver.get("AllocatedRAM") == null) {
            App.getInstance().getLogger().info("Creating \"AllocatedRAM\" in setting file!");
            saver.set("AllocatedRAM", String.valueOf(512/1024.0));
        }

        if(saver.get("GameDirectory") == null) {
            App.getInstance().getLogger().info("Creating \"AllocatedRAM\" in setting file!");
            saver.set("AllocatedRAM", String.valueOf(512/1024.0));
        }

        if(saver.get("HideAfterLaunch") == null) {
            App.getInstance().getLogger().info("Creating \"HideAfterLaunch\" in setting file!");
            saver.set("HideAfterLaunch", String.valueOf(false));
        }

        if(saver.get("LaunchHeight") == null) {
            App.getInstance().getLogger().info("Creating \"LaunchHeight\" in setting file!");
            saver.set("LaunchHeight", String.valueOf(480));
        }

        if(saver.get("KeepAfterLaunch") == null) {
            App.getInstance().getLogger().info("Creating \"KeepAfterLaunch\" in setting file!");
            saver.set("KeepAfterLaunch", String.valueOf(true));
        }

        if(saver.get("LaunchWidth") == null) {
            App.getInstance().getLogger().info("Creating \"LaunchWidth\" in setting file!");
            saver.set("LaunchWidth", String.valueOf(854));
        }

        if(saver.get("AutoAuth") == null) {
            App.getInstance().getLogger().info("Creating \"AutoAuth\" in setting file!");
            saver.set("AutoAuth", String.valueOf(854));
        }
    }

    public void checkGameDirectory() {
        if(Files.notExists(Path.of(saver.get("GameDirectory")))) {
            App.getInstance().getLogger().info("No game directory found, setting default directory at:" + App.getInstance().getLauncherDirectory());
            saver.set("GameDirectory", String.valueOf(App.getInstance().getLauncherDirectory()));
            setGameDirectory(Path.of(String.valueOf(App.getInstance().getLauncherDirectory())));
        } else {
            App.getInstance().getLogger().info("Found game directory at:" + saver.get("GameDirectory"));
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

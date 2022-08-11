package com.workerai.launcher.utils;

import fr.flowarg.flowcompat.Platform;

import java.nio.file.Path;
import java.nio.file.Paths;

public class PlatformHandler {
    public static Path createFolder(String... folderName) {
        Path path;
        switch (Platform.getCurrentPlatform()) {
            case WINDOWS:
                path = Paths.get(System.getenv("APPDATA"));
                break;
            case MAC:
                path = Paths.get(System.getProperty("user.home"), "/Library/Application Support/*");
                break;
            case LINUX:
                path = Paths.get(System.getProperty("user.home"), ".local/share");
            default:
                path = Paths.get(System.getProperty("user.home"));
        }

        path = Paths.get(path.toString(), folderName);
        return path;
    }
}

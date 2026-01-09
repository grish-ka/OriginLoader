package com.mojang.rubydung;

import java.io.File;
import com.mojang.rubydung.OriginLogger;

public class OriginDirectories {
    // This will now point to project_root/run/.minecraft when launched via Gradle
    public static final File MINECRAFT_DIR = new File(System.getProperty("user.home"), ".minecraft");
    public static final File LOADER_DIR = new File(MINECRAFT_DIR, ".OriginLoader");
    public static final File VERSIONS_DIR = new File(MINECRAFT_DIR, "versions");
    public static final File LOGS_DIR = new File(MINECRAFT_DIR, "logs");
    public static final File WORLDS_DIR = new File(MINECRAFT_DIR, "worlds");

    public static void init() {
        if (!MINECRAFT_DIR.exists()) {
            OriginLogger.LOGGER.info("Creating local .minecraft directory...");
            MINECRAFT_DIR.mkdirs();
        }
        if (!LOADER_DIR.exists()) LOADER_DIR.mkdirs();
        if (!VERSIONS_DIR.exists()) VERSIONS_DIR.mkdirs();
        if (!LOGS_DIR.exists()) LOGS_DIR.mkdirs();
        if (!WORLDS_DIR.exists()) WORLDS_DIR.mkdirs();
    }
}
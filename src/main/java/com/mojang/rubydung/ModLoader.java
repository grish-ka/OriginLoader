// src/main/java/com/mojang/rubydung/ModLoader.java
package com.mojang.rubydung;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.jar.JarFile;

// import local systems
import com.mojang.rubydung.OriginDirectories;
import com.mojang.rubydung.OriginLogger;

public class ModLoader {
    public static void loadMods() {
        File[] files = OriginDirectories.MODS_DIR.listFiles((dir, name) -> name.endsWith(".jar"));
        if (files == null) return;

        for (File file : files) {
            try {
                OriginLogger.LOGGER.info("Loading mod: " + file.getName());
                
                // Add JAR to ClassLoader
                URL url = file.toURI().toURL();
                URLClassLoader classLoader = new URLClassLoader(new URL[]{url}, ModLoader.class.getClassLoader());
                
                // Search for an entry point in the manifest or a standard class name
                // For this simple version, we'll look for "OriginMod"
                Class<?> modClass = Class.forName("OriginMod", true, classLoader);
                modClass.getDeclaredMethod("init").invoke(null);
                
                OriginLogger.LOGGER.info("Successfully initialized " + file.getName());
            } catch (Exception e) {
                OriginLogger.LOGGER.warning("Failed to load mod " + file.getName() + ": " + e.getMessage());
            }
        }
    }
}
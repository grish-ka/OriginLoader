// src/main/java/com/mojang/rubydung/ModLoader.java
package com.mojang.rubydung;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.JarURLConnection;
import java.util.jar.JarFile;
import java.util.jar.Manifest;
import java.util.jar.Attributes;

// import local systems
import com.mojang.rubydung.OriginDirectories;
import com.mojang.rubydung.OriginLogger;

public class ModLoader {
    // API Hook for World Generation
    public static java.util.function.BiFunction<Integer, Integer, com.mojang.rubydung.level.Tile> worldGenHook = null;

    public static void loadMods() {
        File[] files = OriginDirectories.MODS_DIR.listFiles((dir, name) -> name.endsWith(".jar"));
        if (files == null) return;

        for (File file : files) {
            try {
                OriginLogger.LOGGER.info("Loading mod: " + file.getName());
                
                URL url = file.toURI().toURL();
                URLClassLoader classLoader = new URLClassLoader(new URL[]{url}, ModLoader.class.getClassLoader());
                String modClassName = null;

                // Attempt to read the entry point from the Manifest
                try (JarFile jarFile = new JarFile(file)) {
                    Manifest manifest = jarFile.getManifest();
                    if (manifest != null) {
                        modClassName = manifest.getMainAttributes().getValue("Origin-Mod-Class");
                    }
                }

                // If no manifest attribute, fallback to the standard "OriginMod"
                if (modClassName == null) {
                    modClassName = "OriginMod";
                }

                Class<?> modClass = Class.forName(modClassName, true, classLoader);
                modClass.getDeclaredMethod("init").invoke(null);
                
                OriginLogger.LOGGER.info("Successfully initialized mod: " + modClassName);
            } catch (Exception e) {
                OriginLogger.LOGGER.warning("Failed to load mod " + file.getName() + ": " + e.getMessage());
            }
        }
    }
}
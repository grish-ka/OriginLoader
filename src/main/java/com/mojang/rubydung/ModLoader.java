// src/main/java/com/mojang/rubydung/ModLoader.java
package com.mojang.rubydung;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.JarURLConnection;
import java.util.jar.JarFile;
import java.util.jar.Manifest;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.Attributes;

// import local systems
import com.mojang.rubydung.OriginDirectories;
import com.mojang.rubydung.OriginLogger;
import com.mojang.rubydung.level.IWorldGenerator;

public class ModLoader {
    // A list to hold all registered mod generators
    public static final List<IWorldGenerator> GENERATORS = new ArrayList<>();
    public static int terrainTextureId;

    public static void registerGenerator(IWorldGenerator gen) {
        OriginLogger.LOGGER.info("Registering world generator: " + gen.getClass().getName());
        GENERATORS.add(gen);
    }

    public static void loadMods() {
        File[] files = OriginDirectories.MODS_DIR.listFiles((dir, name) -> name.endsWith(".jar"));
        if (files == null) return;

        for (File file : files) {
            try {
                OriginLogger.LOGGER.info("Loading mod: " + file.getName());
                
                URL url = file.toURI().toURL();
                URLClassLoader classLoader = new URLClassLoader(new URL[]{url}, ModLoader.class.getClassLoader());
                String modClassName = null;
                String modVersion = "unknown";

                // Attempt to read the entry point from the Manifest
                try (JarFile jarFile = new JarFile(file)) {
                    Manifest manifest = jarFile.getManifest();
                    if (manifest != null) {
                        modClassName = manifest.getMainAttributes().getValue("Origin-Mod-Class");
                        modVersion = manifest.getMainAttributes().getValue("Origin-Mod-Version");
                    }
                }

                // If no manifest attribute, fallback to the standard "OriginMod"
                if (modClassName == null) {
                    modClassName = "OriginMod";
                }
                if (modVersion == null) {
                    modVersion = "unknown version";
                }

                Class<?> modClass = Class.forName(modClassName, true, classLoader);
                modClass.getDeclaredMethod("init").invoke(null);
                
                OriginLogger.LOGGER.info("Successfully initialized mod: " + modClassName + " v" + modVersion);
            } catch (Exception e) {
                OriginLogger.LOGGER.warning("Failed to load mod " + file.getName() + ": " + e.getMessage());
            }
            terrainTextureId = Textures.bakeAtlas("/terrain.png");
        }
    }
}
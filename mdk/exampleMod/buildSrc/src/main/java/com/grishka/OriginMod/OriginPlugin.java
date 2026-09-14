package com.grishka.OriginMod;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.tasks.bundling.Jar;
import java.io.File;

import com.grishka.OriginMod.Extensions.OriginModExtension;

public class OriginPlugin implements Plugin<Project> {
    @Override
    public void apply(Project project) {
        // 0. Create and add the extension to the project
        OriginModExtension extension = project.getExtensions()
            .create("originMod", OriginModExtension.class);

        // 1. Automatically configure the JAR manifest for mods
        project.getTasks().withType(Jar.class).configureEach(jar -> {
            jar.getManifest().getAttributes().put("Origin-Mod-Version", extension.MODVERSION);
            // This ensures every mod built with this plugin is 'Origin-ready'
        });

        // 2. Add a custom task to verify 16x16 textures
        project.getTasks().register("verifyTextures", task -> {
            task.setGroup("verification");
            task.doLast(s -> {
                File res = project.file("src/main/resources");
                // You could add logic here to check if .png files are exactly 16x16
                project.getLogger().lifecycle("Verifying mod textures for OriginLoader...");
            });
        });
    }
}
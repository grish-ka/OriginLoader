package com.mojang.rubydung;

import java.util.ArrayList;
import java.util.List;

public class TextureRegistry {
    private static int nextX = 2; // Start after Rock(0,0) and Grass(1,0)
    private static int nextY = 0;
    
    // Store the textures that need to be "baked"
    public static List<ModTexture> pendingTextures = new ArrayList<>();

    public static class ModTexture {
        public String resourcePath;
        public int u, v;
        public ClassLoader loader;

        public ModTexture(String path, int u, int v, ClassLoader loader) {
            this.resourcePath = path;
            this.u = u;
            this.v = v;
            this.loader = loader;
        }
    }

    public static int[] registerModTexture(String resourcePath, ClassLoader loader) {
        int[] slot = {nextX, nextY};
        pendingTextures.add(new ModTexture(resourcePath, nextX, nextY, loader));
        
        nextX++;
        if (nextX >= 16) {
            nextX = 0;
            nextY++;
        }
        return slot;
    }
}
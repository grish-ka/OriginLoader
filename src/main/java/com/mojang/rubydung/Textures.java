package com.mojang.rubydung;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.HashMap;
import javax.imageio.ImageIO;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;
import java.awt.Graphics2D;

public class Textures {
   private static HashMap<String, Integer> idMap = new HashMap<>();
   private static int lastId = -9999999;

   public static int loadTexture(String resourceName, int mode) {
      try {
         if (idMap.containsKey(resourceName)) {
            return idMap.get(resourceName);
         } else {
            IntBuffer ib = BufferUtils.createIntBuffer(1);
            GL11.glGenTextures(ib);
            int id = ib.get(0);
            bind(id);
            GL11.glTexParameteri(3553, 10241, mode);
            GL11.glTexParameteri(3553, 10240, mode);
            BufferedImage img = ImageIO.read(Textures.class.getResourceAsStream(resourceName));
            int w = img.getWidth();
            int h = img.getHeight();
            ByteBuffer pixels = BufferUtils.createByteBuffer(w * h * 4);
            int[] rawPixels = new int[w * h];
            img.getRGB(0, 0, w, h, rawPixels, 0, w);

            for (int i = 0; i < rawPixels.length; i++) {
               int a = rawPixels[i] >> 24 & 0xFF;
               int r = rawPixels[i] >> 16 & 0xFF;
               int g = rawPixels[i] >> 8 & 0xFF;
               int b = rawPixels[i] & 0xFF;
               rawPixels[i] = a << 24 | b << 16 | g << 8 | r;
            }

            pixels.asIntBuffer().put(rawPixels);
            GLU.gluBuild2DMipmaps(3553, 6408, w, h, 6408, 5121, pixels);
            return id;
         }
      } catch (IOException var14) {
         throw new RuntimeException("!!");
      }
   }

   public static int bakeAtlas(String baseResource) {
        try {
            // 1. Load the original terrain.png
            BufferedImage base = ImageIO.read(Textures.class.getResourceAsStream(baseResource));
            int w = base.getWidth();
            int h = base.getHeight();

            // 2. Create a "canvas" for the master atlas
            BufferedImage atlas = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = atlas.createGraphics();
            
            // 3. Draw the original textures first
            g.drawImage(base, 0, 0, null);

            // 4. "Bake" each mod texture into its assigned slot
            for (TextureRegistry.ModTexture modTex : TextureRegistry.pendingTextures) {
                // Load the mod's specific 16x16 PNG
                BufferedImage modImg = ImageIO.read(modTex.loader.getResourceAsStream(modTex.resourcePath));
                if (modImg != null) {
                    // Draw it at (U * 16, V * 16)
                    g.drawImage(modImg, modTex.u * 16, modTex.v * 16, null);
                    OriginLogger.LOGGER.info("Baked mod texture: " + modTex.resourcePath + " at [" + modTex.u + "," + modTex.v + "]");
                }
            }
            g.dispose();

            // 5. Convert the finished atlas to an OpenGL texture
            return uploadTexture(atlas); 
        } catch (Exception e) {
            throw new RuntimeException("Failed to bake texture atlas!", e);
        }
    }
    
    // Helper to upload a BufferedImage to GL (reusing your existing logic)
    private static int uploadTexture(BufferedImage img) {
        int w = img.getWidth();
        int h = img.getHeight();
        IntBuffer ib = BufferUtils.createIntBuffer(1);
        GL11.glGenTextures(ib);
        int id = ib.get(0);
        bind(id);
        GL11.glTexParameteri(3553, 10241, 9728);
        GL11.glTexParameteri(3553, 10240, 9728);
        
        int[] raw = new int[w * h];
        img.getRGB(0, 0, w, h, raw, 0, w);
        for (int i = 0; i < raw.length; i++) {
            int a = raw[i] >> 24 & 0xFF;
            int r = raw[i] >> 16 & 0xFF;
            int g = raw[i] >> 8 & 0xFF;
            int b = raw[i] & 0xFF;
            raw[i] = a << 24 | b << 16 | g << 8 | r;
        }
        ByteBuffer pixels = BufferUtils.createByteBuffer(w * h * 4);
        pixels.asIntBuffer().put(raw);
        GLU.gluBuild2DMipmaps(3553, 6408, w, h, 6408, 5121, pixels);
        return id;
    }

   public static void bind(int id) {
      if (id != lastId) {
         GL11.glBindTexture(3553, id);
         lastId = id;
      }
   }
}

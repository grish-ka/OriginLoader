package com.grishka.OriginLoader.helloMod.level.Textures.Custom;

import com.mojang.rubydung.TextureRegistry;
import com.mojang.rubydung.level.Tile;

public class Test_Block {
    public static final String RESOURCE_PATH = "assets/helloMod/Textures/Blocks/Test_Block.png"; 
    private static final int[] slot = TextureRegistry.registerModTexture(RESOURCE_PATH, Test_Block.class.getClassLoader());   
    public static Tile testBlockTile = new Tile(slot[0], slot[1]);
    public static int getU(){
        return slot[0];
    }
    public static int getV(){   
        return slot[1];
    }
    public static String getResourcePath(){
        return RESOURCE_PATH;
    }
}

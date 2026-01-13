package com.grishka.OriginLoader.helloMod.level.Blocks;

import com.mojang.rubydung.ModLoader;
import com.grishka.OriginLoader.helloMod.level.Blocks.Custom.*;

public class ModBlocks {
    public static void init() {
        // Initialize mod blocks here
        ModLoader.registerGenerator(new testBlock()); 
    }
}

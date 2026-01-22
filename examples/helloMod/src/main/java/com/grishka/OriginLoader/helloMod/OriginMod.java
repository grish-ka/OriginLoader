package com.grishka.OriginLoader.helloMod;

import com.mojang.rubydung.OriginLogger;
import com.grishka.OriginLoader.helloMod.level.Blocks.ModBlocks;

public class OriginMod {
    public static void init() {
        OriginLogger.LOGGER.info("Hello from helloMod!");
        ModBlocks.init();
    }
}
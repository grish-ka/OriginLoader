package com.grishka.OriginLoader.helloMod;

import com.mojang.rubydung.OriginLogger;

public class OriginMod {
    public static void init() {
        OriginLogger.LOGGER.info("Hello from helloMod!");
    }
}
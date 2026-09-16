package com.example.exampleMod;

import com.mojang.rubydung.OriginLogger;

public class OriginMod {
    public static String MODID = "exampleMod";
    public static void init() {
        OriginLogger.LOGGER.info("Hello from " + MODID + "!");
    }
}
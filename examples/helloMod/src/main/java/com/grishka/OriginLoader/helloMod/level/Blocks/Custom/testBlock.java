package com.grishka.OriginLoader.helloMod.level.Blocks.Custom;

import com.mojang.rubydung.level.IWorldGenerator;
import com.mojang.rubydung.level.Tile;

public class testBlock implements IWorldGenerator {
    public static Tile testTile = new Tile(2);
    @Override
    public Tile getTile(int y, int depth, Tile currentTile) {
        if (y > depth * 2/3) return testTile; // test block above 2/3 height
        return currentTile; // Otherwise, keep what was there
    }
}
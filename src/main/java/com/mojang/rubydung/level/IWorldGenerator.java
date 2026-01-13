package com.mojang.rubydung.level;

// In com.mojang.rubydung.level
public interface IWorldGenerator {
    /**
     * @param y Current vertical coordinate
     * @param depth Total world depth
     * @param currentTile The tile decided by previous mods or the engine
     * @return The tile this mod wants to place
     */
    Tile getTile(int y, int depth, Tile currentTile);
}
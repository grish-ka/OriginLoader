# exampleMod
<p align="center">
  <img src="https://img.shields.io/badge/Java-17-orange?logo=openjdk">
  <img src="https://img.shields.io/badge/Gradle-8.5-blue?logo=gradle">
</p>

**exampleMod** is a mod template for the **minecraft version rd-132211** using **OriginLoader**

## Requirements:
 - java 17

## Run
```bash
$ ./gradlew run
```

## a quick toutrial

So you want to add a block easy!
you have the mdk already soo...

inside `level/Blocks` create a `ModBlocks.java` file,
inside you want
```java
package com.example.exampleMod.level.Blocks;

import com.mojang.rubydung.ModLoader;
import com.example.exampleMod.level.Blocks.Custom.*;

public class ModBlocks {
    public static void init() {
        // Initialize mod blocks here
    }
}
```
and then create a folder called `Custom`
make a `.java` file called `exampleBlock`
inside:
```java
package com.example.exampleMod.level.Blocks.Custom;

import com.mojang.rubydung.level.IWorldGenerator;
import com.mojang.rubydung.level.Tile;
import com.example.exampleMod.level.Textures.ModTextures;

public class exampleBlock implements IWorldGenerator {
    public static Tile exampleTile = ModTextures.testBlockTile;
    @Override
    public Tile getTile(int y, int depth, Tile currentTile) {
        if (y > depth * 2/3) return exampleTile; // example block above 2/3 height
        return currentTile; // Otherwise, keep what was there
    }
}
```
And then back in `ModBlocks` add
```java
public static void init() {
    // Initialize mod blocks here
    ModLoader.registerGenerator(new exampleBlock());
}
```
next go to `com.example.exampleMod.level.Textures`
add `ModTextures`
```java
package com.example.exampleMod.level.Textures;

import com.example.exampleMod.level.Textures.Custom.Example_Block;
import com.mojang.rubydung.level.Tile;

public class ModTextures {
    public static Tile exampleBlockTile = Example_Block.exampleBlockTile;
}
```
in a foldder called `Custom`

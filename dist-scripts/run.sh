#!/bin/bash
cd "$(dirname "$0")"

java -Djava.library.path=bin/natives \
     -Dorg.lwjgl.opengl.Display.noXRandR=true \
     --add-opens=java.base/java.nio=ALL-UNNAMED \
     --add-opens=java.desktop/sun.awt=ALL-UNNAMED \
     -cp "bin/OriginLoader-1.0.0.jar" \
     com.mojang.rubydung.RubyDung
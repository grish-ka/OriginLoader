#!/bin/bash
set -e

cd "$(dirname "$0")"
ROOT_DIR="$(pwd)"

mkdir -p ".minecraft/mods"

java -Duser.home="$ROOT_DIR" \
     -Djava.library.path=".minecraft/bin/natives" \
     -Dorg.lwjgl.opengl.Display.noXRandR=true \
     --enable-native-access=ALL-UNNAMED \
     --add-opens=java.base/java.nio=ALL-UNNAMED \
     --add-opens=java.desktop/sun.awt=ALL-UNNAMED \
     -cp ".minecraft/versions/rd-132211.jar" \
     com.mojang.rubydung.RubyDung
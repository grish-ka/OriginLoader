@echo off
title OriginLoader - rd-132211
cd /d "%~dp0"

REM Ensure mods directory exists
if not exist ".minecraft\mods" mkdir ".minecraft\mods"

REM Use %CD% (no trailing backslash) so Java does not misinterpret the closing quote
java -Duser.home="%CD%" ^
     -Djava.library.path=".minecraft\bin\natives" ^
     -Dorg.lwjgl.opengl.Display.noXRandR=true ^
     --enable-native-access=ALL-UNNAMED ^
     --add-opens=java.base/java.nio=ALL-UNNAMED ^
     --add-opens=java.desktop/sun.awt=ALL-UNNAMED ^
     -cp ".minecraft\versions\rd-132211.jar" ^
     com.mojang.rubydung.RubyDung

if %ERRORLEVEL% NEQ 0 (
    echo.
    echo Game closed with an error. Ensure Java 17+ is installed.
    pause
)
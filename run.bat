@echo off
title OriginLoader - rd-132211
cd /d "%~dp0"

java -Djava.library.path=bin/natives ^
     --add-opens=java.base/java.nio=ALL-UNNAMED ^
     --add-opens=java.desktop/sun.awt=ALL-UNNAMED ^
     -cp "bin/OriginLoader-1.0.0.jar" ^
     com.mojang.rubydung.RubyDung

if %ERRORLEVEL% NEQ 0 (
    echo.
    echo Game closed with an error. Ensure Java 17+ is installed.
    pause
)

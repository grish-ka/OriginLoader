// src/main/java/com/mojang/rubydung/OriginLogger.java
package com.mojang.rubydung;

import java.util.logging.*;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class OriginLogger {
    public static final Logger LOGGER = Logger.getLogger("OriginLoader");

    public static void setup() {
        try {
            LogManager.getLogManager().reset();
            LOGGER.setLevel(Level.ALL);

            // Minecraft-style format: [14:30:05] [main/INFO]: Message
            Formatter formatter = new Formatter() {
                private final SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
                @Override
                public String format(LogRecord record) {
                    return String.format("[%s] [%s/%s]: %s\n",
                        df.format(new Date(record.getMillis())),
                        Thread.currentThread().getName(),
                        record.getLevel().getLocalizedName(),
                        formatMessage(record));
                }
            };

            // Terminal Output
            ConsoleHandler console = new ConsoleHandler();
            console.setFormatter(formatter);
            LOGGER.addHandler(console);

            // File Output: .minecraft/logs/latest.log
            File logFile = new File(OriginDirectories.LOGS_DIR, "latest.log");
            FileHandler fileHandler = new FileHandler(logFile.getPath(), false);
            fileHandler.setFormatter(formatter);
            LOGGER.addHandler(fileHandler);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
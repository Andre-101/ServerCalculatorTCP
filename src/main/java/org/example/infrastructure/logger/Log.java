package org.example.infrastructure.logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.*;

public class Log {
    private static final Logger LOGGER = Logger.getLogger("ServerLogger");

    static {
        try {
            Formatter formatter = new SimpleFormatter();

            ConsoleHandler consoleHandler = new ConsoleHandler();
            consoleHandler.setLevel(Level.INFO);
            consoleHandler.setFormatter(formatter);

            FileHandler fileHandler = new FileHandler("src/main/java/org/example/logs/server.log", 1024 * 1024, 5, true);
            fileHandler.setLevel(Level.ALL);
            fileHandler.setFormatter(formatter);

            LOGGER.setUseParentHandlers(false);
            LOGGER.addHandler(consoleHandler);
            LOGGER.addHandler(fileHandler);
            LOGGER.setLevel(Level.ALL);

        } catch (IOException e) {
            System.err.println("Error configuring the logger: " + e.getMessage());
        }
    }


    public static void info(String msg) {
        LOGGER.info(msg);
    }

    public static void warn(String msg) {
        LOGGER.warning(msg);
    }

    public static void error(String msg, Throwable e) {
        LOGGER.severe(msg + " - " + e.getMessage());
    }
}

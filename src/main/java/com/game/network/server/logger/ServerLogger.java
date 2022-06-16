package com.game.network.server.logger;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class ServerLogger implements System.Logger {
    // Reset
    public static final String RESET = "\033[0m";  // Text Reset

    // Regular Colors
    public static final String BLACK = "\033[0;30m";   // BLACK
    public static final String RED = "\033[0;31m";     // RED
    public static final String GREEN = "\033[0;32m";   // GREEN
    public static final String YELLOW = "\033[0;33m";  // YELLOW
    public static final String BLUE = "\033[0;34m";    // BLUE
    public static final String PURPLE = "\033[0;35m";  // PURPLE
    public static final String CYAN = "\033[0;36m";    // CYAN
    public static final String WHITE = "\033[0;37m";   // WHITE

    // Bold
    public static final String BLACK_BOLD = "\033[1;30m";  // BLACK
    public static final String RED_BOLD = "\033[1;31m";    // RED
    public static final String GREEN_BOLD = "\033[1;32m";  // GREEN
    public static final String YELLOW_BOLD = "\033[1;33m"; // YELLOW
    public static final String BLUE_BOLD = "\033[1;34m";   // BLUE
    public static final String PURPLE_BOLD = "\033[1;35m"; // PURPLE
    public static final String CYAN_BOLD = "\033[1;36m";   // CYAN
    public static final String WHITE_BOLD = "\033[1;37m";  // WHITE

    // Underline
    public static final String BLACK_UNDERLINED = "\033[4;30m";  // BLACK
    public static final String RED_UNDERLINED = "\033[4;31m";    // RED
    public static final String GREEN_UNDERLINED = "\033[4;32m";  // GREEN
    public static final String YELLOW_UNDERLINED = "\033[4;33m"; // YELLOW
    public static final String BLUE_UNDERLINED = "\033[4;34m";   // BLUE
    public static final String PURPLE_UNDERLINED = "\033[4;35m"; // PURPLE
    public static final String CYAN_UNDERLINED = "\033[4;36m";   // CYAN
    public static final String WHITE_UNDERLINED = "\033[4;37m";  // WHITE

    private static ServerLogger logger;

    public static ServerLogger getLogger() {
        if (logger == null)
            logger = new ServerLogger();
        return logger;
    }

    private ServerLogger() {}

    @Override
    public String getName() {
        return "Server logger";
    }

    @Override
    public boolean isLoggable(Level level) {
        return true;
    }

    private String colorize(Level level, String log) {
        switch (level) {
            case ALL:
                break;
            case TRACE:
                log = RED_BOLD + log + RESET;
                break;
            case DEBUG:
                break;
            case INFO:
                log = BLUE_BOLD + log + RESET;
                break;
            case WARNING:
                log = YELLOW_BOLD + log + RESET;
                break;
            case ERROR:
                log = RED_BOLD + log + RESET;
                break;
            case OFF:
                log = "";
                break;
        }
        return log;
    }

    @Override
    public void log(Level level, ResourceBundle resourceBundle, String s, Throwable throwable) {
        String message = s; // TODO

        String log = colorize(level, message);

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        System.out.println("[" + dtf.format(now) + "]" + log);
    }

    @Override
    public void log(Level level, ResourceBundle resourceBundle, String s, Object... objects) {
        String message = s; // TODO

        String log = colorize(level, message);

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        System.out.println("[" + dtf.format(now) + "]" + log);
    }
}

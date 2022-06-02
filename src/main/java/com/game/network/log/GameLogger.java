package com.game.network;

import java.util.ResourceBundle;
import java.util.function.Supplier;

public class GameLogger implements System.Logger {
    //TODO proper display
    @Override
    public String getName() {
        return "Valhalla game logger";
    }

    @Override
    public boolean isLoggable(Level level) {
        return false;
    }

    @Override
    public void log(Level level, String msg) {
        System.out.println(msg);
    }

    @Override
    public void log(Level level, Supplier<String> msgSupplier) {
        System.out.println(msgSupplier.get().toString());
    }

    @Override
    public void log(Level level, Object obj) {
        System.out.println(obj);
    }

    @Override
    public void log(Level level, String msg, Throwable thrown) {
        System.out.println(msg);
        System.out.println(thrown.getMessage());
        System.out.println(thrown.getStackTrace());
    }

    @Override
    public void log(Level level, Supplier<String> msgSupplier, Throwable thrown) {
        System.Logger.super.log(level, msgSupplier, thrown);
    }

    @Override
    public void log(Level level, String format, Object... params) {
        System.Logger.super.log(level, format, params);
    }

    @Override
    public void log(Level level, ResourceBundle resourceBundle, String s, Throwable throwable) {
        // TODO
    }

    @Override
    public void log(Level level, ResourceBundle resourceBundle, String s, Object... objects) {
        // TODO
    }
}

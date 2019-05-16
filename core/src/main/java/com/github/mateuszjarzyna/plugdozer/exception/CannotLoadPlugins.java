package com.github.mateuszjarzyna.plugdozer.exception;

public class CannotLoadPlugins extends PlugDozerException {

    public CannotLoadPlugins(String msg) {
        super(msg);
    }

    public CannotLoadPlugins(String msg, Throwable throwable) {
        super(msg, throwable);
    }

}

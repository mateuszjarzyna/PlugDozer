package com.github.mateuszjarzyna.plugdozer.exception;

public class CannotResolveDependency extends PlugDozerException {

    public CannotResolveDependency(String msg) {
        super(msg);
    }

    public CannotResolveDependency(String msg, Throwable throwable) {
        super(msg, throwable);
    }

}

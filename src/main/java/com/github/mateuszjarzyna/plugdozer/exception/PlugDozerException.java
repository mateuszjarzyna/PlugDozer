package com.github.mateuszjarzyna.plugdozer.exception;

public class PlugDozerException extends RuntimeException {

    public PlugDozerException(String msg) {
        super(msg);
    }

    public PlugDozerException(String msg, Throwable throwable) {
        super(msg, throwable);
    }

}

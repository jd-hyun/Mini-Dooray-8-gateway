package com.nhnacademy.minidooray.exception;

public class FailedFromRestServer extends RuntimeException {
    public FailedFromRestServer(String message) {
        super(message);
    }
}

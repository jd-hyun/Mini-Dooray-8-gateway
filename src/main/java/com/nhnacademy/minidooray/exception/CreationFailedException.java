package com.nhnacademy.minidooray.exception;

public class CreationFailedException extends FailedFromRestServer{
    public CreationFailedException(String message) {
        super(message);
    }
}

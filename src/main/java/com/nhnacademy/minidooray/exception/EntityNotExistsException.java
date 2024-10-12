package com.nhnacademy.minidooray.exception;

public class EntityNotExistsException extends FailedFromRestServer{
    public EntityNotExistsException(String message) {
        super(message);
    }
}

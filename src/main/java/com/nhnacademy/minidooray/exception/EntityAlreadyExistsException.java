package com.nhnacademy.minidooray.exception;

public class EntityAlreadyExistsException extends FailedFromRestServer{
    public EntityAlreadyExistsException(String message) {
        super(message);
    }
}

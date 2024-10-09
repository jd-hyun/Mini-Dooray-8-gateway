package com.nhnacademy.minidooray.exception;

import lombok.Getter;

@Getter
public class RedirectRuntimeException extends RuntimeException {
    private final String redirect;
    public RedirectRuntimeException(Throwable throwable, String redirectTo) {
        super(throwable);
        redirect = redirectTo;
    }
}

package com.nhnacademy.minidooray.annotation;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(RedirectOnExceptionContainer.class)
public @interface RedirectOnException {
    Class<? extends Exception>[] exceptions() default {Exception.class};
    String value();
}

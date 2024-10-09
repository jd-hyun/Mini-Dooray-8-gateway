package com.nhnacademy.minidooray.controller;

import com.nhnacademy.minidooray.exception.RedirectRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@Slf4j(topic = "ExceptionControllerAdvice")
@ControllerAdvice
public class ExceptionControllerAdvice {
    @ExceptionHandler({RedirectRuntimeException.class})
    public ModelAndView redirectExceptionHandler(RedirectRuntimeException e) {
        ModelAndView mv = new ModelAndView("error/alertMessageRedirect");
        mv.addObject("exception", e.getCause().getMessage());
        mv.addObject("redirect", e.getRedirect());
        return mv;
    }

    @ExceptionHandler({NoResourceFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void notFound() {
    }

    @ExceptionHandler({NullPointerException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public void nullPointer(NullPointerException e) {
        log.error("NullPointerException thrown: ", e);
    }
}

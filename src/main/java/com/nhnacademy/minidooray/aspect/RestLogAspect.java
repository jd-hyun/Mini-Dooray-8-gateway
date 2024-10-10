//package com.nhnacademy.minidooray.aspect;
//
//import lombok.extern.slf4j.Slf4j;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Component;
//
//@Aspect
//@Slf4j
//@Component
//public class RestLogAspect {
//    @Around("execution(* com.nhnacademy.minidooray.util.RestUtil.doRest(url, ..)) && args(url)")
//    public Object logAroundRestRequest(ProceedingJoinPoint joinPoint, String url) throws Throwable{
//        log.trace("Send request to {}...", url);
//        ResponseEntity value = (ResponseEntity) joinPoint.proceed();
//        log.trace("Received response from {} with status code {}", url, value.getStatusCode());
//        return value;
//    }
//}

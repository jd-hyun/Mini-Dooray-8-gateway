package com.nhnacademy.minidooray.aspect;

import com.nhnacademy.minidooray.annotation.RedirectOnException;
import com.nhnacademy.minidooray.exception.RedirectRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Slf4j
@Component
public class RedirectAspect {
    @Pointcut("@annotation(com.nhnacademy.minidooray.annotation.RedirectOnException)")
    public void redirectCut(){}

    // 다중 RedirectOnException 적용했을 경우, 실제로 RedirectOnExceptionContainer 가 붙어있는 것으로 취급함.
    @Pointcut("@annotation(com.nhnacademy.minidooray.annotation.RedirectOnExceptionContainer)")
    public void redirectContainerCut(){}

    // RedirectOnException 어노테이션을 읽어 예외가 발생했을때 해당 경로로 메세지와 함께 throw 하여
    // ExceptionControllerAdvice 에서 캐치하게 해줌.
    @Around("redirectCut() || redirectContainerCut()")
    public Object redirectThrowable(ProceedingJoinPoint joinPoint) throws Throwable {
        Object value;
        try {
            value = joinPoint.proceed();
        }catch (Exception e) {
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Method method = signature.getMethod();
            // @RedirectOnException 을 여러개 붙일수 있음!
            RedirectOnException[] r = method.getAnnotationsByType(RedirectOnException.class);
            if (r.length == 0) {
                throw new IllegalArgumentException();
            }
            for (RedirectOnException redirect: r) {
                Class<? extends Exception>[] target = redirect.exceptions();
                // annotation 에서 설정한 class 의 하위 클래스이면 RedirectRuntimeException 으로 재포장해서 throw.
                for (Class<? extends Exception> clazz: target) {
                    if (clazz.isAssignableFrom(e.getClass())) {
                        throw new RedirectRuntimeException(e, redirect.value());
                    }
                }
            }
            // 아니면 그냥 throw.
            throw e;
        }
        return value;
    }
}

package com.burakkocak.casestudies.exchangeservice.aspect;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingRestControllersAspect {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Pointcut(value = "execution(* com.burakkocak.casestudies.exchangeservice.controller.ExchangeController.exchangeRate(..))")
    public void controllersPointcut() {

    }

    @Around("controllersPointcut()")
    public Object controllersLogger(ProceedingJoinPoint point) throws Throwable {
        String methodName = point.getSignature().getName();
        String className = point.getTarget().getClass().getName();

        Object[] args = point.getArgs();

        log.info("Incoming message to {}() at {} : {}", methodName, className, MAPPER.writeValueAsString(args));

        Object o = point.proceed();

        log.info("Outgoing message from {}() at {} : {}", methodName, className, MAPPER.writeValueAsString(o));

        return o;
    }

}

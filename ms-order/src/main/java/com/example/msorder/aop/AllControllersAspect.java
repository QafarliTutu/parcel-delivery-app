package com.example.msorder.aop;

import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class AllControllersAspect {

    @Before(value = "execution(* com.example.msorder.controller.v1.*.*(..))")
    public void beforeAllMethodsInAllControllers(JoinPoint joinPoint) {
        log.info("METHOD LOCATION {}", joinPoint.getSignature());
        log.info("METHOD PARAMETERS {}", Arrays.toString(joinPoint.getArgs()));
    }

    @AfterReturning(value = "execution(* com.example.msorder.controller.v1.*.*(..))", returning = "result")
    public void afterAllMethodsInAllControllers(Object result) {
        log.info("RESULT IS {}", result);
    }

}

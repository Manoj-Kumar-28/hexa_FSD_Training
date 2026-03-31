package com.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Aspect
@Component
public class LogAspect {

    @Before("execution(* com.repository.*.*(..))")
    public void logBefore(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        System.out.println("Start time:- " + LocalTime.now());
        System.out.println(">>> Repository execution started " + methodName);

    }

    //@After("execution(* com.service.*.*(..))")
//    public void logAfter() {
//        System.out.println("Service method completed");
//    }
    @After("execution(* com.repository.*.*(..))")
    public void logAfter(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        System.out.println("Start time:- " + LocalTime.now());
        System.out.println(">>> Repository execution completed " + methodName);

    }
}

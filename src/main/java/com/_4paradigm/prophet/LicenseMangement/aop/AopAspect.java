package com._4paradigm.prophet.LicenseMangement.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class AopAspect {
    @Pointcut(value = "execution(* com._4paradigm.prophet.LicenseMangement.aop.aopController..*.*(..))")
    public void aop(){}


    @Before("aop()")
    public void doBefore(JoinPoint joinPoint){
        System.out.println("进入before");
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        System.out.println(request.getRequestURI());

    }
    @AfterReturning(returning = "ret",pointcut = "aop()")
    public void doAfterReturning(Object ret) throws Throwable {
        System.out.println("RESPONSE : " + ret);
    }

    @AfterThrowing(pointcut = "aop()",throwing = "exception")
    public void doAfterThrowingAdvice(JoinPoint joinPoint,Throwable exception){
        System.out.println("进入AfterThrowing");
        //目标方法名：
        System.out.println(joinPoint.getSignature().getName());

    }



}
package com.zc.security.web.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * Created by Administrator on 2017/9/21 0021.
 */
@Aspect
@Component
public class MyAspect {

    @Around("execution(* com.zc.security.web.controller.UserController.*(..))")
    public Object handlerControllerMethod(ProceedingJoinPoint pjp) throws Throwable {

        System.out.println("my aspect start!");

        Object obj = pjp.proceed();

        Object[] args= pjp.getArgs();


        Arrays.asList(args).forEach(arg -> {

            System.out.println(arg.toString());

        });

        System.out.println("my aspect end!");

        return obj;
    }

}

package com.pace2car.springbootdemo.handler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author Pace2Car
 * @date 2019/1/17 15:27
 */
@Component
@Aspect
public class PermissionVerify {

    private Logger logger = LogManager.getLogger("permissionVerify");

    @Pointcut("@annotation(com.pace2car.springbootdemo.shiro.anno.PermissionName)")
    public void needVerifyMethod() {
    }

    @Around("needVerifyMethod()")
    public Object verify(ProceedingJoinPoint joinPoint) throws Throwable {
        logger.info("验证权限AOP");
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            logger.info(arg);
        }
        return joinPoint.proceed();
    }
}

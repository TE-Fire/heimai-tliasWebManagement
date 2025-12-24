package com.example.demo.aop;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
// import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
// @Aspect 
@Slf4j
public class RecordTimeAspect {

    // 修改切点表达式，添加execution关键字
    @Around("execution(* com.example.demo.service.Impl.*.*(..))")
    public Object recordTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long begin = System.currentTimeMillis();

        //执行原始方法
        Object result = joinPoint.proceed();

        long end = System.currentTimeMillis(); 

        log.info("{}方法耗时: {}毫秒", joinPoint.getSignature(), end-begin);
        return result;
    }

    @Before("execution(* com.example.demo.service.*.*(..))")
    public void before(JoinPoint point) {
        log.info("获取目标对象: {}", point.getTarget());
        log.info("获取目标类: {}", point.getTarget().getClass().getName());
        log.info("获取目标方法: {}", point.getSignature().getName());
        Object[] args = point.getArgs();
        log.info("获取目标方法参数: {}", Arrays.toString(args));
    }
}
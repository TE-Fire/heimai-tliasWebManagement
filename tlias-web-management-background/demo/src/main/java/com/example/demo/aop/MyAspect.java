package com.example.demo.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
// import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
// @Aspect
public class MyAspect {
    //切入点方法(将公共切入点表达式抽取出来)
    @Pointcut("execution(* com.example.demo.service.Impl.*.*(..))") // 切入点表达式
    private void pt() {}

    //前置通知(引用切入点)
    @Before("pt()")  // 前置通知,在目标方法执行之前执行
    public void before(JoinPoint point) {
        log.info("before ...");
    }

    //环绕通知
    @Around("pt()") // 环绕通知,在目标方法执行前后都执行,发生异常后的代码不会执行
    public Object around(ProceedingJoinPoint point) throws Throwable {
        log.info("around before ...");

        Object result = point.proceed();

        log.info("around after ...");

        return result;
    }

    //后置通知
    @After("pt()") // 后置通知,在目标方法执行之后执行,无论是否发生异常
    public void after(JoinPoint point) {
        log.info("after ...");
    }

    //返回后通知（程序在正常执行的情况下，会执行的后置通知）
    @AfterReturning("pt()") // 返回后通知,在目标方法正常执行完毕后执行
    public void afterReturning(JoinPoint point) {

    }

    //异常通知（程序在出现异常的情况下，执行的后置通知）
    @AfterThrowing("pt()") // 异常通知,在目标方法抛出异常后执行
    public void afterThrowing() {
        log.info("after throwing ...");
    }
}

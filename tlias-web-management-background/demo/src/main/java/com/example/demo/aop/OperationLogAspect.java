package com.example.demo.aop;

import java.time.LocalDateTime;
import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.mapper.OperateLogMapper;
import com.example.demo.model.OperateLog;
import com.example.demo.utils.CurrentHolder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
public class OperationLogAspect {

    //构造器注入
    @Autowired
    private OperateLogMapper operateLogMapper;

    // public OperationLogAspect(OperateLogMapper operateLogMapper) {
    //     this.operateLogMapper = operateLogMapper;
    // }

    // 定义切点
    @Pointcut("@annotation(com.example.demo.annotation.LogOperation)")
    public void logOperationPointcut() {}

    private Integer getCurrentUserId() {
        return CurrentHolder.getCurrentId();
    }

    @Around("logOperationPointcut()")
    public Object logOperation(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = null;
        try {
            result = joinPoint.proceed();
            return result;
        } finally {
            try {
                // 在finally块中记录日志，确保即使方法执行失败也能记录
                long endTime = System.currentTimeMillis();
                long costTime = endTime - startTime;
                
                OperateLog operateLog = new OperateLog();
                operateLog.setOperateEmpId(getCurrentUserId());
                operateLog.setOperateTime(LocalDateTime.now());
                operateLog.setClassName(joinPoint.getTarget().getClass().getName());
                operateLog.setMethodName(joinPoint.getSignature().getName());
                operateLog.setMethodParams(Arrays.toString(joinPoint.getArgs()));
                
                // 安全处理result
                operateLog.setReturnValue(result != null ? result.toString() : "null");
                
                operateLog.setCostTime(costTime);
            
                log.info("操作日志记录: {}", operateLog);
                operateLogMapper.insert(operateLog);
            } catch (Exception e) {
                // 记录日志记录过程中的异常，但不影响原方法的返回
                log.error("记录操作日志失败", e);
            }
        }
    }
    
    

}
package com.example.demo.exception;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.demo.model.Result;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler
    public Result handleException(Exception e) {
        log.info("程序出错啦", e);
        return Result.error("出错了，请联系管理员");
    }

    @ExceptionHandler
    public Result handleDuplicateKeyException(DuplicateKeyException e) {
        log.info("程序出错啦", e);
        String message = e.getMessage();
        int i = message.indexOf("Duplicate entry");
        String errMsg = message.substring(i);
        String[] arr = errMsg.split(" ");
        return Result.error(arr[2] + "已存在");
    }

      // 添加处理RuntimeException的方法，将具体错误信息返回给前端
    @ExceptionHandler
    public Result handleRuntimeException(RuntimeException e) {
        log.info("业务异常: {}", e.getMessage(), e);
        // 直接返回RuntimeException中的错误信息
        return Result.error(e.getMessage());
    }

}

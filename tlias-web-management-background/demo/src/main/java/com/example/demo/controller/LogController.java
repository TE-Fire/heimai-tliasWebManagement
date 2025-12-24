package com.example.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.OperateLog;
import com.example.demo.model.PageResult;
import com.example.demo.model.Result;
import com.example.demo.service.LoggerService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class LogController {
    @Autowired
    private LoggerService loggerService;

    @GetMapping("/log/page")
    public Result page(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10")Integer pageSize) {
        log.info("分页查询");
        PageResult<OperateLog> pageResult = loggerService.list(page, pageSize);
        if (pageResult != null) {
            return Result.success(pageResult);
        } else {
            return Result.error("查询失败");
        }
    }
}

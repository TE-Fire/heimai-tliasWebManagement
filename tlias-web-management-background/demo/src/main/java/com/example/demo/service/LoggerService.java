package com.example.demo.service;


import com.example.demo.model.OperateLog;
import com.example.demo.model.PageResult;

public interface LoggerService {

    public PageResult<OperateLog> list(Integer page, Integer pageSize);
    
}

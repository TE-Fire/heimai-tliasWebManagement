package com.example.demo.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.mapper.EmpMapper;
import com.example.demo.mapper.LoggerMapper;
import com.example.demo.model.OperateLog;
import com.example.demo.model.PageResult;
import com.example.demo.service.LoggerService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

@Service
public class LoggerServiceImpl implements LoggerService{

    @Autowired
    private LoggerMapper loggerMapper;

    @Autowired
    private EmpMapper empMapper;

    @Override
    public PageResult<OperateLog> list(Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        List<OperateLog> operateLogList = loggerMapper.pageList();
        for (OperateLog operateLog : operateLogList) {
            Integer empId = operateLog.getId();
            String empName = empMapper.getUserNameById(empId);
            operateLog.setOperateEmpName(empName);
        }
        try (Page<OperateLog> p = (Page<OperateLog>) operateLogList) {
            return new PageResult<OperateLog>(p.getTotal(), p.getResult());
        }
    }
}

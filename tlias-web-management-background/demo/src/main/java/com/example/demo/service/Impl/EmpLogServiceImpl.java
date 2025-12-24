package com.example.demo.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.mapper.EmpLogMapper;
import com.example.demo.model.EmpLog;
import com.example.demo.service.EmpLogService;

@Service
public class EmpLogServiceImpl implements EmpLogService{

    @Autowired
    private EmpLogMapper empLogMapper;

    @Transactional(propagation = Propagation.REQUIRES_NEW) //propagation属性: 表示事务的传播行为, REQUIRES_NEW表示每次调用都创建一个新的事务, 并在新事务中执行，默认值为Propagation.REQUIRED，加入已存在事务
    @Override
    public void insert(EmpLog empLog) {
        empLogMapper.insert(empLog);
    }
}

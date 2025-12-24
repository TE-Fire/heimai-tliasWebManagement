package com.example.demo.service;

import java.util.List;

// import java.time.LocalDate;

import com.example.demo.model.Emp;
import com.example.demo.model.EmpQueryParam;
import com.example.demo.model.LoginInfo;
import com.example.demo.model.PageResult;

public interface EmpService {

    PageResult<Emp> page(EmpQueryParam param);

    public int insert(Emp emp); 

    public boolean deleteByIds(List<Integer> ids);

    Emp getInfo(Integer id);

    void update(Emp emp);

    List<Emp> getAllEmpInfo();

    LoginInfo login(Emp emp);

}

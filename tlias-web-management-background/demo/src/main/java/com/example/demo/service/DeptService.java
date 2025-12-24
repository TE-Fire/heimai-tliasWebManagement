package com.example.demo.service;

import java.util.List;

import com.example.demo.model.Dept;

public interface DeptService {

    public List<Dept> findAll();

    public int deleteById(Integer id);

    public int addByDept(Dept dept);

    public Dept getById(Integer deptId);

    public int update(Dept dept);

}

package com.example.demo.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import com.example.demo.model.EmpLog;

@Mapper
public interface EmpLogMapper {
    //language=SQL
    @Insert("insert into emp_log(operate_time, info) values(#{operateTime}, #{info})")
    public void insert(EmpLog empLog);
}

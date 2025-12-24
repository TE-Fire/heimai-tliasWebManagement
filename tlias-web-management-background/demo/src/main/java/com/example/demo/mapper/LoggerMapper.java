package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.example.demo.model.OperateLog;

@Mapper
public interface LoggerMapper {

    //language=SQL
    @Select("select * from operate_log order by operate_time desc")
    List<OperateLog> pageList();

}

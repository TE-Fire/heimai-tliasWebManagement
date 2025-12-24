package com.example.demo.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.example.demo.model.Clazz;
import com.example.demo.model.ClazzOption;
import com.example.demo.model.ClazzQueryParam;

@Mapper
public interface ClazzMapper {

    public List<Clazz> list(ClazzQueryParam param);

    //language=SQL
    @Delete("delete from clazz where id=#{id}")
    public int deleteById(Integer id);

    public int insert(ClazzOption clazzOption);

    //language=SQL
    @Select("select * from clazz where id=#{id}")
    public Clazz findById(Integer id);

    public int updateClazzInfo(ClazzOption clazzOption);
    
    //language=SQL
    @Select("select * from clazz")
    public List<Clazz> findAll();

    public List<Map<String, Object>> countClazzData();

}

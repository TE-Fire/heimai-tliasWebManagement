package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.demo.model.Dept;

@Mapper
public interface DeptMapper {

    //查询所有部门信息
    //language=SQL
    @Select("select id, name, create_time, update_time from dept ORDER BY update_time DESC")
    public List<Dept> findAll();

    //根据ID删除部门
    //language=SQL
    @Delete("delete from dept where id = #{id}")
    public int deleteById(Integer id);
    
    //新增部门
    //language=SQL
    @Insert("insert into dept(name, create_time, update_time) values(#{name}, #{createTime}, #{updateTime})")
    public int insert(Dept dept);

    //language=SQL
    @Select("select id, name, create_time, update_time from dept where id = #{id}")
    public Dept getById(Integer id);

    //language=SQL
    @Update("update dept set name=#{name}, update_time=#{updateTime} where id=#{id}")
    public int update(Dept dept);

    


}

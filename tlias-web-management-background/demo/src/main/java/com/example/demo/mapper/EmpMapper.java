package com.example.demo.mapper;

// import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
// import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import com.example.demo.model.Emp;
import com.example.demo.model.EmpQueryParam;

@Mapper
public interface EmpMapper {

    // -----------------原始分页查询实现
    // // 查询总记录数
    // //language=SQL
    // @Select("select count(*) from emp e left join dept d on e.dept_id=d.id")
    // public Long count();
    
    // // 分页查询
    // //language=SQL
    // @Select("select e.*, d.name as deptName from emp e left join dept d on e.dept_id=d.id order by e.update_time desc limit #{start}, #{pageSize}")
    // public List<Emp> list(Integer start, Integer pageSize);

    //@Select("select e.*, d.name as deptName from emp e left join dept d on e.dept_id=d.id order by e.update_time desc ")
        // where e.name like concat('%', #{name}, '%') 在处理占位符如果不进行字符串拼接，被mybits处理为‘？’

    public List<Emp> list(EmpQueryParam param);
    //插入员工信息
    //language=SQL
    @Options(useGeneratedKeys = true, keyProperty = "id") // 开启主键返回，将返回的主键值设置到emp对象的id属性中
    @Insert("insert into emp(username, name, gender, phone, job, salary, image, entry_date, dept_id, create_time, update_time) values (#{username},#{name},#{gender},#{phone},#{job},#{salary},#{image},#{entryDate},#{deptId},#{createTime},#{updateTime})")
    public int insert(Emp emp);
    public void deleteByIds(List<Integer> ids);
    public Emp getById(Integer id);
    public void updateById(Emp emp);

    public List<Map<String, Object>> countEmpJobData();
    public List<Map<Object, Object>> countEmpGenderData();
    //language=SQL
    @Select("select * from emp")
    public List<Emp> getAllEmpInfo();

    //language=SQL
    @Select("select id, username, name from emp where username=#{username} and password=#{password} ")
    public Emp getUsernameAndPassword(Emp emp);

    //language=SQL
    @Select("select name from emp where id=#{empId}")
    public String getUserNameById(Integer empId);

    
    
} 
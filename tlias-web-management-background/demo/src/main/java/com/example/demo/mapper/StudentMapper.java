package com.example.demo.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.demo.model.Student;
import com.example.demo.model.StudentOption;
import com.example.demo.model.StudentQueryParam;

@Mapper
public interface StudentMapper {
    //language=SQL
    @Select("select count(*) from student where clazz_id=#{clazz_id}")
    public int countByClazzId(int clazz_id);

    public List<Student> list(StudentQueryParam param);

    public int insertToStudentInfo(StudentOption studentOption);

    //language=SQL
    @Select("select * from student where id=#{id}")
    public Student findInfoById(Integer id);

    public int updateById(StudentOption studentOption);

    public int deleteById(List<Integer> ids);

    //language=SQL
    @Select("select violation_score from student where id=#{id}")
    public Integer getOriginalScore(Integer id);

    //language=SQL
    @Update("update student set violation_score=#{originalScore} where id=#{id}")
    public int addViolatioScore(Integer id, Integer originalScore);

    //language=SQL
    @Select("select violation_count from student where id=#{id}")
    public Integer getViolationCount(Integer id);

    //language=SQL
    @Update("update student set violation_count=#{violationCount} where id=#{id}")
    public void addViolationCount(Integer id, Integer violationCount);

    public List<Map<Object, Object>> countStuDegreeData();
}

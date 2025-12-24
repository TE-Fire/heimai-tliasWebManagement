package com.example.demo.service;

import java.util.List;

import com.example.demo.model.PageResult;
import com.example.demo.model.Student;
import com.example.demo.model.StudentOption;
import com.example.demo.model.StudentQueryParam;

public interface StudentService {

    PageResult<Student> page(StudentQueryParam param);

    int insertToStudentInfo(StudentOption studentOption);

    Student findInfoById(Integer id);

    int updateById(StudentOption studentOption);

    int deleteById(List<Integer> ids);

    int addViolatioScore(Integer id, Integer score);

}

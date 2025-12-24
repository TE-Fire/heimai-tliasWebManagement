package com.example.demo.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.mapper.StudentMapper;
import com.example.demo.model.PageResult;
import com.example.demo.model.Student;
import com.example.demo.model.StudentOption;
import com.example.demo.model.StudentQueryParam;
import com.example.demo.service.StudentService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

@Service
public class StudentServiceImpl implements StudentService{


    @Autowired
    private StudentMapper studentMapper;

    

    @Override
    public PageResult<Student> page(StudentQueryParam param) {
        PageHelper.startPage(param.getPage(), param.getPageSize());
        List<Student> studentList = studentMapper.list(param);
        try (Page<Student> page = (Page<Student>) studentList) {
            return new PageResult<Student>(page.getTotal(), page.getResult());
        }
    }

    @Override
    public int insertToStudentInfo(StudentOption studentOption) {
        return studentMapper.insertToStudentInfo(studentOption);
    }

    @Override
    public Student findInfoById(Integer id) {
        return studentMapper.findInfoById(id);
    }

    @Override
    public int updateById(StudentOption studentOption) {
        return studentMapper.updateById(studentOption);
    }

    @Override
    public int deleteById(List<Integer> ids) {
        return studentMapper.deleteById(ids);
    }

    @Transactional
    @Override
    public int addViolatioScore(Integer id, Integer score) {
        Integer addViolationScore = studentMapper.getOriginalScore(id) + score;
        Integer violationCount = studentMapper.getViolationCount(id);
        violationCount += 1;
        studentMapper.addViolationCount(id, violationCount);
        return studentMapper.addViolatioScore(id, addViolationScore);
    }
}

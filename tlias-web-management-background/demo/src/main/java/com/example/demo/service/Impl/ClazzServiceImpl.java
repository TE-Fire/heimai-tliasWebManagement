package com.example.demo.service.Impl;

import java.time.LocalDate;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.mapper.ClazzMapper;
import com.example.demo.mapper.StudentMapper;
import com.example.demo.model.Clazz;
import com.example.demo.model.ClazzOption;
import com.example.demo.model.ClazzQueryParam;
import com.example.demo.model.PageResult;
import com.example.demo.service.ClazzService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

@Service
public class ClazzServiceImpl implements ClazzService{

    @Autowired
    private ClazzMapper clazzMapper;
    @Autowired
    private StudentMapper studentMapper;

    @Override
    public PageResult<Clazz> page(ClazzQueryParam param) {        
        PageHelper.startPage(param.getPage(), param.getPageSize());
        List<Clazz> clazzList = clazzMapper.list(param);

        LocalDate currDate = LocalDate.now();
        for (Clazz clazz : clazzList) {
            if (currDate.isAfter(clazz.getEndDate())) {
                clazz.setStatus("已结课");
            } else if (currDate.isBefore(clazz.getBeginDate())) {
                clazz.setStatus("未开班");
            } else {
                clazz.setStatus("在读中");
            }
        }
        try (Page<Clazz> p = (Page<Clazz>) clazzList) {
            return new PageResult<Clazz>(p.getTotal(), p.getResult());
        }
        
    }

    @Override
    public int deleteById(Integer id) {
        int count = studentMapper.countByClazzId(id);
        if (count > 0) {
            throw new RuntimeException("该班级在有学生，不能直接删除");
        }

        return clazzMapper.deleteById(id);
    }

    @Override
    public int insertInfoToClazz(ClazzOption clazzOption) {
        return clazzMapper.insert(clazzOption);
    }

    @Override
    public Clazz findById(Integer id) {
        return clazzMapper.findById(id);
    }

    @Override
    public int updateClazzInfo(ClazzOption clazzOption) {
        return clazzMapper.updateClazzInfo(clazzOption);
    }

    @Override
    public List<Clazz> findAll() {
        return clazzMapper.findAll();
    }
}

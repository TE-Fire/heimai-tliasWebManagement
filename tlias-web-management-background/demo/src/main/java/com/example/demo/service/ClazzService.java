package com.example.demo.service;

import java.util.List;

import com.example.demo.model.Clazz;
import com.example.demo.model.ClazzOption;
import com.example.demo.model.ClazzQueryParam;
import com.example.demo.model.PageResult;

public interface ClazzService {

    PageResult<Clazz> page(ClazzQueryParam param);

    int deleteById(Integer id);

    int insertInfoToClazz(ClazzOption clazzOption);

    Clazz findById(Integer id);

    int updateClazzInfo(ClazzOption clazzOption);

    List<Clazz> findAll();

}

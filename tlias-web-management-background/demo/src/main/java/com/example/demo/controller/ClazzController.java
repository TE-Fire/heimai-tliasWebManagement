package com.example.demo.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Clazz;
import com.example.demo.model.ClazzOption;
import com.example.demo.model.ClazzQueryParam;
import com.example.demo.model.PageResult;
import com.example.demo.model.Result;
import com.example.demo.service.ClazzService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/clazzs")
@RestController
public class ClazzController {
    
    @Autowired
    private ClazzService clazzService;

    //班级列表查询(/clazzs?name=java&begin=2023-01-01&end=2023-06-30&page=1&pageSize=5)
    @GetMapping
    public Result page(ClazzQueryParam param) {
        log.info("分页查询:{}", param);
        PageResult<Clazz> pageResult = clazzService.page(param);
        return Result.success(pageResult);
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        log.info("删除指定id班级信息{}: ", id);
        int effectRows = clazzService.deleteById(id);
        if (effectRows > 0) {
            return Result.success();
        } else {
            return Result.error("删除失败");
        }
    }

    @PostMapping
    public Result insert(@RequestBody ClazzOption clazzOption) {

        log.info("新增班级信息{}: ", clazzOption);
        int effectRows = clazzService.insertInfoToClazz(clazzOption);
        if (effectRows > 0) {
            return Result.success();
        } else {
            return Result.error("插入失败");
        }
    }

    @GetMapping("/{id}")
    public Result getClazzInfoById(@PathVariable Integer id) {
        Clazz clazz = clazzService.findById(id);
        log.info("根据{}查询班级信息为{}: ", id, clazz);
        if (clazz != null) {
            return Result.success(clazz);
        } else {
            return Result.error("回显数据失败");
        }
    }

    @PutMapping
    public Result update(@RequestBody ClazzOption clazzOption) {
        log.info("更新班级信息{}: ", clazzOption);
        int effectRows = clazzService.updateClazzInfo(clazzOption);

        if (effectRows > 0) {
            return Result.success();
        } else {
            return Result.error("更新班级信息失败");
        }
    }

    @GetMapping("/list")
    public Result getAllClazzInfo() {
        log.info("查询所有班级信息");
        List<Clazz> clazzList = clazzService.findAll();
        if (!CollectionUtils.isEmpty(clazzList)) {
            return Result.success(clazzList);
        } else {
            return Result.error("查询所有班级信息失败");
        }
    }
}

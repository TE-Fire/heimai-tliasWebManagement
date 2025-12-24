package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.annotation.LogOperation;
import com.example.demo.model.Emp;
import com.example.demo.model.EmpQueryParam;
import com.example.demo.model.PageResult;
import com.example.demo.model.Result;
import com.example.demo.service.EmpService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "/emps")
public class EmpController {

    @Autowired
    private EmpService empService;

    // 分页查询
    @GetMapping
    public Result page(EmpQueryParam param) {
        log.info("分页查询: {}", param);
        PageResult<Emp> pageResult = empService.page(param);
        return Result.success(pageResult);
    }

    //保存员工基本信息和工作经历
    @PostMapping
    public Result save(@RequestBody Emp emp) {
        log.info("接收员工保存请求: {}", emp);
        try {
            int effectRows = empService.insert(emp);
            if (effectRows > 0) {
                return Result.success();
            } else {
                log.warn("保存员工信息失败，无记录受影响: {}", emp);
                return Result.error("保存员工信息失败");
            }
        } catch (Exception e) {
            log.error("保存员工信息时发生异常", e);
            return Result.error("保存失败: " + e.getMessage());
        }
    }


    //删除员工基本信息及自身经历
    @LogOperation
    @DeleteMapping
    public Result delete(@RequestParam List<Integer> ids) {
        log.info("批量删除部门: ids={}", ids);
        boolean flag = empService.deleteByIds(ids);
        if (flag) {
            log.info("删除成功");
            return Result.success();
        } else {
            log.info("删除失败");
            return Result.error("删除失败");
        }
    }

    @LogOperation
    @GetMapping("/{id}")
    public Result getInfo(@PathVariable Integer id) {
        log.info("根据id查询员工的详细信息: " + id);
        Emp emp = empService.getInfo(id);
        return Result.success(emp);
    }

    @LogOperation
    @PutMapping
    public Result update(@RequestBody Emp emp) {
        log.info("修改员工信息, {}", emp);
        empService.update(emp);
        return Result.success();
    }

    @LogOperation
    @GetMapping("/list")
    public Result getAllEmpInfo() {
        log.info("查询员工全部信息");
        List<Emp> empList = empService.getAllEmpInfo();
        return Result.success(empList);
    }
}
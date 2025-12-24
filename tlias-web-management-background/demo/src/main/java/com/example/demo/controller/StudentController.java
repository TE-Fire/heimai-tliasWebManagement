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
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.PageResult;
import com.example.demo.model.Result;
import com.example.demo.model.Student;
import com.example.demo.model.StudentOption;
import com.example.demo.model.StudentQueryParam;
import com.example.demo.service.StudentService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/students")
@RestController
public class StudentController {

    
    @Autowired
    private StudentService studentService;

    

    @GetMapping
    public Result page(StudentQueryParam param) {
        log.info("分页条件查询{}: ", param);
        PageResult<Student> studenResult = studentService.page(param);
        if (studenResult != null) {
            return Result.success(studenResult);
        } else {
            return Result.error("分页条件查询失败");
        }
    }   

    @PostMapping
    public Result insert(@RequestBody StudentOption studentOption) {
        log.info("插入学员信息{}: ", studentOption);
        int effectRows = studentService.insertToStudentInfo(studentOption);
        if (effectRows > 0) {
            return Result.success();
        } else {
            return Result.error("插入学员信息失败");
        }
    }

    @GetMapping("/{id}")
    public Result getInfoById(@PathVariable Integer id) {
        Student student = studentService.findInfoById(id);
        if (student != null) {
            log.info("根据学员id回显学员数据{}: ", student);
            return Result.success(student);
        } else {
            return Result.error("回显数据失败");
        }
    }

    @PutMapping
    public Result update(@RequestBody StudentOption studentOption) {
        log.info("更新学员信息{}: ", studentOption);
        int effectRows = studentService.updateById(studentOption);
        if (effectRows > 0) {
            return Result.success();
        } else {
            return Result.error("更新失败");
        }
    }

    @DeleteMapping("/{ids}")
    public Result delete(@PathVariable List<Integer> ids) {
        log.info("删除学员信息id为{}: ", ids);
        int effectRows = studentService.deleteById(ids);
        if (effectRows > 0) {
            return Result.success();
        } else {
            return Result.error("删除失败");
        }
    }

    @PutMapping("/violation/{id}/{score}")
    public Result processViolation(@PathVariable Integer id, @PathVariable Integer score) {
        log.info("id为{}学员违纪扣分{}: ", id, score);
        if (score <= 0) {
            return Result.error("违纪扣分不能小于等于0");
        }
        int effectRows = studentService.addViolatioScore(id, score);
        if (effectRows > 0) {
            return Result.success();
        } else {
            return Result.error("扣分失败");
        }
    } 
}

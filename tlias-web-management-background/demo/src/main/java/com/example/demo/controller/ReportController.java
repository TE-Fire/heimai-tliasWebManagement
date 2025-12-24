package com.example.demo.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.ClazzCount;
import com.example.demo.model.JobOption;
import com.example.demo.model.Result;
import com.example.demo.service.ReportService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/report")
@RestController
public class ReportController {
    
    @Autowired
    private ReportService reportService;

    //统计各个职位的人数
    @GetMapping("/empJobData")
    public Result getEmpJobData() {
        log.info("统计各个职位的员工人数");
        JobOption jobOption = reportService.getEmpJobData();
        return Result.success(jobOption);
    }
    //统计男女员工人数
    @GetMapping("/empGenderData")
    public Result getGenderEmpData() {
        log.info("统计员工性别人数");
        List<Map<Object, Object>> empGenderData = reportService.getEmpGenderData();
        return Result.success(empGenderData);
    }

    @GetMapping("/studentCountData")
    public Result countClazzStuNumData() {
        log.info("统计班级人数信息");
        ClazzCount clazzCount = reportService.getClazzOfStudentNum();
        return Result.success(clazzCount);
    }

    @GetMapping("/studentDegreeData")
    public Result countDegreeData() {
        log.info("统计学历信息");
        List<Map<Object, Object>> studentDegreeData = reportService.countStuDegreeData();
        return Result.success(studentDegreeData);
    } 
}

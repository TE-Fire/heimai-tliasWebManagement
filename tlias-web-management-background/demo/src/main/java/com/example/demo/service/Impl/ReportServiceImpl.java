package com.example.demo.service.Impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.mapper.ClazzMapper;
import com.example.demo.mapper.EmpMapper;
import com.example.demo.mapper.StudentMapper;
import com.example.demo.model.ClazzCount;
import com.example.demo.model.JobOption;
import com.example.demo.service.ReportService;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private EmpMapper empMapper;
    
    @Autowired
    private ClazzMapper clazzMapper;

    @Autowired
    private StudentMapper studentMapper;

    @Override
    public JobOption getEmpJobData() {
        // 1.获取对应职位的员工数量
        List<Map<String, Object>> list = empMapper.countEmpJobData();
        log.info("获取到的职位员工数量数据: {}", list);
        // 2.对获取结果封装到JobOption对象中
        List<Object> jobList = list.stream().map(map -> map.get("pos")).toList();
        List<Object> numList = list.stream().map(map -> map.get("num")).toList();
        
        return new JobOption(jobList, numList);
    }

    @Override
    public List<Map<Object, Object>> getEmpGenderData() {
        List<Map<Object, Object>> empGenderData = empMapper.countEmpGenderData();
        log.info("员工信息{}: ", empGenderData);
        return empGenderData;
    }

    @Override
    public ClazzCount getClazzOfStudentNum() {
        List<Map<String, Object>> list = clazzMapper.countClazzData();
        List<Object> clazzList = list.stream().map(map -> map.get("clazzName")).toList();
        List<Object> dataList = list.stream().map(map -> map.get("studentNum")).toList();
        return new ClazzCount(clazzList, dataList);
    }

    @Override
    public List<Map<Object, Object>> countStuDegreeData() {
        return studentMapper.countStuDegreeData();
    }
}

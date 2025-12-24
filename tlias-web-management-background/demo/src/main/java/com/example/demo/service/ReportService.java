package com.example.demo.service;

import java.util.List;
import java.util.Map;

import com.example.demo.model.ClazzCount;
import com.example.demo.model.JobOption;

public interface ReportService {

    JobOption getEmpJobData();

    List<Map<Object, Object>> getEmpGenderData();

    ClazzCount getClazzOfStudentNum();

    List<Map<Object, Object>> countStuDegreeData();


}

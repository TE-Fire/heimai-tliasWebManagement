package com.example.demo.model;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClazzQueryParam {
    
    private Integer page = 1; //页码
    private Integer pageSize = 5; //每页记录数
    private String name; //班级名
    private LocalDate begin; //班级开课时间
    private LocalDate end; //班级结课时间

}

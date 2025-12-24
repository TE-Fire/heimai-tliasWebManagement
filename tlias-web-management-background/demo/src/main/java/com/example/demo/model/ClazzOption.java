package com.example.demo.model;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClazzOption {
    private Integer id;
    private String name; //班级名
    private String room; //教师号
    private LocalDate beginDate; //开课日期
    private LocalDate endDate; //结课日期
    private Integer masterId; //班主任id
    private Integer subject;
    
}

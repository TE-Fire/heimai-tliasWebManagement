package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentQueryParam {
    private String name; //学员姓名
    private Integer degree; //学位信息
    private Integer clazzId; //学员参与课程id
    private Integer page = 1; //页码
    private Integer pageSize = 5; //每页展示记录数
}

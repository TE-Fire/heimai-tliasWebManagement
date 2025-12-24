package com.example.demo.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentOption {
    private Integer id; //主键
    private String name; //学生姓名
    private String no; //学号
    private Integer gender; //性别
    private String phone; //手机号
    private Integer degree; //学历
    private Integer clazzId; //班级号
    private String idCard; //身份证号
    private Integer isCollege; //是否在读
    private String address; //联系地址
    private String graduationDate; //毕业时间
    private Integer violationCount; //违纪次数
    private Integer violationScore; //违纪扣分
}

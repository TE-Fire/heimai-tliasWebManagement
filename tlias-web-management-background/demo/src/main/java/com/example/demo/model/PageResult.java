package com.example.demo.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//封装分页查询的响应体
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageResult<T> {
    private Long total; //记录总数
    private List<T> rows; //当前页面数据列表
}

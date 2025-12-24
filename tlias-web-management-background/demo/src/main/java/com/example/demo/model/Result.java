package com.example.demo.model;

import lombok.Data;

@Data
public class Result {
    private Integer code; // 状态码
    private String msg; // 提示信息
    private Object data; // 数据

    public static Result success() {
        Result result = new Result();
        result.code = 1;
        result.msg = "success";
        return result;
    }

    public static Result success(Object data) {
        Result result = success();
        result.data = data;
        result.code = 1;
        result.msg = "success";
        return result;
    }
    public static Result error(String msg) {
        Result result = new Result();
        result.code = 0;
        result.msg = msg;
        return result;
    }
}

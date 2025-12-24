package com.example.demo.controller;

import java.util.List;

// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Dept;
import com.example.demo.model.Result;
import com.example.demo.service.DeptService;

import lombok.extern.slf4j.Slf4j;

@Slf4j // 基于slf4j日志框架的日志记录器
@RestController
@RequestMapping(value = "depts")
public class DeptController {

    // private static final Logger log = LoggerFactory.getLogger(DeptController.class);
    @Autowired
    private DeptService deptService;


    // @RequestMapping(value = "/depts", method = RequestMethod.GET)
    @GetMapping
    public Result list() {
        List<Dept> deptList = deptService.findAll();
        // System.out.println(deptList);
        log.info("查询所有部门信息: {}", deptList);
        return Result.success(deptList);
    }

    // 删除部门 方式一:通过HTttpServletRequest获取请求参数
    // @DeleteMapping(value = "/depts")
    // public Result delete(HttpServletRequest request) {
    //     String idStr = request.getParameter("id");
    //     int id = Integer.parseInt(idStr);
    //     System.out.println("根据ID删除部门: " + id);
    //     return Result.success();
    // }

    // 删除部门 方式二:通过@RequestParam获取请求参数
    //注意事项：
    // 1.@RequestParam注解可以获取请求参数，参数名必须与请求参数名一致
    // 2.@RequestParam注解可以设置required属性，默认值为true，即请求参数必须包含在请求中
    // 3.@RequestParam注解可以设置defaultValue属性，即请求参数不存在时的默认值
    // @DeleteMapping(value = "/depts")
    // public Result delete(@RequestParam(value = "id", required = false, defaultValue = "1") Integer id) {
    //     System.out.println("根据ID删除部门: " + id);
    //     return Result.success();
    // }

    //删除部门 方式三：省略@RequestParam注解
    // 注意事项：
    // 1.如果请求参数名与方法参数名一致，@RequestParam注解可以省略不写
    // 2.如果请求参数名与方法参数名不一致，@RequestParam注解必须写，且value属性值必须与请求参数名一致
    @DeleteMapping
    public Result deleteDept(Integer id) {

        int affectedRows = deptService.deleteById(id);
        // System.out.println("根据ID删除部门: " + id);
        log.info("根据ID删除部门: {}", id);
        if (affectedRows > 0) {
            return Result.success();
        } else {
            return Result.error("删除失败");
        }
    }

    //新增部门
    @PostMapping
    public Result addDept(@RequestBody Dept dept) { // @RequestBody注解用于将请求体中的JSON数据绑定到方法对象中的字段中，字段名与json中的字段名一致
        int affectedRows = deptService.addByDept(dept);
        if (affectedRows > 0) {
            // System.out.println("新增部门: " + dept);
            log.info("新增部门: {}", dept);
            return Result.success();
        } else {
            return Result.error("增添失败");
        }    
    }

    // 
    @GetMapping(value = "/{id}")
    public Result getDeptInfo(@PathVariable("id") Integer deptId) { //@PathVariable注解用于将URL中的路径变量绑定到方法参数中，路径变量名与注解中的value属性值一致路径变量名与方法参数名一致，@PathVariable注解可以省略不写
        Dept dept = deptService.getById(deptId);
        if (dept != null) {
            // System.out.println("根据Id查询部门: " + deptId);  
            // 日志记录查询部门信息
            log.info("根据Id查询部门: {}", dept);
            return Result.success(dept);
        } else {
            return Result.error("查询失败");
        }
    }

    // 修改部门数据
    @PutMapping
    public Result updateDeptInfo(@RequestBody Dept dept) {
        int affectedRows = deptService.update(dept);
        if (affectedRows > 0) {
            // System.out.println("修改部门数据: " + dept);
            // 日志记录修改部门数据
            log.info("修改部门数据: {}", dept);
            return Result.success();
        } else {
            return Result.error("修改错误");
        }
    }

    
}

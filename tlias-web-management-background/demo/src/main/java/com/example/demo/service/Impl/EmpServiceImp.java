package com.example.demo.service.Impl;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.example.demo.mapper.EmpExprMapper;
import com.example.demo.mapper.EmpMapper;
import com.example.demo.model.Emp;
import com.example.demo.model.EmpExpr;
import com.example.demo.model.EmpLog;
import com.example.demo.model.EmpQueryParam;
import com.example.demo.model.LoginInfo;
import com.example.demo.model.PageResult;
import com.example.demo.service.EmpLogService;
import com.example.demo.service.EmpService;
import com.example.demo.utils.JwtUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

@Service
public class EmpServiceImp implements EmpService{

    private static final Logger log = LoggerFactory.getLogger(EmpServiceImp.class);
    
    @Autowired
    private EmpMapper empMapper;
    @Autowired
    private EmpExprMapper empExprMapper;
    @Autowired
    private EmpLogService empLogService;
    
    //原始分页查询
    // @Override
    // public PageResult<Emp> page(Integer page, Integer pageSize) {
    //     // 计算起始索引
    //     Integer start = (page - 1) * pageSize;
        
    //     // 获取总记录数
    //     Long total = empMapper.count();
        
    //     // 获取分页数据
    //     List<Emp> rows = empMapper.list(start, pageSize);
        
    //     // 创建并返回PageResult对象
    //     return new PageResult<Emp>(total, rows);
    // }

    // pegeHelper使用注意事项:
        // 1. 必须在查询方法之前调用PageHelper.startPage方法设置分页参数
        // 2. 紧跟在查询方法后面的第一个查询结果会被分页
        // 3. 分页参数只对紧跟在它后面的第一个查询方法生效
        // 4. 分页参数在当前线程中生效，方法执行完成后会自动清除
        // 5. 分页参数可以在一次请求中多次调用，每次调用会覆盖之前的参数
    @Override
    public PageResult<Emp> page(EmpQueryParam param) {
        //设置分页参数
        PageHelper.startPage(param.getPage(), param.getPageSize()); //使用PageHelper插件进行分页查询
        // 获取分页数据
        List<Emp> empList = empMapper.list(param);
        try (//解析查询结果，并封装
        Page<Emp> p = (Page<Emp>) empList) { //将查询结果转换为Page对象
            // 创建并返回PageResult对象
            return new PageResult<Emp>(p.getTotal(), p.getResult());
        }
    }

    @Transactional(rollbackFor = Exception.class) // 开启事务管理, 确保数据库操作的原子性,rollbackFor属性用于控制出现何种异常，回滚事务（默认回滚RuntimeException和Error异常）
    @Override
    public int insert(Emp emp) {
        log.info("开始插入员工信息: {}", emp);
        try {
            emp.setUpdateTime(LocalDateTime.now());
            emp.setUpdateTime(LocalDateTime.now());
            int rows = empMapper.insert(emp);
            // int i = 1/0; // 模拟异常
            //保存员工的工作经历(批量完成)
            List<EmpExpr> exprList = emp.getExprList();
            if (!CollectionUtils.isEmpty(exprList)) {
                exprList.forEach(empExpr ->{
                    empExpr.setEmpId(emp.getId());
                });
                empExprMapper.insertBatch(exprList);
                log.info("成功插入员工工作经历，数量: {}", exprList.size());
            }
            log.info("员工信息插入成功，影响行数: {}", rows);
            return rows;
        } catch (Exception e) {
            log.error("插入员工信息失败: {}", emp, e);
            throw e; // 重新抛出异常让上层处理
        } finally {
            // 记录日志
            EmpLog empLog = new EmpLog();
            empLog.setOperateTime(LocalDateTime.now());
            empLog.setInfo("插入员工信息: " + emp);
            empLogService.insert(empLog);
        }
    }
    @Transactional
    @Override
    public boolean deleteByIds(List<Integer> ids) {
        log.info("开始批量删除员工信息: ids={}", ids);
        try {
            //1. 根据ID批量删除员工基本信息
            empMapper.deleteByIds(ids);

            //2. 根据员工的ID批量删除员工的工作经历信息
            empExprMapper.deleteByEmpIds(ids);
            
            log.info("批量删除员工信息成功，影响行数: {}", ids.size());
            return true;
        } catch (Exception e) {
            log.error("批量删除员工信息失败: ids={}", ids, e);
            throw e; // 重新抛出异常让上层处理
        } finally {
            EmpLog empLog = new EmpLog();
            empLog.setOperateTime(LocalDateTime.now());
            empLog.setInfo("删除员工");
            empLogService.insert(empLog);
        }
    }

    @Override
    public Emp getInfo(Integer id) {
        return empMapper.getById(id);
    }

    @Transactional
    @Override
    public void update(Emp emp) {
        // 1.根据员工ID更新员工基本信息
        emp.setUpdateTime(LocalDateTime.now());
        empMapper.updateById(emp);

        //2删除旧的员工工作经历信息
        empExprMapper.deleteByEmpIds(Arrays.asList(emp.getId()));
        //3新增员工的工作经历数据(未变动的和变动的)
        List<EmpExpr> exprList = emp.getExprList();
        if (!CollectionUtils.isEmpty(exprList)) {
            exprList.forEach(empExpr -> empExpr.setEmpId(emp.getId()));
        }
    }

    @Override
    public List<Emp> getAllEmpInfo() {
        return empMapper.getAllEmpInfo();
    }

    @Override
    public LoginInfo login(Emp emp) {
        Emp empLogin = empMapper.getUsernameAndPassword(emp);
        if (empLogin != null) {
            Map<String, Object> dataMap = new HashMap<>();
            dataMap.put("id", empLogin.getId());
            dataMap.put("username", empLogin.getUsername());
            String jwt = JwtUtils.generateJwt(dataMap);
            LoginInfo loginInfo = new LoginInfo(empLogin.getId(), empLogin.getUsername(), empLogin.getName(), jwt);
            return loginInfo;
        } else {
            return null;
        }
    }
}
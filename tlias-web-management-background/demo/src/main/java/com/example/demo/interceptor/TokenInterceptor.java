package com.example.demo.interceptor;

import org.apache.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.example.demo.utils.CurrentHolder;
import com.example.demo.utils.JwtUtils;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class TokenInterceptor implements HandlerInterceptor{


    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler)
            throws Exception {
        

        //1 获取请求uri
        String uri = httpServletRequest.getRequestURI(); 
        //2 判断请求中是否包含login，包含则是登录操作，直接放行
        if (uri.contains("login")) {
            log.info("登录请求，直接放行");
            return true;
        }
        //3 获取请求头汇总的令牌token
        String token = httpServletRequest.getHeader("token");
        //4 判断令牌是否存在,不存在返回响应状态码401
        if (token == null || token.isEmpty()) {
            log.info("获取到的token为空，返回401");
            httpServletResponse.setStatus(HttpStatus.SC_UNAUTHORIZED);
            return false;
        }

        //5 解析token,解析失败返回响应状态码401
        try {
            Claims claims = JwtUtils.parserJwt(token);
            Integer empId = Integer.valueOf(claims.get("id").toString());
            CurrentHolder.setCurrentId(empId);
            log.info("当前登录员工id: {}", empId);
        } catch (Exception e) {
            log.info("令牌非法,响应401");
            httpServletResponse.setStatus(HttpStatus.SC_UNAUTHORIZED);
            return false;
        }

        //6 校验成功，放行
        log.info("令牌合法，放行");


        return true;


    }

    @Override //afterCompletion 方法在请求处理完成后调用，无论是否发生异常
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        log.info("请求资源完成访问后返回，拦截后....");
        CurrentHolder.remove();

    }

    

}

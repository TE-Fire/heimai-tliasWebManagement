package com.example.demo.filter;

import java.io.IOException;

import org.apache.http.HttpStatus;

import com.example.demo.utils.JwtUtils;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
// import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
// @WebFilter(urlPatterns = "/*")
public class TokenFilter implements Filter{
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        //1 获取请求uri
        String uri = httpServletRequest.getRequestURI(); 
        //2 判断请求中是否包含login，包含则是登录操作，直接放行
        if (uri.contains("login")) {
            log.info("登录请求，直接放行");
            chain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }
        //3 获取请求头汇总的令牌token
        String token = httpServletRequest.getHeader("token");
        //4 判断令牌是否存在,不存在返回响应状态码401
        if (token == null || token.isEmpty()) {
            log.info("获取到的token为空，返回401");
            httpServletResponse.setStatus(HttpStatus.SC_UNAUTHORIZED);
            return;
        }

        //5 解析token,解析失败返回响应状态码401
        try {
            JwtUtils.parserJwt(token);
        } catch (Exception e) {
            log.info("令牌非法，响应401");
            httpServletResponse.setStatus(HttpStatus.SC_UNAUTHORIZED);
            return;
        }

        //6 校验成功，放行
        log.info("令牌合法，放行");
        chain.doFilter(httpServletRequest, httpServletResponse);

    }
}

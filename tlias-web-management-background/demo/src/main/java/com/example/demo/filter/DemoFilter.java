package com.example.demo.filter;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
// import jakarta.servlet.annotation.WebFilter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
// @WebFilter(urlPatterns = "/*") //注解WebFilter，urlPatterns表示拦截的URL模式
public class DemoFilter implements Filter{

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("init 初始化方法 ...");
    }
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        log.info("拦截到了请求...");
        //放行
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        log.info("destory 销毁方法 ...");
    }
    
}

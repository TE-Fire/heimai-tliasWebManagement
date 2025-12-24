package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.demo.interceptor.TokenInterceptor;
@Configuration // 配置自定义拦截器
public class WebConfig implements WebMvcConfigurer {
    
    //拦截对象
    @Autowired
    private TokenInterceptor tokenInterceptor;

    @SuppressWarnings("null") // 忽略null警告
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //注册自定义拦截对象
        registry.addInterceptor(tokenInterceptor) // 注册自定义拦截对象
                .addPathPatterns("/**") // 所有请求都拦截
                .excludePathPatterns("/login"); // 登录请求不拦截
    }
}

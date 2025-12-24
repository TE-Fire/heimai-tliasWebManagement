package com.example.demo.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Result;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestController
public class SessionController {
    
    //设置Cookie
    @GetMapping("/c1")
    public Result setCookie(HttpServletResponse response) {
        response.addCookie(new Cookie("name", "王德发"));
        return Result.success();
    }
     
    @GetMapping("/c2")
    public Result getCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("name")) {
                System.out.println("name " + cookie.getValue());
            }
        }
        return Result.success();
    }

    @GetMapping("/s1")
    public Result setSession(HttpSession session) {
        log.info("HttpSession-s1: {}", session.hashCode());
        session.setAttribute("loginUser", "tom");
        return Result.success();
    }

    @GetMapping("/s2")
    public Result getMethodName(HttpServletRequest request) {
        HttpSession session = request.getSession();
        log.info("HttpSession-s2: {}", session.hashCode());
        Object loginUser = session.getAttribute("loginUser");
        return Result.success(loginUser);
    }
    
    

}

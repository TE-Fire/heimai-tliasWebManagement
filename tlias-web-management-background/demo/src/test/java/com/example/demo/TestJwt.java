package com.example.demo;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.TestConfiguration;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@TestConfiguration
public class TestJwt {
    @Test
    public void testGenJwt() {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", 10);
        claims.put("username", "wangdefa");
        
        String jwt = Jwts.builder() //构建JWT
            .signWith(SignatureAlgorithm.HS256, "d2FuZ2RlZmEK") //指定签名算法和密钥
            .addClaims(claims) //添加声明,声明具体指JWT的有效载荷(payload)部分，是一个键值对的集合，用于存储用户信息、权限等
            .setExpiration(new Date(System.currentTimeMillis() + 3600 * 1000)) //设置过期时间
            .compact(); //生成JWT字符串
            log.info("生成base64编码后的令牌{}: ", jwt);
    }
    
    @Test
    public void testParseJwt() {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJpZCI6MTAsInVzZXJuYW1lIjoid2FuZ2RlZmEiLCJleHAiOjE3NjM5MDM3MDB9.pEvTur98YMF7ZoFYAIt0FKYizQ45jKMnCCUDt-uN0gQ";
        Claims claims = Jwts.parser() //解析JWT字符串
                            .setSigningKey("d2FuZ2RlZmEK") //指定密钥，用于验证JWT的签名
                            .parseClaimsJws(token) //解析JWT字符串，验证签名并返回Claims对象
                            .getBody(); //获取Claims对象，包含JWT的有效载荷(payload)部分
        log.info("claims{}: ", claims);
    }
}


package com.exam.config;

import cn.hutool.jwt.JWTUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wangdy
 * @date 2022/4/29 21:33
 */
@Configuration
public class ThirdConfig {
    @Bean
    public JWTUtil jwtUtil(){
        return new JWTUtil();
    }
}

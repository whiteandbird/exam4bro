package com.exam.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * @author wangdy
 * @date 2022/4/28 16:27
 */
@Configuration
public class WebConfigure implements WebMvcConfigurer {
    @Resource
    private AuthInterceptor authInterceptor;

//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//            registry.addMapping("/**")
//
//                    .allowedOrigins("*")
//
//                    .allowedMethods("GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS")
//
//                    .allowCredentials(true)
//
//                    .maxAge(3600)
//
//                    .allowedHeaders("*");
//
//
//    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor);
    }
}

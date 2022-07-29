package com.exam.config;

import com.alibaba.fastjson.JSON;
import com.exam.entity.ApiResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.time.LocalDateTime;

/**
 * @author wangdy
 * @date 2022/4/30 7:57
 */
@Component
@RestControllerAdvice
public class ResponseConfig implements ResponseBodyAdvice {
    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter methodParameter, MediaType mediaType, Class aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        System.out.println("===========拦截件=====================");
        System.out.println(body);
        if (body instanceof String) {
            return JSON.toJSONString((new ApiResult(200,
                    "success", body)));
        }
        //如果响应返回类型为统一格式，直接返回
        if (body instanceof ApiResult) {
            return body;
        } else {
            // 否则，封装响应返回为统一格式  （注：正确返回才会进入此流程，存在错误的会被异常捕获）
            return new ApiResult(200,
                    "success",  body);
        }
    }
}

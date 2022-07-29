package com.exam.config;

import com.exam.entity.ApiResult;
import com.exam.exceptions.AuthException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author wangdy
 * @date 2022/4/28 16:43
 */
@Slf4j
@RestControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler(AuthException.class)
    public ApiResult errHandler(AuthException e){
        log.info("登录异常");
        return new ApiResult(403, "failed", e.getMsg());
    }


    @ExceptionHandler(value = Exception.class)
    public ApiResult errHandler(Exception e){
        log.info("捕捉全局错误{}",e);
        return new ApiResult(500, "err", "服务错误");
    }
}

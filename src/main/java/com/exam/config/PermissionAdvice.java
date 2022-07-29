package com.exam.config;

import com.exam.context.Current;
import com.exam.entity.ContextUser;
import com.exam.exceptions.AuthException;
import com.exam.exceptions.ExamException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Currency;

/**
 * @author wangdy
 * @date 2022/5/4 8:59
 */
@Component
@Aspect
@Slf4j
public class PermissionAdvice {

    @Pointcut("@annotation(com.exam.annotation.Permission)")
    public void pointCut(){};


    @Before("pointCut()")
    public void beforeHandle(JoinPoint point)  {
        String token = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader("token");
        log.info("=========切面拦截=============token=={}", token);
        ContextUser user = Current.user();
        if(null == user || user.getType() != 0){
            throw new AuthException("未授权或者权限不足");
        }
    }
}

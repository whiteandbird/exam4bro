package com.exam.controller;

import com.exam.entity.*;
import com.exam.exceptions.AuthException;
import com.exam.exceptions.ExamException;
import com.exam.service.LoginService;
import com.exam.serviceimpl.LoginServiceImpl;
import com.exam.util.ApiResultHandler;
import com.exam.util.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Slf4j
@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;
    @Autowired
    private JwtUtils jwtUtils;

    @ResponseBody
    @PostMapping("/login")
    public ApiResult login(@RequestBody @Valid Login login, HttpServletResponse response) {
        log.info("enter login");

        User user = loginService.userLogin(login);
        if(user == null){
            throw new AuthException("用户登录失败");
        }
        String token = jwtUtils.createToken(user.getUserId(), user.getUserName(), user.getRole());
        response.setHeader("token", token);
        return ApiResultHandler.buildApiResult(200, "成功", user);
    }

    @GetMapping("/logout")
    public ApiResult<String> logout(){
        return ApiResultHandler.buildApiResult(200, "成功", "logout");
    }
}

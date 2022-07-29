package com.exam.util;

import cn.hutool.core.date.DateUtil;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import com.exam.entity.ContextUser;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wangdy
 * @date 2022/4/29 21:22
 */
@Data
@Component
@ConfigurationProperties(prefix = "jwt.token")
public class JwtUtils {

    @Resource
    private JWTUtil util;

    private String sig;


    public String createToken(Integer userId, String userName,Integer role){
        Map<String,Object> userInfo = new HashMap<>();
        userInfo.put("userId", userId);
        userInfo.put("userName", userName);
        userInfo.put("role", role);
        userInfo.put("loginTime", new Date().getTime());
        String token = JWTUtil.createToken(userInfo, sig.getBytes());
        return token;
    }

    public boolean verify(String token){
        return util.verify(token, sig.getBytes());
    }

    public JWT getInfo(String token){
        return util.parseToken(token);
    }

    public static void main(String[] args) {
        Map<String, Object> map = new HashMap<>();
        map.put("name","wang");
        String token = JWTUtil.createToken(map, "hello".getBytes(StandardCharsets.UTF_8));
        System.out.println(token);

        JWTUtil jwtUtil = new JWTUtil();
        boolean verify = jwtUtil.verify(token, "hello".getBytes(StandardCharsets.UTF_8));
        System.out.println(verify);
        JWT jwt = jwtUtil.parseToken(token);
        Object name = jwt.getPayload("name");
        System.out.println(name);
    }
}

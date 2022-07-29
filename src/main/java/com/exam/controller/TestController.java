package com.exam.controller;

import cn.hutool.core.util.StrUtil;
import com.exam.properties.WhiteProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wangdy
 * @date 2022/4/28 15:21
 */
@RestController
public class TestController {
    @Autowired
    private WhiteProperties properties;

    @GetMapping("/config")
    public String getConfig(){
        return StrUtil.join(",", properties.getWhite());
    }
}

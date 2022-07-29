package com.exam.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author wangdy
 * @date 2022/4/28 15:17
 */
@Data
@Component
@ConfigurationProperties(prefix = "applicaiton.service.url")
public class WhiteProperties {

    private List<String> white;
}

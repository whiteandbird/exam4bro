package com.exam.util;

import lombok.Builder;
import lombok.Data;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author wangdy
 * @date 2022/6/14 12:11
 */
public class IpUtils {
    public static String getIPAddress(HttpServletRequest request) {
        String ip = null;

        //X-Forwarded-For：Squid 服务代理
        String ipAddresses = request.getHeader("X-Forwarded-For");

        if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
            //Proxy-Client-IP：apache 服务代理
            ipAddresses = request.getHeader("Proxy-Client-IP");
        }

        if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
            //WL-Proxy-Client-IP：weblogic 服务代理
            ipAddresses = request.getHeader("WL-Proxy-Client-IP");
        }

        if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
            //HTTP_CLIENT_IP：有些代理服务器
            ipAddresses = request.getHeader("HTTP_CLIENT_IP");
        }

        if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
            //X-Real-IP：nginx服务代理
            ipAddresses = request.getHeader("X-Real-IP");
        }

        //有些网络通过多层代理，那么获取到的ip就会有多个，一般都是通过逗号（,）分割开来，并且第一个ip为客户端的真实IP
        if (ipAddresses != null && ipAddresses.length() != 0) {
            ip = ipAddresses.split(",")[0];
        }

        //还是不能获取到，最后再通过request.getRemoteAddr();获取
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    public static void main(String[] args) {
        List<Man> mans = new ArrayList<>();
        mans.add(Man.builder().name("wang").age(BigDecimal.valueOf(22)).other("other").build());
        mans.add(Man.builder().name("anmi").age(BigDecimal.valueOf(22)).other("other").build());
        mans.add(Man.builder().name("anmi").age(BigDecimal.valueOf(22)).other("other").build());
        mans.add(Man.builder().name("li").age(BigDecimal.valueOf(22)).other("other").build());
        Map<String, BigDecimal> collect = mans.stream().collect(Collectors.groupingBy(Man::getName, Collectors.mapping(Man::getAge, Collectors.reducing(BigDecimal.ZERO, BigDecimal::add))));
        System.out.println(collect);
    }

    @Builder
    @Data
    public static class Man{
        private String name;
        private BigDecimal age;
        private String other;
    }

}

package com.exam.config;

import cn.hutool.core.date.DateUtil;
import cn.hutool.jwt.JWT;
import com.alibaba.fastjson.JSON;
import com.exam.context.Current;
import com.exam.entity.ApiResult;
import com.exam.entity.ContextUser;
import com.exam.properties.WhiteProperties;
import com.exam.util.JwtUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.exam.constants.RequestMethodConstants.OPTION_METHOD;
import static com.exam.util.IpUtils.getIPAddress;

/**
 * @author wangdy
 * @date 2022/4/28 15:06
 */
@Slf4j
@Service
public class AuthInterceptor implements HandlerInterceptor {
    @Resource
    private WhiteProperties properties;
    @Resource
    private JwtUtils jwtUtils;

    @Value("${application.service.url.white}")
    private List<String> white;

    private Pattern pattern;

    /**
     * 1.服务端不需要存储用户的认证信息
     * 2.避免跨域
     * 3.数据的安全性
     *
     * what is jwt JSON WEB TOKEN（web应用的令牌）
     * 用户的认证、存储信息、加密数据
     * 访问页面需要带上token 才能进行访问
     *
     * JWT 组成结构 --- 标头(HEADER) 载荷(PAYLOAD) 签名(Signature)
     * 结构 HEADER.PAYLOAD.SIGNATURE
     * 标头 ：使用Base64 编码 将令牌类型和签名算法经过加工后生成的一段字符串 Base64(JWT类型+签名算法)
     *       JWT    ：类型
     *       签名算法： SHA256,HMAC
     *       Base64({'agl':'HS256', 'typ':'JWT'})
     *
     * 载荷：存储一些自定义信息。 也是使用base64编码加工生成的一段字符串 Base64({name:'张三',age:13}) --> fajsfjasjfafnsnb
     *
     * 签名：通过一个密钥和标头中提供的算法再将标头和载荷进行加工生成的一段字符串;
     *      HS256(Base64(Header)+"."+BASE64(payload), secret) --> 'fasfhsadfnnsadfh'
     *
     *  认证流程：
     *
     *     前端                                后端
     *                    1
     *      认证      ----------->             通过
     *                                         |2
     *   前端保存     <----------             生成token
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String ipAddress = getIPAddress(request);
        String requestURL = request.getRequestURI();
        log.info("ip : {}, requestUrl : {}", ipAddress, requestURL);
        System.out.println("");
        if(isOptionRequest(request)){
            log.info("=============option method================");
            return true;
        }
        String uri = request.getRequestURI();
        log.info(uri);
        if(pattern.matcher(uri).find()){
            return true;
        }
        boolean pass = checkLoginValid(request.getHeader("token"),response);
        if(!pass){
            errAuth(response);
            return false;
        }
        return true;
    }


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        Current.clear();
    }

    private boolean isOptionRequest(HttpServletRequest request){
        return OPTION_METHOD.equals(request.getMethod().toLowerCase());
    }

    private void errAuth(HttpServletResponse response) throws IOException {

        ApiResult apiResult = new ApiResult(403,"no auth","no auth");
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().print(JSON.toJSONString(apiResult));
        response.getWriter().flush();
    }

    private boolean checkLoginValid(String token,HttpServletResponse response){
        if(null == token){
            return false;
        }
        boolean verify = jwtUtils.verify(token);
        if(!verify){
            return false;
        }
        JWT info = jwtUtils.getInfo(token);
        Long loginTime = (Long) info.getPayload("loginTime");
        Long timedif = DateUtil.date().getTime()-loginTime;
        if(timedif>90*120*1000){
            return false;
        }
        Integer userId = (Integer) info.getPayload("userId");
        String userName = (String) info.getPayload("userName");
        Integer rule =   (Integer)info.getPayload("role");
        Current.setUserContext(new ContextUser(userId, userName, rule));
        if(timedif>90*90*1000){
            log.info("重新刷新token");
            response.setHeader(token,jwtUtils.createToken(userId, userName, rule));
        }
        return true;
    }

    @PostConstruct
    public void init(){
        pattern = Pattern.compile(String.join("|", white));
    }
}

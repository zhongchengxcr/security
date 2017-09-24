package com.zc.security.core.validate.code;

import com.google.code.kaptcha.Producer;
import com.zc.security.core.validate.code.exception.ValidateCodeException;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 说明 . <br>
 * 处理验证码相关的过滤器
 * <p>
 * Copyright: Copyright (c) 2017/09/24 下午2:56
 * <p>
 * Company: 百趣
 * <p>
 *
 * @author zhongcheng_m@yeah.net
 * @version 1.0.0
 */
@Component("validateCodeFilter")
public class ValidateCodeFilter extends OncePerRequestFilter {


    public static final String IMAGE_CODE_SESSION_KEY = "IMAGE_CODE_SESSION_KEY";

    @Autowired
    private Producer producer;

    /**
     * 验证码校验失败处理器
     */
    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {


        System.out.println("request.getRequestURI() >>>" + request.getRequestURI());

            if ( request.getRequestURI().endsWith("form")) {
                HttpSession session = request.getSession(false);

                try {
                    String tarGetcode = (String) session.getAttribute(IMAGE_CODE_SESSION_KEY);

                    if (StringUtils.isEmpty(tarGetcode)) {
                        authenticationFailureHandler.onAuthenticationFailure(request, response, new ValidateCodeException("未找到验证码"));
                        return;
                    }

                    String code = request.getParameter("imageCode");
                    if (StringUtils.isEmpty(code)) {
                        authenticationFailureHandler.onAuthenticationFailure(request, response, new ValidateCodeException("请输入验证码"));
                        return;
                    }


                    if (!code.equals(tarGetcode)) {
                        authenticationFailureHandler.onAuthenticationFailure(request, response, new ValidateCodeException("验证码不匹配"));
                        return;
                    }
                } catch (Exception e) {

                }
            }




            filterChain.doFilter(request, response);
        }



}

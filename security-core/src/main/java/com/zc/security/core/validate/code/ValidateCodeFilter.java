package com.zc.security.core.validate.code;

import com.zc.security.core.SecurityConstants;
import com.zc.security.core.properties.SecurityProperties;
import com.zc.security.core.validate.code.exception.ValidateCodeException;
import com.zc.security.core.validate.code.image.ImageValidateCode;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

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


    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SecurityProperties securityProperties;

    private Set<String> urls = new HashSet<>();

    /**
     * 验证请求url与配置的url是否匹配的工具类
     */
    private AntPathMatcher pathMatcher = new AntPathMatcher();

    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    /**
     * 验证码校验失败处理器
     */
    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        ServletWebRequest servletWebRequest = new ServletWebRequest(request);

        logger.info("request.getRequestURI() >>>" + request.getRequestURI());

        boolean action = false;


        for (String pattern : urls) {
            if (pathMatcher.match(pattern, request.getRequestURI())) {
                action = true;
                break;
            }
        }


        if (action) {
            ImageValidateCode imageValidateCode = (ImageValidateCode) sessionStrategy.getAttribute(servletWebRequest,
                    SecurityConstants.IMAGE_CODE_SESSION_KEY);

            String tarGetcode = imageValidateCode.getCode();

            String imageCode = ServletRequestUtils.getStringParameter(request, "imageCode");


            try {

                if (StringUtils.isEmpty(tarGetcode)) {
                    throw new ValidateCodeException("验证码为空");
                }

                if (StringUtils.isEmpty(imageCode)) {
                    throw new ValidateCodeException("请输入验证码");
                }


                if (!imageCode.equals(tarGetcode)) {
                    throw new ValidateCodeException("验证码不正确");
                }

            } catch (ValidateCodeException vex) {
                authenticationFailureHandler.onAuthenticationFailure(request, response, vex);
                return;
            }
        }
        filterChain.doFilter(request, response);
    }


    @PostConstruct
    private void addUrlToMap() {
        String url = securityProperties.getCode().getImage().getUrl();
        if (StringUtils.isNotBlank(url)) {
            String[] tmp = url.split(",");
            for (String s : tmp) {
                urls.add(s);
            }
        }
    }


}

package com.zc.security.core.validate.code.filter;

import com.zc.security.core.SecurityConstants;
import com.zc.security.core.properties.SecurityProperties;
import com.zc.security.core.validate.code.ValidateCodeProcessor;
import com.zc.security.core.validate.code.ValidateCodeProcessorHolder;
import com.zc.security.core.validate.code.ValidateCodeType;
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
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
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
public class ValidateCodeFilter extends OncePerRequestFilter {


    private Logger logger = LoggerFactory.getLogger(getClass());

    private SecurityProperties securityProperties;

    /**
     * 验证请求url与配置的url是否匹配的工具类
     */
    private AntPathMatcher pathMatcher = new AntPathMatcher();

    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    private Map<String, ValidateCodeType> urls = new HashMap<>();

    private ValidateCodeProcessorHolder validateCodeProcessorHolder;

    private AuthenticationFailureHandler authenticationFailureHandler;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        ServletWebRequest servletWebRequest = new ServletWebRequest(request, response);

        logger.info("request.getRequestURI() >>>" + request.getRequestURI());


        ValidateCodeType validateCodeType = getValidateCodeType(request);


        if (validateCodeType != null) {
            ValidateCodeProcessor validateCodeProcessor = validateCodeProcessorHolder.findValidateCodeProcessor(validateCodeType);

            try {
                validateCodeProcessor.validate(servletWebRequest);
            } catch (ValidateCodeException e) {
                authenticationFailureHandler.onAuthenticationFailure(request, response, e);
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    /**
     * 获取ValidateType
     *
     * @param request
     * @return
     */
    private ValidateCodeType getValidateCodeType(HttpServletRequest request) {

        ValidateCodeType validateCodeType = null;

        for (String url : urls.keySet()) {
            boolean action = pathMatcher.match(url, request.getRequestURI());

            if (action) {
                validateCodeType = urls.get(url);
            }
        }

        return validateCodeType;
    }


    /**
     * 容器启动时执行,向map中添加配置好的url
     */
    @PostConstruct
    private void addUrlToMap() {


        String imageUrls = securityProperties.getCode().getImage().getUrl();
        String smsUrls = securityProperties.getCode().getSms().getUrl();

        urls.put(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM, ValidateCodeType.IMAGE);
        urls.put(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_SMS, ValidateCodeType.SMS);

        addUrl2Map(imageUrls, ValidateCodeType.IMAGE);
        addUrl2Map(smsUrls, ValidateCodeType.SMS);

        //System.out.println(":ab:cd:ef::".split(":").length);//末尾分隔符全部忽略
        //System.out.println(":ab:cd:ef::".split(":",-1).length);//不忽略任何一个分隔符
        //System.out.println(StringUtils.split(":ab:cd:ef::",":").length);//最前面的和末尾的分隔符全部都忽略,apache commons
        //System.out.println(StringUtils.splitPreserveAllTokens(":ab:cd:ef::",":").length);//不忽略任何一个分隔符 apache commons

    }

    /**
     * @param src
     * @param type
     */
    private void addUrl2Map(String src, ValidateCodeType type) {

        if (StringUtils.isNotBlank(src)) {
            for (String url : src.split(",")) {
                urls.put(url, type);
            }
        }
    }


    public void setAuthenticationFailureHandler(AuthenticationFailureHandler authenticationFailureHandler) {
        this.authenticationFailureHandler = authenticationFailureHandler;
    }


    public void setSecurityProperties(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }

    public void setValidateCodeProcessorHolder(ValidateCodeProcessorHolder validateCodeProcessorHolder) {
        this.validateCodeProcessorHolder = validateCodeProcessorHolder;
    }
}

package com.zc.security.app.authentication.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zc.security.app.authentication.handler.openid.OpenIdAuthenticationToken;
import com.zc.security.app.social.exception.SocialUserNotFoundException;
import com.zc.security.core.properties.SecurityProperties;
import com.zc.security.core.support.SimpleResponse;
import com.zc.security.core.support.SocialSingUpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.security.SocialAuthenticationServiceLocator;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登陆成功处理逻辑 . <br>
 * <p>
 * <p>
 * Copyright: Copyright (c) 2017/09/24 下午3:01
 * <p>
 * Company: 百趣
 * <p>
 *
 * @author zhongcheng_m@yeah.net
 * @version 1.0.0
 */
@Component()
public class AppAuthenctiationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SecurityProperties securityProperties;

    @Resource
    private ObjectMapper objectMapper;


    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {


        logger.info("login failed");
        Object reponse = null;

        //社交登录用户未找到
        if (exception instanceof SocialUserNotFoundException) {
            //TODO  待改造
            reponse = new SocialSingUpResponse();
            OpenIdAuthenticationToken authenticationToken = (OpenIdAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

            String providerId = authenticationToken.getProviderId();



        } else {
            reponse = new SimpleResponse(exception.getMessage());
        }


        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(reponse));


    }


}

package com.zc.security.browser.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zc.security.core.properties.SecurityProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Component("simpleAuthenticationSuccessHandler")
public class SimpleAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SecurityProperties securityProperties;

    @Resource
    private ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {

        logger.info(" 登录成功处理!");

        logger.info("loginType >>> {} !" , securityProperties.getBrowser().getLoginType() );

        switch (securityProperties.getBrowser().getLoginType()) {

            case JSON:
                response.setContentType("application/json;charset=UTF-8");
                response.getWriter().write(objectMapper.writeValueAsString(authentication));
                break;
            case REDIRECT:
                super.onAuthenticationSuccess(request, response, authentication);
                break;
            default:
                super.onAuthenticationSuccess(request, response, authentication);
        }

    }
}

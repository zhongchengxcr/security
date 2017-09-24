package com.zc.security.browser.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zc.security.core.properties.SecurityProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component("simpleAuthenctiationFailureHandler")
public class SimpleAuthenctiationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SecurityProperties securityProperties;

    @Resource
    private ObjectMapper objectMapper;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        logger.info("登录失败处理!");

        logger.info("loginType >>> {} !" , securityProperties.getBrowser().getLoginType() );
        switch (securityProperties.getBrowser().getLoginType()){
            case JSON:
                response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
                response.setContentType("application/json;charset=UTF-8");
                response.getWriter().write(objectMapper.writeValueAsString(exception.getMessage()));
                break;
            case REDIRECT:
                super.onAuthenticationFailure(request,response,exception);
                break;
            default:
                super.onAuthenticationFailure(request,response,exception);
        }


    }


}

package com.zc.security.browser;

import com.zc.security.core.SecurityConstants;
import com.zc.security.core.authentication.sms.SmsCodeAuthenticationFilter;
import com.zc.security.core.authentication.sms.SmsCodeAuthenticationSecurityConfig;
import com.zc.security.core.properties.SecurityProperties;
import com.zc.security.core.validate.code.ValidateCodeProcessorHolder;
import com.zc.security.core.validate.code.filter.ValidateCodeFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;

/**
 * 浏览器部分 Configu ation
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
@Configuration
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private SecurityProperties securityProperties;

    @Resource(name = "simpleAuthenctiationFailureHandler")
    private AuthenticationFailureHandler authenticationFailureHandler;

    @Resource(name = "simpleAuthenticationSuccessHandler")
    private AuthenticationSuccessHandler authenticationSuccessHandler;

    @Autowired
    private ValidateCodeProcessorHolder validateCodeProcessorHolder;

    @Autowired
    private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;


    @Bean
    public PasswordEncoder setPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        ValidateCodeFilter validateCodeFilter = new ValidateCodeFilter();
        validateCodeFilter.setAuthenticationFailureHandler(authenticationFailureHandler);
        validateCodeFilter.setSecurityProperties(securityProperties);
        validateCodeFilter.setValidateCodeProcessorHolder(validateCodeProcessorHolder);

        http.apply(smsCodeAuthenticationSecurityConfig).and()
                .addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class)
                .formLogin()
                .loginPage(securityProperties.getBrowser().getLoginPage())   //登录页面
                .loginProcessingUrl(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM)  //处理登录页面的url
                .successHandler(authenticationSuccessHandler)
                .failureHandler(authenticationFailureHandler)
                .and()
                .authorizeRequests()//权限配置
                .antMatchers(securityProperties.getBrowser().getLoginPage(), SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM, SecurityConstants.DEFAULT_VALIDATE_PROCESSING_URL_IMAGE, SecurityConstants.DEFAULT_VALIDATE_PROCESSING_URL_SMS).permitAll() //匹配  登录页面地址  放开所有权限
                .anyRequest()   //任何请求
                .authenticated()  //都需要授权
                .and().csrf().disable();    //表单登录

    }
}

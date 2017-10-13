package com.zc.security.app.config.server;

import com.zc.security.app.authentication.handler.openid.OpenIdAuthenticationSecurityConfig;
import com.zc.security.core.SecurityConstants;
import com.zc.security.core.authentication.sms.SmsCodeAuthenticationSecurityConfig;
import com.zc.security.core.properties.SecurityProperties;
import com.zc.security.core.validate.code.config.ValidateCodeSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.social.security.SpringSocialConfigurer;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

/**
 * 资源服务器配置. <br>
 * <p>
 * <p>
 * Copyright: Copyright (c) 2017/10/07 17:12
 * <p>
 * Company: 百趣
 * <p>
 *
 * @author zhongcheng_m@yeah.net
 * @version 1.0.0
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired()
    private AuthenticationFailureHandler authenticationFailureHandler;

    @Autowired()
    private AuthenticationSuccessHandler authenticationSuccessHandler;

    /**
     * 短信验证码登录配置
     */
    @Autowired
    private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;

    /**
     * 验证码配置
     */
    @Autowired
    private ValidateCodeSecurityConfig validateCodeSecurityConfig;

    @SuppressWarnings("all")
    @Autowired
    private DataSource dataSource;

    @Autowired
    private SpringSocialConfigurer springSocialConfigurer;


    @Autowired
    private OpenIdAuthenticationSecurityConfig openIdAuthenticationSecurityConfig;


    private List<String> permitAllUrl = new ArrayList<>();

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.apply(smsCodeAuthenticationSecurityConfig)
                .and()
                .apply(validateCodeSecurityConfig)
                .and()
                .apply(springSocialConfigurer)
                .and()
                .apply(openIdAuthenticationSecurityConfig)
                .and()
                .formLogin()
                .loginPage(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL)   //登录页面
                .loginProcessingUrl(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM)  //处理登录页面的url
                //.loginProcessingUrl(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL)  //处理登录页面的url
                .successHandler(authenticationSuccessHandler)
                .failureHandler(authenticationFailureHandler)
                .and()
                .authorizeRequests()//权限配置
                .antMatchers(postPermitAllUrl()).permitAll() //匹配  登录页面地址  放开所有权限
                .anyRequest()   //任何请求
                .authenticated()  //都需要授权
                .and().csrf().disable();    //表单登录
    }

    /**
     * 添加无需授权即可访问的页面
     *
     * @return
     */
    private String[] postPermitAllUrl() {
        //默认的用户名密码登录请求处理url
        permitAllUrl.add(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM);
        //默认的获取图片验证码的url
        permitAllUrl.add(SecurityConstants.DEFAULT_VALIDATE_PROCESSING_URL_IMAGE);
        //默认的获取短信验证码的url
        permitAllUrl.add(SecurityConstants.DEFAULT_VALIDATE_PROCESSING_URL_SMS);
        //默认的注册url
        permitAllUrl.add(securityProperties.getBrowser().getSignUpUrl());
        //社交登录授权跳转url
        permitAllUrl.add(securityProperties.getSocial().getFilterProcessesUrl() + "/*");
        //注册处理逻辑
        permitAllUrl.add("/user/regist");
        //需要授权时跳转的url
        permitAllUrl.add(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL);

        //社交登录绑定,解绑相关
        permitAllUrl.add("/connect/*");
        //session超时
        permitAllUrl.add(securityProperties.getBrowser().getSessionTimeoutUrl());

        //登出
        permitAllUrl.add("/oauth/token");

        String[] permitAllUrlArray = new String[permitAllUrl.size()];


        return permitAllUrl.toArray(permitAllUrlArray);
    }
}

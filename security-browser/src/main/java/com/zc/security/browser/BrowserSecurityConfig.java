package com.zc.security.browser;

import com.zc.security.core.SecurityConstants;
import com.zc.security.core.authentication.sms.SmsCodeAuthenticationSecurityConfig;
import com.zc.security.core.properties.SecurityProperties;
import com.zc.security.core.validate.code.config.ValidateCodeSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.social.security.SpringSocialConfigurer;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

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
    private UserDetailsService userDetailsService;

    @Autowired
    private SpringSocialConfigurer springSocialConfigurer;

    @Autowired
    private SessionInformationExpiredStrategy expiredStrategy;

    @Autowired
    private InvalidSessionStrategy invalidSessionStrategy;

    @Autowired
    private LogoutSuccessHandler logoutSuccessHandler;


    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
        // tokenRepository.setCreateTableOnStartup(true);
        return tokenRepository;
    }

    private List<String> permitAllUrl = new ArrayList<>();

    @Override
    protected void configure(HttpSecurity http) throws Exception {


        http.apply(smsCodeAuthenticationSecurityConfig)
                .and()
                .apply(validateCodeSecurityConfig)
                .and()
                .apply(springSocialConfigurer)
                .and()
                .formLogin()
                .loginPage(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL)   //登录页面
                .loginProcessingUrl(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM)  //处理登录页面的url
                //.loginProcessingUrl(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL)  //处理登录页面的url
                .successHandler(authenticationSuccessHandler)
                .failureHandler(authenticationFailureHandler)
                .and()
                .logout()
                .logoutUrl(securityProperties.getBrowser().getSignOutUrl())
                .logoutSuccessUrl(securityProperties.getBrowser().getSignOutSuccessUrl())
                .logoutSuccessHandler(logoutSuccessHandler)
                .and()
                .sessionManagement()
                //session失效跳转地址
                .invalidSessionUrl(securityProperties.getBrowser().getSessionTimeoutUrl())
                //session过期策略
                .invalidSessionStrategy(invalidSessionStrategy)
                //统一账号最大允许的session个数
                .maximumSessions(securityProperties.getBrowser().getMaximumSessions())

                //是否允许重复登录
                .maxSessionsPreventsLogin(securityProperties.getBrowser().getMaxSessionsPreventsLogin())
                //session并发登录策略
                .expiredSessionStrategy(expiredStrategy)
                .and()
                .and()
                .rememberMe()
                .tokenRepository(persistentTokenRepository())
                .tokenValiditySeconds(securityProperties.getBrowser().getRememberMeSeconds())
                .userDetailsService(userDetailsService)
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
        //浏览器登录页
        permitAllUrl.add(securityProperties.getBrowser().getLoginPage());
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

        permitAllUrl.add("/connect/*");
        //session超时
        permitAllUrl.add(securityProperties.getBrowser().getSessionTimeoutUrl());

        //登出
        permitAllUrl.add(securityProperties.getBrowser().getSignOutUrl());

        String[] permitAllUrlArray = new String[permitAllUrl.size()];


        return permitAllUrl.toArray(permitAllUrlArray);
    }
}

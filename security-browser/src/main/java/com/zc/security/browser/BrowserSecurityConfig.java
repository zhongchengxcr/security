package com.zc.security.browser;

import com.zc.security.core.properties.SecurityConstants;
import com.zc.security.core.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.annotation.Resource;

@Configuration
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private SecurityProperties securityProperties;


    @Resource(name = "simpleAuthenctiationFailureHandler")
    private AuthenticationFailureHandler authenticationFailureHandler;

    @Resource(name = "simpleAuthenticationSuccessHandler")
    private AuthenticationSuccessHandler authenticationSuccessHandler;

    @Bean
    public PasswordEncoder setPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.formLogin()
                .loginPage(securityProperties.getBrowser().getLoginPage())   //登录页面
                .loginProcessingUrl(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM)  //处理登录页面的url
                .successHandler(authenticationSuccessHandler)
                .failureHandler(authenticationFailureHandler)
                .and()
                .authorizeRequests()//权限配置
                .antMatchers(securityProperties.getBrowser().getLoginPage(), SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM).permitAll() //匹配  登录页面地址  放开所有权限
                .anyRequest()   //任何请求
                .authenticated()  //都需要授权
                .and().csrf().disable();    //表单登录
    }
}

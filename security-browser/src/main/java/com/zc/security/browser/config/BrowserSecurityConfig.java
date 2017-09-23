package com.zc.security.browser.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter {


    @Bean
    public PasswordEncoder setPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.formLogin()
                .loginPage("/zc/login")   //登录页面
                .loginProcessingUrl("/allpication/login")  //处理登录页面的url
                .and()
                .authorizeRequests()//权限配置
                .antMatchers("/zc/login").permitAll() //匹配  登录页面地址  放开所有权限
                .anyRequest()   //任何请求
                .authenticated()  //都需要授权
                .and().csrf().disable();    //表单登录
    }
}

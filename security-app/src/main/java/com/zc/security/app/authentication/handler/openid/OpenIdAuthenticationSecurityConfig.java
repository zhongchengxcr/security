package com.zc.security.app.authentication.handler.openid;

import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.security.SocialAuthenticationServiceLocator;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Component;

/**
 * 说明 . <br>
 * <p>
 * <p>
 * Copyright: Copyright (c) 2017/10/09 下午10:13
 * <p>
 * Company: 百趣
 * <p>
 *
 * @author zhongcheng_m@yeah.net
 * @version 1.0.0
 */
@Component
public class OpenIdAuthenticationSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
    @Autowired
    private AuthenticationSuccessHandler authenticationSuccessHandler;

    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;

    @Autowired
    private SocialUserDetailsService userDetailsService;

    @Autowired
    private UsersConnectionRepository usersConnectionRepository;

    @SuppressWarnings("all")
    @Autowired
    private SocialAuthenticationServiceLocator authServiceLocator;

    @Override
    public void configure(HttpSecurity http) throws Exception {

//        ApplicationContext applicationContext = http.getSharedObject(ApplicationContext.class);
//        SocialAuthenticationServiceLocator authServiceLocator = getDependency(applicationContext, SocialAuthenticationServiceLocator.class);


        OpenIdAuthenticationFilter openIdAuthenticationFilter = new OpenIdAuthenticationFilter(authServiceLocator);
        openIdAuthenticationFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler);
        openIdAuthenticationFilter.setAuthenticationFailureHandler(authenticationFailureHandler);
        openIdAuthenticationFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));


        OpenIdAuthenticationProvider openIdAuthenticationProvider = new OpenIdAuthenticationProvider();
        openIdAuthenticationProvider.setUserDetailsService(userDetailsService);
        openIdAuthenticationProvider.setUsersConnectionRepository(usersConnectionRepository);


        http.authenticationProvider(openIdAuthenticationProvider)
                .addFilterAfter(openIdAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);


    }


//    /**
//     * @param applicationContext
//     * @param dependencyType
//     * @param <T>
//     * @return
//     */
//    private <T> T getDependency(ApplicationContext applicationContext, Class<T> dependencyType) {
//        try {
//            T dependency = applicationContext.getBean(dependencyType);
//            return dependency;
//        } catch (NoSuchBeanDefinitionException e) {
//            throw new IllegalStateException("SpringSocialConfigurer depends on " + dependencyType.getName() + ". No single bean of that type found in application context.", e);
//        }
//    }
}

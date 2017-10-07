package com.zc.security.core.social;

import com.zc.security.core.properties.SecurityProperties;
import com.zc.security.core.social.binding.view.ConnectResultView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.social.security.SpringSocialConfigurer;
import org.springframework.web.servlet.View;

import javax.sql.DataSource;

/**
 * 说明 . <br>
 * <p>
 * <p>
 * Copyright: Copyright (c) 2017/09/30 下午4:13
 * <p>
 * Company: 百趣
 * <p>
 *
 * @author zhongcheng_m@yeah.net
 * @version 1.0.0
 */
@Configuration
@EnableSocial
public class SocialConfig extends SocialConfigurerAdapter {


    @SuppressWarnings("all")
    @Autowired
    private DataSource dataSource;

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired(required = false)
    private ConnectionSignUp connectionSignUp;

    @Autowired
    private UsersConnectionRepository usersConnectionRepository;


    @Override
    public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
       /* JdbcUsersConnectionRepository  repository = new JdbcUsersConnectionRepository(dataSource, connectionFactoryLocator, Encryptors.noOpText());
        *//**
         * 表名的前缀
         *//*
        repository.setTablePrefix("zc_");

        if (connectionSignUp != null) {
            repository.setConnectionSignUp(connectionSignUp);
        }*/
        return usersConnectionRepository;
    }


    @Bean
    public UsersConnectionRepository usersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
        JdbcUsersConnectionRepository repository = new JdbcUsersConnectionRepository(dataSource, connectionFactoryLocator, Encryptors.noOpText());
        /**
         * 表名的前缀
         */
        repository.setTablePrefix("zc_");

        if (connectionSignUp != null) {
            repository.setConnectionSignUp(connectionSignUp);
        }

        return repository;
    }

    @Bean
    public SpringSocialConfigurer springSocialConfigurer() {
        ZcSpringSocialConfigurer configurer = new ZcSpringSocialConfigurer(securityProperties.getSocial().getFilterProcessesUrl());
        //设置注册url
        configurer.signupUrl(securityProperties.getBrowser().getSignUpUrl());
        return configurer;
    }


    /**
     * 注册工具类
     *
     * @param connectionFactoryLocator
     * @return
     */
    @Bean
    public ProviderSignInUtils providerSignInUtils(ConnectionFactoryLocator connectionFactoryLocator) {
        return new ProviderSignInUtils(connectionFactoryLocator, getUsersConnectionRepository(connectionFactoryLocator));
    }


    @Bean({"connect/weixinConnect", "connect/weixinConnected"})
    @ConditionalOnMissingBean(name = "connectedView")
    public View connectedView() {
        return new ConnectResultView();
    }

}

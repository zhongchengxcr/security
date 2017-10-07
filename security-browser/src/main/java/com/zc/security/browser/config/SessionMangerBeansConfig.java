package com.zc.security.browser.config;

import com.zc.security.browser.logout.SimpleLogoutSuccessHandler;
import com.zc.security.browser.session.DemoSessionStrategy;
import com.zc.security.core.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

/**
 * 说明 . <br>
 * <p>
 * <p>
 * Copyright: Copyright (c) 2017/10/07 14:35
 * <p>
 * Company: 百趣
 * <p>
 *
 * @author zhongcheng_m@yeah.net
 * @version 1.0.0
 */
@Configuration
public class SessionMangerBeansConfig {


    @Autowired
    private SecurityProperties securityProperties;

    @ConditionalOnMissingBean(SessionInformationExpiredStrategy.class)
    @Bean
    public SessionInformationExpiredStrategy sessionInformationExpiredStrategy() {
        return new DemoSessionStrategy(securityProperties);
    }


    @ConditionalOnMissingBean(SessionInformationExpiredStrategy.class)
    @Bean
    public InvalidSessionStrategy invalidSessionStrategy() {
        return new DemoSessionStrategy(securityProperties);
    }


    @ConditionalOnMissingBean(LogoutSuccessHandler.class)
    @Bean
    public LogoutSuccessHandler logoutSuccessHandler() {
        return new SimpleLogoutSuccessHandler(securityProperties.getBrowser().getSignOutSuccessUrl());
    }

}

package com.zc.security.core.social.qq.config;

import com.zc.security.core.properties.SecurityProperties;
import com.zc.security.core.social.qq.connect.QQConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.social.SocialAutoConfigurerAdapter;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.connect.ConnectionFactory;

/**
 * 说明 . <br>
 * <p>
 * <p>
 * Copyright: Copyright (c) 2017/09/30 下午5:21
 * <p>
 * Company: 百趣
 * <p>
 *
 * @author zhongcheng_m@yeah.net
 * @version 1.0.0
 */
@Configuration
@ConditionalOnProperty(prefix = "zc.security.social.qq",name = "appId")
public class QQAutoConfig extends SocialAutoConfigurerAdapter {

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    protected ConnectionFactory<?> createConnectionFactory() {
        String providerId = securityProperties.getSocial().getQq().getProviderId();
        String appId = securityProperties.getSocial().getQq().getAppId();
        String appSecret = securityProperties.getSocial().getQq().getAppSecret();

        return new QQConnectionFactory(providerId, appId, appSecret);
    }
}

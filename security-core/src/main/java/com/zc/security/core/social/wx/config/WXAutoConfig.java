package com.zc.security.core.social.wx.config;

import com.zc.security.core.properties.SecurityProperties;
import com.zc.security.core.social.qq.connect.QQConnectionFactory;
import com.zc.security.core.social.wx.connect.WXConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.social.SocialAutoConfigurerAdapter;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.connect.ConnectionFactory;

/**
 * 说明 . <br>
 * <p>
 * <p>
 * Copyright: Copyright (c) 2017/10/03 19:27
 * <p>
 * Company: 百趣
 * <p>
 *
 * @author zhongcheng_m@yeah.net
 * @version 1.0.0
 */
@Configuration
@ConditionalOnProperty(prefix = "zc.security.social.wx", name = "appId")
public class WXAutoConfig extends SocialAutoConfigurerAdapter {


    @Autowired
    private SecurityProperties securityProperties;

    @Override
    protected ConnectionFactory<?> createConnectionFactory() {
        String providerId = securityProperties.getSocial().getWx().getProviderId();
        String appId = securityProperties.getSocial().getWx().getAppId();
        String appSecret = securityProperties.getSocial().getWx().getAppSecret();

        //return new QQConnectionFactory(providerId, appId, appSecret);

        return new WXConnectionFactory(providerId, appId, appSecret);
    }
}

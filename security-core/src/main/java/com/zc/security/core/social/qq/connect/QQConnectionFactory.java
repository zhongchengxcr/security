package com.zc.security.core.social.qq.connect;

import com.zc.security.core.social.qq.api.QQApi;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;
import org.springframework.social.oauth2.OAuth2ServiceProvider;

/**
 * 说明 . <br>
 * <p>
 * <p>
 * Copyright: Copyright (c) 2017/09/30 下午4:09
 * <p>
 * Company: 百趣
 * <p>
 *
 * @author zhongcheng_m@yeah.net
 * @version 1.0.0
 */
public class QQConnectionFactory extends OAuth2ConnectionFactory<QQApi> {


    /**
     * Create a {@link OAuth2ConnectionFactory}.
     *
     * @param providerId the provider id e.g. "facebook"
     * @param appid
     * @param appSecret
     */
    public QQConnectionFactory(String providerId, String appid , String appSecret) {
        super(providerId, new QQServiceProvider(appid,appSecret), new QQAdapter());
    }



}

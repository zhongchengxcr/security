package com.zc.security.core.social.wx.connect;

import com.zc.security.core.social.wx.api.WXApi;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionData;
import org.springframework.social.connect.support.OAuth2Connection;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2ServiceProvider;

/**
 * 说明 . <br>
 * <p>
 * <p>
 * Copyright: Copyright (c) 2017/10/03 19:21
 * <p>
 * Company: 百趣
 * <p>
 *
 * @author zhongcheng_m@yeah.net
 * @version 1.0.0
 */
public class WXConnectionFactory extends OAuth2ConnectionFactory<WXApi> {

    /**
     * Create a {@link OAuth2ConnectionFactory}.
     *
     * @param providerId the provider id e.g. "facebook"
     * @param appId
     * @param appSecret
     */
    public WXConnectionFactory(String providerId, String appId, String appSecret) {
        super(providerId, new WXServiceProvider(appId, appSecret), new WXAdapter());
    }


    /**
     * 由于微信的openId是和accessToken一起返回的，所以在这里直接根据accessToken设置providerUserId即可，不用像QQ那样通过QQAdapter来获取
     */
    @Override
    protected String extractProviderUserId(AccessGrant accessGrant) {
        if (accessGrant instanceof WXAccessGrant) {
            return ((WXAccessGrant) accessGrant).getOpenId();
        }
        return null;
    }


    /* (non-Javadoc)
 * @see org.springframework.social.connect.support.OAuth2ConnectionFactory#createConnection(org.springframework.social.oauth2.AccessGrant)
 */
    @Override
    public Connection<WXApi> createConnection(AccessGrant accessGrant) {
        return new OAuth2Connection<WXApi>(getProviderId(), extractProviderUserId(accessGrant), accessGrant.getAccessToken(),
                accessGrant.getRefreshToken(), accessGrant.getExpireTime(), getOAuth2ServiceProvider(), getApiAdapter(extractProviderUserId(accessGrant)));
    }

    /* (non-Javadoc)
     * @see org.springframework.social.connect.support.OAuth2ConnectionFactory#createConnection(org.springframework.social.connect.ConnectionData)
     */
    @Override
    public Connection<WXApi> createConnection(ConnectionData data) {
        return new OAuth2Connection<WXApi>(data, getOAuth2ServiceProvider(), getApiAdapter(data.getProviderUserId()));
    }

    private ApiAdapter<WXApi> getApiAdapter(String providerUserId) {
        return new WXAdapter(providerUserId);
    }

    private OAuth2ServiceProvider<WXApi> getOAuth2ServiceProvider() {
        return (OAuth2ServiceProvider<WXApi>) getServiceProvider();
    }


}

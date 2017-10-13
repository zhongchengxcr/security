package com.zc.security.core.properties.oauth;

/**
 * 说明 . <br>
 * <p>
 * <p>
 * Copyright: Copyright (c) 2017/10/12 下午9:28
 * <p>
 * Company: 百趣
 * <p>
 *
 * @author zhongcheng_m@yeah.net
 * @version 1.0.0
 */
public class OAuth2Properties {

    /**
     * 使用jwt时为token签名的秘钥
     */
    private String jwtSigningKey = "zc";
    /**
     * 客户端配置
     */
    private OAuth2ClientProperties[] clients = {};

    private String tokenStore;


    public String getJwtSigningKey() {
        return jwtSigningKey;
    }

    public void setJwtSigningKey(String jwtSigningKey) {
        this.jwtSigningKey = jwtSigningKey;
    }

    public OAuth2ClientProperties[] getClients() {
        return clients;
    }

    public void setClients(OAuth2ClientProperties[] clients) {
        this.clients = clients;
    }

    public String getTokenStore() {
        return tokenStore;
    }

    public void setTokenStore(String tokenStore) {
        this.tokenStore = tokenStore;
    }
}

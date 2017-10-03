package com.zc.security.core.social.qq.connect;

import com.zc.security.core.social.qq.api.QQApi;
import com.zc.security.core.social.qq.api.SimpleQQApi;
import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;
import org.springframework.social.oauth2.OAuth2Template;

/**
 * 说明 . <br>
 * <p>
 * <p>
 * Copyright: Copyright (c) 2017/09/30 下午3:29
 * <p>
 * Company: 百趣
 * <p>
 *
 * @author zhongcheng_m@yeah.net
 * @version 1.0.0
 */
public class QQServiceProvider extends AbstractOAuth2ServiceProvider<QQApi> {


    //QQ 文档地址
    //http://wiki.connect.qq.com/使用authorization_code获取access_token

    /**
     * 第三方应用将用户导向第三方应用授权的地址
     */
    private static final String URL_AUTHORIZE = "https://graph.qq.com/oauth2.0/authorize";

    /**
     * 根据授权码 获取 access token 的地址
     */
    private static final String URL_ACCESSTOKEN = "https://graph.qq.com/oauth2.0/token";


    //=============================================================================

    private String appid;


    /**
     * Create a new {@link QQServiceProvider}.
     *
     * @param appid     qq分配给应用的 appid
     * @param appSecret appid对应的appSecret
     */
    public QQServiceProvider(String appid, String appSecret) {
        super(new QQOauth2Template(appid, appSecret, URL_AUTHORIZE, URL_ACCESSTOKEN));
        this.appid = appid;
    }

    @Override
    public QQApi getApi(String accessToken) {
        return new SimpleQQApi(accessToken, appid);
    }


    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }
}

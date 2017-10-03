package com.zc.security.core.social.wx.connect;

import com.zc.security.core.social.wx.api.SimpleWXApi;
import com.zc.security.core.social.wx.api.WXApi;
import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;

/**
 * 说明 . <br>
 * <p>
 * <p>
 * Copyright: Copyright (c) 2017/10/03 18:52
 * <p>
 * Company: 百趣
 * <p>
 *
 * @author zhongcheng_m@yeah.net
 * @version 1.0.0
 */
public class WXServiceProvider extends AbstractOAuth2ServiceProvider<WXApi> {


    /**
     * 第三方应用将用户导向第三方应用授权的地址
     */
    private static final String URL_AUTHORIZE = "https://open.weixin.qq.com/connect/qrconnect";

    /**
     * 根据授权码 获取 access token 的地址
     */
    private static final String URL_ACCESSTOKEN = "https://api.weixin.qq.com/sns/oauth2/access_token";


    /**
     * Create a new {@link WXServiceProvider}.
     *
     * @param appid
     * @param appSecret
     */
    public WXServiceProvider(String appid, String appSecret) {
        super(new WXOauth2Template(appid, appSecret, URL_AUTHORIZE, URL_ACCESSTOKEN));

    }


    @Override
    public WXApi getApi(String accessToken) {
        return new SimpleWXApi(accessToken);
    }
}

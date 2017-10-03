package com.zc.security.core.social.wx.connect;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Template;
import org.springframework.util.MultiValueMap;

import java.util.Map;

/**
 * 说明 . <br>
 * <p>
 * <p>
 * Copyright: Copyright (c) 2017/10/03 18:57
 * <p>
 * Company: 百趣
 * <p>
 *
 * @author zhongcheng_m@yeah.net
 * @version 1.0.0
 */
public class WXOauth2Template extends OAuth2Template {

    private Logger logger = LoggerFactory.getLogger(getClass());


    public WXOauth2Template(String clientId, String clientSecret, String authorizeUrl, String accessTokenUrl) {
        super(clientId, clientSecret, authorizeUrl, accessTokenUrl);
    }


    @Override
    protected AccessGrant postForAccessGrant(String accessTokenUrl, MultiValueMap<String, String> parameters) {

        /**
         * 文档地址
         * https://open.weixin.qq.com/cgi-bin/showdocument?action=dir_list&t=resource/res_list&verify=1&id=open1419316505&token=b9f33b5f52d1472c4284e89522ce9222fb4ea10b&lang=zh_CN
         */

        /*{
            "access_token":"ACCESS_TOKEN",
                "expires_in":7200,
                "refresh_token":"REFRESH_TOKEN",
                "openid":"OPENID",
                "scope":"SCOPE",
                "unionid": "o6_bmasdasdsad6_2sgVt7hMZOPfL"
        }*/
        Map<String, String> response = getRestTemplate().patchForObject(accessTokenUrl, parameters, Map.class);
        logger.info(" Get access token url : {} ", accessTokenUrl);

        logger.info(" Get access token response : {} ", response);

        String accessToken = response.get("access_token");
        String scope = response.get("scope");
        String refreshToken = response.get("refresh_token");
        Long exprienIn = Long.valueOf(response.get("expires_in"));
        String openId = response.get("openid");
        WXAccessGrant accessGrant = new WXAccessGrant(accessToken, scope, refreshToken, exprienIn);

        accessGrant.setOpenId(openId);

        return accessGrant;
    }


}

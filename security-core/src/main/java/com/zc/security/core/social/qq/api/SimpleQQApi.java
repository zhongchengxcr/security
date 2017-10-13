package com.zc.security.core.social.qq.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.TokenStrategy;

/**
 * 说明 . <br>
 * <p>
 * <p>
 * Copyright: Copyright (c) 2017/09/30 下午2:57
 * <p>
 * Company: 百趣
 * <p>
 *
 * @author zhongcheng_m@yeah.net
 * @version 1.0.0
 */
public class SimpleQQApi extends AbstractOAuth2ApiBinding implements QQApi {

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * qq文档地址
     * http://wiki.connect.qq.com/openapi调用说明_oauth2-0
     */

    //======================================================================================================

    //access_token
    /**
     * 获取opem id 地址
     */
    private final static String URL_GET_OPENID = "https://graph.qq.com/oauth2.0/me?access_token=%s";

    //appid  openid  access_token(由父类代替拼接)

    /**
     * 获取用户信息地址
     */
    private final static String URL_GET_USERINFO = "https://graph.qq.com/user/get_user_info?oauth_consumer_key=%s&openid=%s";

    private ObjectMapper objectMapper = new ObjectMapper();

    private String appId;

    private String openId;

    public SimpleQQApi(String accessToken, String appId) {
        super(accessToken, TokenStrategy.ACCESS_TOKEN_PARAMETER);
        this.appId = appId;

        //获取用户openId

        //QQ文档地址  http://wiki.connect.qq.com/获取用户openid_oauth2-0
        String urlGetOpenId = String.format(URL_GET_OPENID, accessToken);
        String response = getRestTemplate().getForObject(urlGetOpenId, String.class);

        //返回值示例
        //callback( {"client_id":"YOUR_APPID","openid":"YOUR_OPENID"} );

        logger.info("获取QQ用户openId返回结果 : {}", response);

        this.openId = StringUtils.substringBetween(response, "openid\":\"", "\"");

    }

    /**
     * 通过qq互联提供的api 获取qq用户信息
     *
     * @return
     */
    @Override
    public QQUserInfo getQQUserInfo() {
        String urlGetUserInfo = String.format(URL_GET_USERINFO, appId, openId);

        String response = getRestTemplate().getForObject(urlGetUserInfo, String.class);

        QQUserInfo qqUserInfo;

        try {
            qqUserInfo = objectMapper.readValue(response, QQUserInfo.class);
            qqUserInfo.setOpenId(openId);
        } catch (Exception e) {
            throw new RuntimeException("转换用户信息失败!");
        }

        return qqUserInfo;
    }


    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }
}

package com.zc.security.core.social.wx.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.TokenStrategy;

import java.nio.charset.Charset;
import java.util.List;

/**
 * 说明 . <br>
 * <p>
 * <p>
 * Copyright: Copyright (c) 2017/10/03 18:03
 * <p>
 * Company: 百趣
 * <p>
 *
 * @author zhongcheng_m@yeah.net
 * @version 1.0.0
 */
public class SimpleWXApi extends AbstractOAuth2ApiBinding implements WXApi {


    private Logger logger = LoggerFactory.getLogger(getClass());

    private ObjectMapper objectMapper = new ObjectMapper();


    /**
     * 获取用户信息地址
     */
    private final static String URL_GET_USERINFO = "https://api.weixin.qq.com/sns/userinfo?openid=%s";


    public SimpleWXApi(String accessToken) {
        super(accessToken, TokenStrategy.ACCESS_TOKEN_PARAMETER);
    }


    /**
     * 默认注册的StringHttpMessageConverter字符集为ISO-8859-1，而微信返回的是UTF-8的，所以覆盖了原来的方法。
     */
    protected List<HttpMessageConverter<?>> getMessageConverters() {
        List<HttpMessageConverter<?>> messageConverters = super.getMessageConverters();
        messageConverters.remove(0);
        messageConverters.add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
        return messageConverters;
    }

    /**
     * 文档地址
     * https://open.weixin.qq.com/cgi-bin/showdocument?action=dir_list&t=resource/res_list&verify=1&id=open1419316518&token=b9f33b5f52d1472c4284e89522ce9222fb4ea10b&lang=zh_CN
     */

    @Override
    public WXUserInfo getWXUserInfo(String openId) {

        logger.info("获取微信用户信息!");
        String urlGetUserInfo = String.format(URL_GET_USERINFO, openId);

        logger.info("获取微信用户信息url: {} ", urlGetUserInfo);

        String response = getRestTemplate().getForObject(urlGetUserInfo, String.class);

        logger.info("获取微信用户信息: {} ", response);

        if (StringUtils.contains(response, "errcode")) {
            return null;
        }

        WXUserInfo profile = null;
        try {
            profile = objectMapper.readValue(response, WXUserInfo.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return profile;
    }


}

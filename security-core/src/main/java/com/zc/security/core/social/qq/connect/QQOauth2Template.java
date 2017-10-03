package com.zc.security.core.social.qq.connect;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Template;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;

/**
 * 说明 . <br>
 * <p>
 * <p>
 * Copyright: Copyright (c) 2017/10/03 上午10:52
 * <p>
 * Company: 百趣
 * <p>
 *
 * @author zhongcheng_m@yeah.net
 * @version 1.0.0
 */
public class QQOauth2Template extends OAuth2Template {

    private Logger logger = LoggerFactory.getLogger(getClass());

    public QQOauth2Template(String clientId, String clientSecret, String authorizeUrl, String accessTokenUrl) {
        super(clientId, clientSecret, authorizeUrl, accessTokenUrl);
        setUseParametersForClientAuthentication(true);
    }

    public QQOauth2Template(String clientId, String clientSecret, String authorizeUrl, String authenticateUrl, String accessTokenUrl) {
        super(clientId, clientSecret, authorizeUrl, authenticateUrl, accessTokenUrl);
        setUseParametersForClientAuthentication(true);
    }


    @Override
    protected RestTemplate createRestTemplate() {
        RestTemplate restTemplate = super.createRestTemplate();
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter(Charset.forName("UTF-8")));

        return restTemplate;
    }

    @Override
    protected AccessGrant postForAccessGrant(String accessTokenUrl, MultiValueMap<String, String> parameters) {


        /**
         * qq文档地址
         * http://wiki.connect.qq.com/使用authorization_code获取access_token
         */

        String response = getRestTemplate().patchForObject(accessTokenUrl, parameters, String.class);

        logger.info(" Get access token response : {} ", response);


        //access_token=FE04************************CCE2&expires_in=7776000&refresh_token=88E4************************BE14
        String[] item = StringUtils.splitByWholeSeparatorPreserveAllTokens(response, "&");

        //授权令牌，Access_Token。
        String accessToken = StringUtils.substringAfterLast(item[0], "=");
        //该access token的有效期，单位为秒。
        Long exprienIn = Long.valueOf(StringUtils.substringAfterLast(item[1], "="));
        //在授权自动续期步骤中，获取新的Access_Token时需要提供的参数。
        String refreshToken = StringUtils.substringAfterLast(item[2], "=");


        return new AccessGrant(accessToken, null, refreshToken, exprienIn);
    }
}

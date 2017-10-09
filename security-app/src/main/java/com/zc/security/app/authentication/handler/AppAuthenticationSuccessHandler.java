package com.zc.security.app.authentication.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zc.security.core.properties.SecurityProperties;
import org.apache.commons.codec.binary.StringUtils;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.UnapprovedClientAuthenticationException;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登陆失败处理逻辑 . <br>
 * <p>
 * <p>
 * Copyright: Copyright (c) 2017/09/24 下午3:01
 * <p>
 * Company: 百趣
 * <p>
 *
 * @author zhongcheng_m@yeah.net
 * @version 1.0.0
 */
@Component()
public class AppAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SecurityProperties securityProperties;

    @Resource
    private ObjectMapper objectMapper;

    @SuppressWarnings("all")
    @Autowired
    private AuthorizationServerTokenServices tokenServices;


    @Autowired
    private ClientDetailsService clientDetailsService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {

        String header = request.getHeader("Authorization");

        if (header == null || !header.startsWith("Basic ")) {

            logger.info("请求投中不包含Basic认证信息");
            throw new UnapprovedClientAuthenticationException("请求投中无client信息");
        }
        //从请求头中获取clientId和clientSecret
        String[] tokens = extractAndDecodeHeader(header);
        assert tokens.length == 2;

        String clientId = tokens[0];
        String clientSecret = tokens[1];

        ClientDetails clientDetails = clientDetailsService.loadClientByClientId(clientId);

        if (clientDetails == null) {
            throw new UnapprovedClientAuthenticationException("clientId对应的配置信息不存在:" + clientId);
        } else if (!StringUtils.equals(clientDetails.getClientSecret(), clientSecret)) {
            throw new UnapprovedClientAuthenticationException("clientSecret不匹配:" + clientId);
        }


        TokenRequest tokenRequest = new TokenRequest(MapUtils.EMPTY_MAP, clientId, clientDetails.getScope(), "custom");
        OAuth2Request oAuth2Request = tokenRequest.createOAuth2Request(clientDetails);
        OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(oAuth2Request, authentication);
        OAuth2AccessToken oAuth2AccessToken = tokenServices.createAccessToken(oAuth2Authentication);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(oAuth2AccessToken));

    }


    /**
     * Decodes the header into a username and password.
     *
     * @throws BadCredentialsException if the Basic header is not present or is not valid
     *                                 Base64
     */
    private String[] extractAndDecodeHeader(String header)
            throws IOException {

        byte[] base64Token = header.substring(6).getBytes("UTF-8");
        byte[] decoded;
        try {
            decoded = Base64.decode(base64Token);
        } catch (IllegalArgumentException e) {
            throw new BadCredentialsException(
                    "Failed to decode basic authentication token");
        }

        String token = new String(decoded, "UTF-8");

        int delim = token.indexOf(":");

        if (delim == -1) {
            throw new BadCredentialsException("Invalid basic authentication token");
        }
        return new String[]{token.substring(0, delim), token.substring(delim + 1)};
    }


}

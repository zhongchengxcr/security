package com.zc.security.app.config.server;

import com.zc.security.core.properties.SecurityProperties;
import com.zc.security.core.properties.oauth.OAuth2ClientProperties;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.builders.InMemoryClientDetailsServiceBuilder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 认证服务器配置 . <br>
 * <p>
 * <p>
 * Copyright: Copyright (c) 2017/10/07 17:12
 * <p>
 * Company: 百趣
 * <p>
 *
 * @author zhongcheng_m@yeah.net
 * @version 1.0.0
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private TokenStore tokenStore;

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired(required = false)
    private JwtAccessTokenConverter jwtAccessTokenConverter;

    @Autowired(required = false)
    private TokenEnhancer jwtTokenEnhancer;


    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {

        endpoints.authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService)
                .tokenStore(tokenStore);

        if (jwtTokenEnhancer != null && jwtAccessTokenConverter != null) {
            List<TokenEnhancer> delegates = new ArrayList<>();
            delegates.add(jwtTokenEnhancer);
            delegates.add(jwtAccessTokenConverter);
            TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
            tokenEnhancerChain.setTokenEnhancers(delegates);

            endpoints.accessTokenConverter(jwtAccessTokenConverter)
                    .tokenEnhancer(tokenEnhancerChain);
        }

    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        InMemoryClientDetailsServiceBuilder builder = clients.inMemory();
        if (ArrayUtils.isNotEmpty(securityProperties.getOauth2().getClients())) {
            for (OAuth2ClientProperties client : securityProperties.getOauth2().getClients()) {
                builder.withClient(client.getClientId())
                        .secret(client.getClientSecret())
                        .authorizedGrantTypes("refresh_token", "authorization_code", "password")
                        .accessTokenValiditySeconds(client.getAccessTokenValidateSeconds())
                        .refreshTokenValiditySeconds(2592000)
                        .scopes("all");
            }
        }

    }

    /**
     * http://localhost:8060/oauth/authorize?response_type=code&client_id=zc&redirect_uri=http://www.pinzhi365.com&scope=all
     */

    /**
     * {
     "access_token": "fad8d829-1298-4b94-aec7-723f00b99d0f",
     "token_type": "bearer",
     "refresh_token": "b7502b90-00ab-4820-8c01-f9f7008c9cf9",
     "expires_in": 43092,
     "scope": "all"
     }
     */


    /**
     * https://tools.ietf.org/html/rfc6749#section-4.1
     *
     * 授权码模式
     * 4.1.1.  Authorization Request
     * 4.1.3.  Access Token Request
     *
     *
     * 密码模式
     * 4.3.2.  Access Token Request
     *
     * {
     "access_token": "fad8d829-1298-4b94-aec7-723f00b99d0f",
     "token_type": "bearer",
     "refresh_token": "b7502b90-00ab-4820-8c01-f9f7008c9cf9",
     "expires_in": 42926,
     "scope": "all"
     }
     *
     */


    /**
     * request
     *
     * POST /oauth/token HTTP/1.1
     Host: localhost:8060
     Content-Type: application/x-www-form-urlencoded
     Authorization: Basic emM6emNz
     Cache-Control: no-cache
     Postman-Token: aea29310-ea17-1c78-e77b-a2218f1aa53f

     grant_type=password&scope=all&username=zhongc&password=123456

     */
}

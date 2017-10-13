package com.zc.security.app.config.store;

import com.zc.security.app.jwt.JwtTokenEnhancer;
import com.zc.security.core.properties.SecurityProperties;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import sun.tools.jstat.Token;

/**
 * 说明 . <br>
 * <p>
 * <p>
 * Copyright: Copyright (c) 2017/10/12 下午9:22
 * <p>
 * Company: 百趣
 * <p>
 *
 * @author zhongcheng_m@yeah.net
 * @version 1.0.0
 */
@Configuration
public class TokenStoreConfig {


    @ConditionalOnProperty(prefix = "zc.security.oauth2", name = "tokenStore", havingValue = "redis")
    @Configuration
    public static class RedisTokenStoreConfig {

        @Autowired
        private RedisConnectionFactory redisConnectionFactory;


        /**
         * 使用redis存储token的配置，只有在zc.security.oauth2.tokenStore配置为redis时生效
         *
         * @author zhailiang
         */

        @Bean
        public TokenStore redisTokenStore() {
            return new RedisTokenStore(redisConnectionFactory);
        }

    }

    @ConditionalOnProperty(prefix = "zc.security.oauth2", name = "tokenStore", havingValue = "jwt", matchIfMissing = true)
    @Configuration
    public static class JwtTokenStoreConfig {

        @Autowired
        private SecurityProperties securityProperties;

        @Bean
        public TokenStore jwtTokenStore() {
            return new JwtTokenStore(jwtAccessTokenConverter());
        }


        @Bean
        public JwtAccessTokenConverter jwtAccessTokenConverter() {
            JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
            jwtAccessTokenConverter.setSigningKey(securityProperties.getOauth2().getJwtSigningKey());
            return jwtAccessTokenConverter;
        }


        @ConditionalOnBean(TokenEnhancer.class)
        @Bean
        public TokenEnhancer jwtTokenEnhancer() {
            return new JwtTokenEnhancer();
        }


    }


}

package com.zc.security.util;

import com.zc.security.dto.UserAuthentication;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.exceptions.UnauthorizedUserException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;

import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.Set;

/**
 * 说明 . <br>
 * <p>
 * <p>
 * Copyright: Copyright (c) 2017/10/13 下午9:34
 * <p>
 * Company: 百趣
 * <p>
 *
 * @author zhongcheng_m@yeah.net
 * @version 1.0.0
 */
public class AuthenticationUtils {


    //test

    /**
     * test
     *
     * @return
     */
    public static UserAuthentication getUserDetails() throws UnsupportedEncodingException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String jwt = null;
        if (authentication != null) {
            OAuth2Authentication oAuth2Authentication = (OAuth2Authentication) authentication;
            OAuth2AuthenticationDetails oAuth2AuthenticationDetails = (OAuth2AuthenticationDetails) oAuth2Authentication.getDetails();
            String clientId = oAuth2Authentication.getOAuth2Request().getClientId();
            String userName = oAuth2Authentication.getName();
            Set<String> scopes = oAuth2Authentication.getOAuth2Request().getScope();
            Collection<GrantedAuthority> authenticats = oAuth2Authentication.getAuthorities();
            jwt = oAuth2AuthenticationDetails.getTokenValue();
            UserAuthentication userAuthentication = new UserAuthentication();
            userAuthentication.setClient_id(clientId);
            userAuthentication.setUserName(userName);
            userAuthentication.setScopes(scopes);

            Claims claims = Jwts.parser() .setSigningKey("zhongc".getBytes("utf-8")).parseClaimsJws(jwt).getBody();

            String company = (String) claims.get("company");
            userAuthentication.setAuthorities(authenticats);

            userAuthentication.setCompany(company);
            return userAuthentication;
        } else {
            throw new UnauthorizedUserException("未登录");
        }

    }
}

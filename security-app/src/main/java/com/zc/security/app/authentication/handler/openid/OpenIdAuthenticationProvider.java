package com.zc.security.app.authentication.handler.openid;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;

import java.util.HashSet;
import java.util.Set;

/**
 * 说明 . <br>
 * <p>
 * <p>
 * Copyright: Copyright (c) 2017/10/09 下午10:00
 * <p>
 * Company: 百趣
 * <p>
 *
 * @author zhongcheng_m@yeah.net
 * @version 1.0.0
 */
public class OpenIdAuthenticationProvider implements AuthenticationProvider {

    private SocialUserDetailsService userDetailsService;

    private UsersConnectionRepository usersConnectionRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        OpenIdAuthenticationToken authenticationToken = (OpenIdAuthenticationToken) authentication;


        String providerId = authenticationToken.getProviderId();
        String openId = authenticationToken.getPrincipal();

        Set<String> providerUserIds = new HashSet<>();

        providerUserIds.add(openId);
        Set<String> userIds = usersConnectionRepository.findUserIdsConnectedTo(providerId, providerUserIds);

        if (userIds == null || userIds.size() < 1) {
            throw new InternalAuthenticationServiceException("无法获取用户信息");
        }


        String userId = userIds.iterator().next();

        //根据userId查询用户信息
        SocialUserDetails user = userDetailsService.loadUserByUserId(userId);

        if (user == null) {
            throw new UsernameNotFoundException("无法获取用户信息");
        }

        OpenIdAuthenticationToken openIdAuthenticationToken = new OpenIdAuthenticationToken(openId, providerId, user.getAuthorities());

        openIdAuthenticationToken.setDetails(authenticationToken.getDetails());


        return openIdAuthenticationToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return OpenIdAuthenticationProvider.class.isAssignableFrom(authentication);
    }


    public SocialUserDetailsService getUserDetailsService() {
        return userDetailsService;
    }

    public void setUserDetailsService(SocialUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    public UsersConnectionRepository getUsersConnectionRepository() {
        return usersConnectionRepository;
    }

    public void setUsersConnectionRepository(UsersConnectionRepository usersConnectionRepository) {
        this.usersConnectionRepository = usersConnectionRepository;
    }
}

package com.zc.security.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


@Component
public class MyUserDetailService implements UserDetailsService {


    Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        boolean enabled = true;  //用户已失效 ,不验证密码  2

        boolean accountNonExpired = true;  //用户帐号已过期 . 不验证密码 3

        boolean credentialsNonExpired = true;  //用户凭证已过期 ,验证密码  4

        boolean accountNonLocked = true; //用户帐号已被锁定. 不验证密码  1


        String password = "123456";

        password = passwordEncoder.encode(password);

        logger.info("加密后的密码>>" + password);

        User u = new User(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));

        return u;
    }
}

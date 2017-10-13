package com.zc.security.user;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 说明 . <br>
 * <p>
 * <p>
 * Copyright: Copyright (c) 2017/10/13 下午11:32
 * <p>
 * Company: 百趣
 * <p>
 *
 * @author zhongcheng_m@yeah.net
 * @version 1.0.0
 */
@Component
public class MyUserDetailsService implements UserDetailsService {

    @Resource
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        boolean enabled = true;  //用户已失效 ,不验证密码  2

        boolean accountNonExpired = true;  //用户帐号已过期 . 不验证密码 3

        boolean credentialsNonExpired = true;  //用户凭证已过期 ,验证密码  4

        boolean accountNonLocked = true; //用户帐号已被锁定. 不验证密码  1


        String password = "123456";

        User u = new User(username, passwordEncoder.encode(password), enabled, accountNonExpired
                , credentialsNonExpired, accountNonLocked
                , AuthorityUtils.commaSeparatedStringToAuthorityList("admin,ROLE_USER"));

        return u;
    }

}

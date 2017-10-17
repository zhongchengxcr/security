package com.zc.security.security;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * 说明 . <br>
 * <p>
 * <p>
 * Copyright: Copyright (c) 2017/09/27 下午5:02
 * <p>
 * Company: 百趣
 * <p>
 *
 * @author zhongcheng_m@yeah.net
 * @version 1.0.0
 */
//@Component("smsUserDetailsService")
@SuppressWarnings("ALL")
public class SmsUserDetailsService implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        boolean enabled = true;  //用户已失效 ,不验证密码  2

        boolean accountNonExpired = true;  //用户帐号已过期 . 不验证密码 3

        boolean credentialsNonExpired = true;  //用户凭证已过期 ,验证密码  4

        boolean accountNonLocked = true; //用户帐号已被锁定. 不验证密码  1


        String password = "123456";

        User u = new User(username, password, enabled, accountNonExpired
                , credentialsNonExpired, accountNonLocked
                , AuthorityUtils.commaSeparatedStringToAuthorityList("admin,ROLE_USER"));

        return u;
    }
}

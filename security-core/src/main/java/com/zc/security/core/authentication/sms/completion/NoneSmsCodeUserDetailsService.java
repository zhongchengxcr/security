package com.zc.security.core.authentication.sms.completion;

import com.zc.security.core.authentication.sms.SmsCodeUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * 说明 . <br>
 * 默认的短信验证service,返回用户输入的信息
 * <p>
 * Copyright: Copyright (c) 2017/09/28 下午2:41
 * <p>
 * Company: 百趣
 * <p>
 *
 * @author zhongcheng_m@yeah.net
 * @version 1.0.0
 */
public class NoneSmsCodeUserDetailsService implements SmsCodeUserDetailsService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public UserDetails loadUserByMobile(String mobile) throws UsernameNotFoundException {

        logger.info("默认的 SmsCodeUserDetailsService,在使用时 请根据业务逻辑自定义. ");

        logger.info("登录使用的手机号码  : " + mobile);

        boolean enabled = true;  //用户已失效 ,不验证密码  2

        boolean accountNonExpired = true;  //用户帐号已过期 . 不验证密码 3

        boolean credentialsNonExpired = true;  //用户凭证已过期 ,验证密码  4

        boolean accountNonLocked = true; //用户帐号已被锁定. 不验证密码  1

        String password = "123456";

        User u = new User("zhongc", password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));

        return u;
    }
}

package com.zc.security.security;

import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;

/**
 * 说明 . <br>
 * <p>
 * <p>
 * Copyright: Copyright (c) 2017/10/03 15:06
 * <p>
 * Company: 百趣
 * <p>
 *
 * @author zhongcheng_m@yeah.net
 * @version 1.0.0
 */
public class DemoConnectSignUp implements ConnectionSignUp {


    /**
     * 用户可以实现 ConnectionSignUp 接口  来实现默认注册逻辑
     */

    /**
     * @param connection
     * @return userId
     */
    @Override
    public String execute(Connection<?> connection) {

        /**
         * 注册逻辑
         */


        connection.getDisplayName();
        connection.getImageUrl();
        connection.getKey().getProviderId();
        connection.getKey().getProviderUserId();

        return "userId";
    }


}

package com.zc.security.core.social.wx.api;

/**
 * 说明 . <br>
 * <p>
 * <p>
 * Copyright: Copyright (c) 2017/10/03 18:03
 * <p>
 * Company: 百趣
 * <p>
 *
 * @author zhongcheng_m@yeah.net
 * @version 1.0.0
 */
public interface WXApi {

    /**
     * 获取微信登录用户信息
     *
     * @return
     */
    WXUserInfo getWXUserInfo(String openId);
}

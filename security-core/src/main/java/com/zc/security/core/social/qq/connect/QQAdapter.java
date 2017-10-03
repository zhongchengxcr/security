package com.zc.security.core.social.qq.connect;

import com.zc.security.core.social.qq.api.QQApi;
import com.zc.security.core.social.qq.api.QQUserInfo;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;

/**
 * 说明 . <br>
 * <p>
 * <p>
 * Copyright: Copyright (c) 2017/09/30 下午3:48
 * <p>
 * Company: 百趣
 * <p>
 *
 * @author zhongcheng_m@yeah.net
 * @version 1.0.0
 */
public class QQAdapter implements ApiAdapter<QQApi> {

    /**
     * 测试当前api是否可用
     * @param api
     * @return
     */
    @Override
    public boolean test(QQApi api) {
        return true;
    }

    /**
     * 将ConnectionValues需要的数据复制
     * @param api
     * @param values
     */
    @Override
    public void setConnectionValues(QQApi api, ConnectionValues values) {
        //拿到qq的用户信息
        QQUserInfo qqUserInfo = api.getQQUserInfo();

        values.setDisplayName(qqUserInfo.getNickname());
        //头像
        values.setImageUrl(qqUserInfo.getFigureurl_qq_1());
        //个人主页,类似微博的主页  qq没有
        values.setProfileUrl("");
        //openId
        values.setProviderUserId(qqUserInfo.getOpenId());
    }

    @Override
    public UserProfile fetchUserProfile(QQApi api) {
        return null;
    }

    /**
     * 发主页状态,类似微博发状态 , qq没有
     * @param api
     * @param message
     */
    @Override
    public void updateStatus(QQApi api, String message) {

    }
}

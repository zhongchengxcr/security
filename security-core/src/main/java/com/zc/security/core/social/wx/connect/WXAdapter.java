package com.zc.security.core.social.wx.connect;

import com.zc.security.core.social.wx.api.WXApi;
import com.zc.security.core.social.wx.api.WXUserInfo;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;

/**
 * 说明 . <br>
 * <p>
 * <p>
 * Copyright: Copyright (c) 2017/10/03 18:50
 * <p>
 * Company: 百趣
 * <p>
 *
 * @author zhongcheng_m@yeah.net
 * @version 1.0.0
 */
public class WXAdapter implements ApiAdapter<WXApi> {


    private String openId;

    public WXAdapter() {
    }

    public WXAdapter(String openId) {
        this.openId = openId;
    }

    /**
     * 测试当前api是否可用
     *
     * @param api
     * @return
     */
    @Override
    public boolean test(WXApi api) {
        return true;
    }

    /**
     * 将ConnectionValues需要的数据复制
     *
     * @param api
     * @param values
     */
    @Override
    public void setConnectionValues(WXApi api, ConnectionValues values) {

        WXUserInfo wxUserInfo = api.getWXUserInfo(openId);

        values.setDisplayName(wxUserInfo.getNickname());
        //头像
        values.setImageUrl(wxUserInfo.getHeadimgurl());
        //个人主页,类似微博的主页  wx没有
        values.setProfileUrl("");
        //openId
        values.setProviderUserId(wxUserInfo.getOpenid());


    }

    @Override
    public UserProfile fetchUserProfile(WXApi api) {
        return null;
    }

    /**
     * 发主页状态,类似微博发状态 , qq没有
     *
     * @param api
     * @param message
     */
    @Override
    public void updateStatus(WXApi api, String message) {

    }


    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }
}

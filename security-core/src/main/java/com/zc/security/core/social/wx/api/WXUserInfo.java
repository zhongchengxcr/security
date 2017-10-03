package com.zc.security.core.social.wx.api;

import java.util.Arrays;

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
public class WXUserInfo {

    /**
     * 文档地址
     * https://open.weixin.qq.com/cgi-bin/showdocument?action=dir_list&t=resource/res_list&verify=1&id=open1419316518&token=b9f33b5f52d1472c4284e89522ce9222fb4ea10b&lang=zh_CN
     */

    //普通用户的标识，对当前开发者帐号唯一
    private String openid;
    //普通用户昵称
    private String nickname;
    //普通用户性别，1为男性，2为女性
    private int sex;

    //普通用户个人资料填写的省份
    private String province;

    //普通用户个人资料填写的城市
    private String city;

    //国家，如中国为CN
    private String country;

    //用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空
    private String headimgurl;
    //用户特权信息，json数组，如微信沃卡用户为（chinaunicom）
    private String[] privilege;


    /**
     * 开发者最好保存用户unionID信息，以便以后在不同应用中进行用户信息互通。
     */
    //用户统一标识。针对一个微信开放平台帐号下的应用，同一用户的unionid是唯一的。
    private String unionid;


    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getHeadimgurl() {
        return headimgurl;
    }

    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
    }

    public String[] getPrivilege() {
        return privilege;
    }

    public void setPrivilege(String[] privilege) {
        this.privilege = privilege;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("WXUserInfo{");
        sb.append("openid='").append(openid).append('\'');
        sb.append(", nickname='").append(nickname).append('\'');
        sb.append(", sex=").append(sex);
        sb.append(", province='").append(province).append('\'');
        sb.append(", city='").append(city).append('\'');
        sb.append(", country='").append(country).append('\'');
        sb.append(", headimgurl='").append(headimgurl).append('\'');
        sb.append(", privilege=").append(Arrays.toString(privilege));
        sb.append(", unionid='").append(unionid).append('\'');
        sb.append('}');
        return sb.toString();
    }
}

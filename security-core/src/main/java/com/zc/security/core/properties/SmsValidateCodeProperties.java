package com.zc.security.core.properties;

/**
 * 说明 . <br>
 * 短信验证码配置
 * <p>
 * Copyright: Copyright (c) 2017/09/27 上午9:23
 * <p>
 * Company: 百趣
 * <p>
 *
 * @author zhongcheng_m@yeah.net
 * @version 1.0.0
 */
public class SmsValidateCodeProperties {

    //长度
    private int length = 6;


    //过期时间
    private int expireIn = 60;

    private String url;


    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getExpireIn() {
        return expireIn;
    }

    public void setExpireIn(int expireIn) {
        this.expireIn = expireIn;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

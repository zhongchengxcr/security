package com.zc.security.core.properties.social;

/**
 * 属性配置 . <br>
 * <p>
 * <p>
 * Copyright: Copyright (c) 2017/09/24 下午3:01
 * <p>
 * Company: 百趣
 * <p>
 *
 * @author zhongcheng_m@yeah.net
 * @version 1.0.0
 */
public class SocialProperties {

    /**
     * 社交登录功能拦截的url
     */
    private String filterProcessesUrl = "/auth";

    private QQProperties qq = new QQProperties();

    private WXProperties wx = new WXProperties();


    public QQProperties getQq() {
        return qq;
    }

    public void setQq(QQProperties qq) {
        this.qq = qq;
    }

    public String getFilterProcessesUrl() {
        return filterProcessesUrl;
    }

    public void setFilterProcessesUrl(String filterProcessesUrl) {
        this.filterProcessesUrl = filterProcessesUrl;
    }

    public WXProperties getWx() {
        return wx;
    }

    public void setWx(WXProperties wx) {
        this.wx = wx;
    }
}

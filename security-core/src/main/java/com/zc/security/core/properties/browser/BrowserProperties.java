package com.zc.security.core.properties.browser;

import com.zc.security.core.SecurityConstants;

/**
 * 浏览器权限部分属性配置 . <br>
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
public class BrowserProperties {

    //默认的登录页面
    private String loginPage = SecurityConstants.DEFAULT_LOGIN_PAGE_URL;

    private LoginResponseType loginType = LoginResponseType.JSON;

    private String signUpUrl = "/sign_up.html";

    private String signOutUrl = "";

    private String sessionTimeoutUrl = "/session/invalid";

    /**
     * 单个账号最大session个数
     */
    private int maximumSessions = 1;

    private int rememberMeSeconds = 3600;

    /**
     * 当session达到最大个数时是否允许登录
     */
    private boolean maxSessionsPreventsLogin = true;


    public String getLoginPage() {
        return loginPage;
    }

    public void setLoginPage(String loginPage) {
        this.loginPage = loginPage;
    }

    public LoginResponseType getLoginType() {
        return loginType;
    }

    public void setLoginType(LoginResponseType loginType) {
        this.loginType = loginType;
    }

    public int getRememberMeSeconds() {
        return rememberMeSeconds;
    }

    public void setRememberMeSeconds(int rememberMeSeconds) {
        this.rememberMeSeconds = rememberMeSeconds;
    }


    public String getSignUpUrl() {
        return signUpUrl;
    }

    public void setSignUpUrl(String signUpUrl) {
        this.signUpUrl = signUpUrl;
    }

    public String getSessionTimeoutUrl() {
        return sessionTimeoutUrl;
    }

    public void setSessionTimeoutUrl(String sessionTimeoutUrl) {
        this.sessionTimeoutUrl = sessionTimeoutUrl;
    }

    public int getMaximumSessions() {
        return maximumSessions;
    }

    public void setMaximumSessions(int maximumSessions) {
        this.maximumSessions = maximumSessions;
    }

    public boolean getMaxSessionsPreventsLogin() {
        return maxSessionsPreventsLogin;
    }

    public void setMaxSessionsPreventsLogin(boolean maxSessionsPreventsLogin) {
        this.maxSessionsPreventsLogin = maxSessionsPreventsLogin;
    }

    public String getSignOutUrl() {
        return signOutUrl;
    }

    public void setSignOutUrl(String signOutUrl) {
        this.signOutUrl = signOutUrl;
    }

}

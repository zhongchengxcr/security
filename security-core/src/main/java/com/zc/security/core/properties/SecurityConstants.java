package com.zc.security.core.properties;

public interface SecurityConstants {


    /**
     * 当请求需要身份认证时，默认跳转的url
     *
     */
    String DEFAULT_UNAUTHENTICATION_URL = "/authentication/require";


    /**
     * 默认的用户名密码登录请求处理url
     */
    String DEFAULT_LOGIN_PROCESSING_URL_FORM = "/authentication/form";



    String DEFAULT_LOGIN_PAGE_URL = "/error/login.html";

}

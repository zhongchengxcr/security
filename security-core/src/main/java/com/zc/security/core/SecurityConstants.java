package com.zc.security.core;


/**
 * 常量 . <br>
 * <p>
 * <p>
 * Copyright: Copyright (c) 2017/09/24 下午3:27
 * <p>
 * Company: 百趣
 * <p>
 *
 * @author zhongcheng_m@yeah.net
 * @version 1.0.0
 */
public interface SecurityConstants {

    /**
     * 创建验证码url前缀
     */
    String DEFAULT_VALIDATE_CODE_URL_PREFIX = "/validate/code/";


    String IMAGE_CODE_SESSION_KEY = "IMAGE_CODE_SESSION_KEY";

    /**
     * 验证图片验证码时，http请求中默认的携带图片验证码信息的参数的名称
     */
    String DEFAULT_PARAMETER_NAME_CODE_IMAGE = "imageCode";
    /**
     * 验证短信验证码时，http请求中默认的携带短信验证码信息的参数的名称
     */
    String DEFAULT_PARAMETER_NAME_CODE_SMS = "smsCode";

    /**
     * 发送短信验证码 或 验证短信验证码时，传递手机号的参数的名称
     */
    String DEFAULT_PARAMETER_NAME_MOBILE = "mobile";

    /**
     * 当请求需要身份认证时，默认跳转的url
     */
    String DEFAULT_UNAUTHENTICATION_URL = "/authentication/require";

    /**
     * 当请求需要身份认证时，默认跳转的url
     */
    String DEFAULT_AUTHENTICATION_ME = "/authentication/me";

    /**
     * 默认的用户名密码登录请求处理url
     */
    String DEFAULT_LOGIN_PROCESSING_URL_FORM = "/authentication/form";

    String DEFAULT_LOGIN_PROCESSING_URL = "/authentication/*";

    /**
     * 默认的用户名密码登录请求处理url
     */
    String DEFAULT_LOGIN_PROCESSING_URL_SMS = "/authentication/mobile";

    /**
     * 默认获取图片验证码的url
     */
    String DEFAULT_VALIDATE_PROCESSING_URL_IMAGE = "/validate/code/image";

    /**
     * 默认获取短信验证码的url
     */
    String DEFAULT_VALIDATE_PROCESSING_URL_SMS = "/validate/code/sms";


    String DEFAULT_LOGIN_PAGE_URL = "/error/login.html";
}

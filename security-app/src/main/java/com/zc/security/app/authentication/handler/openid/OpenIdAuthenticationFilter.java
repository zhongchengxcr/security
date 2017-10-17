package com.zc.security.app.authentication.handler.openid;

import com.zc.security.core.SecurityConstants;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.security.SocialAuthenticationServiceLocator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Set;

/**
 * 说明 . <br>
 * <p>
 * <p>
 * Copyright: Copyright (c) 2017/10/09 下午9:53
 * <p>
 * Company: 百趣
 * <p>
 *
 * @author zhongcheng_m@yeah.net
 * @version 1.0.0
 */
public class OpenIdAuthenticationFilter extends AbstractAuthenticationProcessingFilter {


    private String openIdParameter = SecurityConstants.DEFAULT_PARAMETER_NAME_OPENID;
    private String providerIdParameter = SecurityConstants.DEFAULT_PARAMETER_NAME_PROVIDERID;
    private String codeParameter = SecurityConstants.DEFAULT_PARAMETER_NAME_CODE;
    private boolean postOnly = true;

    private SocialAuthenticationServiceLocator authServiceLocator;

    // ~ Constructors
    // ===================================================================================================

    public OpenIdAuthenticationFilter(SocialAuthenticationServiceLocator authServiceLocator) {
        super(new AntPathRequestMatcher(SecurityConstants.DEFAULT_SIGN_IN_PROCESSING_URL_OPENID, "POST"));
        this.authServiceLocator = authServiceLocator;
    }

    // ~ Methods
    // ========================================================================================================

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        if (postOnly &&"POST".equals(request.getMethod())) {
            throw new AuthenticationServiceException(
                    "Authentication method not supported: " + request.getMethod());
        }


        /**
         * TODO
         *
         * 改造
         * 目前大多数移动设备使用社交登录的时候 通常会嵌入第三方应用的SDK
         *
         * 第三方的SDK通常提供获取用户信息的api(微信,qq)
         *
         * 所以获取用户信息以及openid或者token交给移动设备去做,然后传到服务端
         *
         *
         */
        String openId;

        String providerId = obtainPassword(request);
        String code = obtainCode(request);

        if (StringUtils.isEmpty(providerId)) {
            throw new AuthenticationServiceException("providerId 不能为空");

        }

        if (StringUtils.isEmpty(code)) {
            openId = obtainUsername(request);
        } else {
            openId = getOpenIdByCode(providerId, code);
        }

        if (StringUtils.isEmpty(openId)) {
            throw new AuthenticationServiceException("无法获取openId");
        }


        providerId = providerId.trim();
        openId = openId.trim();

        OpenIdAuthenticationToken authRequest = new OpenIdAuthenticationToken(openId, providerId);

        // Allow subclasses to set the "details" property
        setDetails(request, authRequest);

        return this.getAuthenticationManager().authenticate(authRequest);
    }


    public String getOpenIdByCode(String providerId, String code) {

        Set<String> authProviders = authServiceLocator.registeredAuthenticationProviderIds();


        if (!authProviders.contains(providerId)) {
            throw new AuthenticationServiceException("无效的providerId");
        }

        OAuth2ConnectionFactory connectionFactory = (OAuth2ConnectionFactory) authServiceLocator.getConnectionFactory(providerId);

        AccessGrant accessGrant = connectionFactory.getOAuthOperations().exchangeForAccess(code, null, null);

        if (accessGrant == null) {
            throw new AuthenticationServiceException("获取accessGrant失败");
        }

        return accessGrant.getAccessToken();
    }

    /**
     * Enables subclasses to override the composition of the password, such as by
     * including additional values and a separator.
     * <p>
     * This might be used for example if a postcode/zipcode was required in addition to
     * the password. A delimiter such as a pipe (|) should be used to separate the
     * password and extended value(s). The <code>AuthenticationDao</code> will need to
     * generate the expected password in a corresponding manner.
     * </p>
     *
     * @param request so that request attributes can be retrieved
     * @return the password that will be presented in the <code>Authentication</code>
     * request token to the <code>AuthenticationManager</code>
     */


    private String obtainCode(HttpServletRequest request) {
        String code = request.getParameter(codeParameter);
        return code;
    }

    protected String obtainPassword(HttpServletRequest request) {
        return request.getParameter(providerIdParameter);
    }

    /**
     * Enables subclasses to override the composition of the username, such as by
     * including additional values and a separator.
     *
     * @param request so that request attributes can be retrieved
     * @return the username that will be presented in the <code>Authentication</code>
     * request token to the <code>AuthenticationManager</code>
     */
    protected String obtainUsername(HttpServletRequest request) {
        return request.getParameter(openIdParameter);
    }

    /**
     * Provided so that subclasses may configure what is put into the authentication
     * request's details property.
     *
     * @param request     that an authentication request is being created for
     * @param authRequest the authentication request object that should have its details
     *                    set
     */
    protected void setDetails(HttpServletRequest request,
                              OpenIdAuthenticationToken authRequest) {
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }


    /**
     * Sets the parameter name which will be used to obtain the username from the login
     * request.
     *
     * @param openIdParameter the parameter name. Defaults to "username".
     */
    public void setOpenIdParameter(String openIdParameter) {
        this.openIdParameter = openIdParameter;
    }

    /**
     * Sets the parameter name which will be used to obtain the password from the login
     * request..
     *
     * @param providerIdParameter the parameter name. Defaults to "password".
     */
    public void setProviderIdParameter(String providerIdParameter) {
        this.providerIdParameter = providerIdParameter;
    }


    /**
     * Defines whether only HTTP POST requests will be allowed by this filter. If set to
     * true, and an authentication request is received which is not a POST request, an
     * exception will be raised immediately and authentication will not be attempted. The
     * <tt>unsuccessfulAuthentication()</tt> method will be called as if handling a failed
     * authentication.
     * <p>
     * Defaults to <tt>true</tt> but may be overridden by subclasses.
     */
    public void setPostOnly(boolean postOnly) {
        this.postOnly = postOnly;
    }


    public String getOpenIdParameter() {
        return openIdParameter;
    }

    public String getProviderIdParameter() {
        return providerIdParameter;
    }


}

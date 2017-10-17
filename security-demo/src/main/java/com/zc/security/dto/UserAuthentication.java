package com.zc.security.dto;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Set;

/**
 * 说明 . <br>
 * <p>
 * <p>
 * Copyright: Copyright (c) 2017/10/13 下午9:44
 * <p>
 * Company: 百趣
 * <p>
 *
 * @author zhongcheng_m@yeah.net
 * @version 1.0.0
 */
@SuppressWarnings("ALL")
public class UserAuthentication {

    private String userName;

    private Set<String> scopes;

    private String company;

    private long exp;

    private Collection<GrantedAuthority> authorities;

    private String jti;

    private String client_id;


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Set<String> getScopes() {
        return scopes;
    }

    public void setScopes(Set<String> scopes) {
        this.scopes = scopes;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public long getExp() {
        return exp;
    }

    public void setExp(long exp) {
        this.exp = exp;
    }

    public Collection<GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Collection<GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    public String getJti() {
        return jti;
    }

    public void setJti(String jti) {
        this.jti = jti;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("UserAuthentication{");
        sb.append("userName='").append(userName).append('\'');
        sb.append(", scopes=").append(scopes);
        sb.append(", company='").append(company).append('\'');
        sb.append(", exp=").append(exp);
        sb.append(", authorities=").append(authorities);
        sb.append(", jti='").append(jti).append('\'');
        sb.append(", client_id='").append(client_id).append('\'');
        sb.append('}');
        return sb.toString();
    }
}

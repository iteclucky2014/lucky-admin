package com.lucky.admin.platform.security;

import java.util.Date;

public class AccessToken {
    private String access_token;
    private Date expired;

    public AccessToken() {
    }

    public AccessToken(String access_token, Date expired) {
        this.access_token = access_token;
        this.expired = expired;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public Date getExpired() {
        return expired;
    }

    public void setExpired(Date expired) {
        this.expired = expired;
    }
}

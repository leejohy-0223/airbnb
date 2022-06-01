package com.airbnb.domain.login;

public class KakaoToken implements OauthToken {
    private String access_token;
    private String token_type;
    private String refresh_token;
    private int expire_in;
    private String scope;
    private int refresh_token_expires_in;

    @Override
    public String getAccessToken() {
        return access_token;
    }
}

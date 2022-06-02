package com.airbnb.api.login.oauth.dto;

public class LoginResponse {
    private final String tokenType;
    private final String token;

    public LoginResponse(String tokenType, String token) {
        this.tokenType = tokenType;
        this.token = token;
    }

    public String getTokenType() {
        return tokenType;
    }

    public String getToken() {
        return token;
    }
}

package com.airbnb.domain.login;

public interface OauthToken {

    String getTokenType();

    String getAccessToken();

    String getAccessTokenHeader();
}

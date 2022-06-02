package com.airbnb.domain.login;

public interface OauthToken {
    String getAccessToken();

    String getAccessTokenHeader();
}

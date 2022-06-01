package com.airbnb.domain.login;

public interface OAuthServer {

    OauthToken getOAuthToken(String code);

    UserProfileDto getUserProfile(OauthToken oAuthToken);
}

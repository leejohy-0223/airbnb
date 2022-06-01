package com.airbnb.domain.login;

import com.airbnb.api.login.oauth.dto.UserProfileDto;

public interface OAuthServer {

    OauthToken getOAuthToken(String code);

    UserProfileDto getUserProfile(OauthToken oAuthToken);
}

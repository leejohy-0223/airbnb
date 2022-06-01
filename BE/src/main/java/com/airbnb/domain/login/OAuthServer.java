package com.airbnb.domain.login;

import com.airbnb.domain.login.dto.UserProfileDto;

public interface OAuthServer {

    OauthToken getOAuthToken(String code);

    UserProfileDto getUserProfile(OauthToken oAuthToken);
}

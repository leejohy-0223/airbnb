package com.airbnb.domain.login;

import org.springframework.stereotype.Component;

import com.airbnb.domain.login.dto.UserProfileDto;

@Component
public interface OAuthServer {

    OauthToken getOAuthToken(String code);

    UserProfileDto getUserProfile(OauthToken oAuthToken);
}

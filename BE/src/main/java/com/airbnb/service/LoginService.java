package com.airbnb.service;

import com.airbnb.api.login.oauth.dto.LoginResponse;
import com.airbnb.domain.login.UserProfileDto;
import com.airbnb.domain.login.*;
import com.airbnb.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class LoginService {

    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;
    private Map<String, OAuthServer> oAuthServerMap = new HashMap<>();

    public LoginService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.objectMapper = new ObjectMapper();
        oAuthServerMap.put("kakao", new KakaoOAuthServer());
        oAuthServerMap.put("github", new GitHubOAuthServer());
    }

    public LoginResponse login(String code, String vendor) {
        // vendor server 가져오기
        OAuthServer oAuthServer = oAuthServerMap.get(vendor);

        // oauthToken 받아오기
        OauthToken oAuthToken = oAuthServer.getOAuthToken(code);

        // oauthToken 안의 AccessToken을 통해 사용자 정보 받아오기
        UserProfileDto userProfileDto = oAuthServer.getUserProfile(oAuthToken);

        // userProfileDto로 User 만들기
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);


        // save
        // userRepository.save();

        return null;
    }
}

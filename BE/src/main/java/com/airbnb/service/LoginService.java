package com.airbnb.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.airbnb.api.login.oauth.dto.LoginResponse;
import com.airbnb.domain.login.GitHubOAuthServer;
import com.airbnb.domain.login.KakaoOAuthServer;
import com.airbnb.domain.login.OAuthServer;
import com.airbnb.domain.login.OauthToken;
import com.airbnb.domain.login.UserProfileDto;
import com.airbnb.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

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


        System.out.println(userProfileDto);
        // objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        // KakaoProfile kakaoProfile = objectMapper.readValue(userProfileDto, KakaoProfile.class);

        // save
        // userRepository.save();

        return null;
    }
}

package com.airbnb.service;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.airbnb.api.login.oauth.dto.LoginResponse;
import com.airbnb.domain.Role;
import com.airbnb.domain.User;
import com.airbnb.domain.login.OAuthServer;
import com.airbnb.domain.login.OauthToken;
import com.airbnb.domain.login.dto.UserProfileDto;
import com.airbnb.repository.UserRepository;
import com.airbnb.utils.oauth.JwtTokenProvider;

@Service
public class LoginService {

    private static final Logger log = LoggerFactory.getLogger(LoginService.class);

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final Map<String, OAuthServer> oAuthServerMap;

    public LoginService(UserRepository userRepository, JwtTokenProvider jwtTokenProvider, Map<String, OAuthServer> oAuthServerMap) {
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.oAuthServerMap = oAuthServerMap;
    }

    public LoginResponse login(String code, String vendor) {
        // vendor server 가져오기
        OAuthServer oAuthServer = oAuthServerMap.get(vendor);

        // oauthToken 받아오기
        OauthToken oAuthToken = oAuthServer.getOAuthToken(code);
        log.info("[oauthToken] : {}", oAuthToken.getAccessToken());

        // oauthToken 안의 AccessToken을 통해 사용자 정보 받아오기
        UserProfileDto userProfileDto = oAuthServer.getUserProfile(oAuthToken);
        log.info("[UserProfileDto] : {}", userProfileDto);
        String email = userProfileDto.getEmail();
        String userName = userProfileDto.getUserName();

        if (!userRepository.findUserByEmail(email).isPresent()) {
            User newUser = new User(userName, email, Role.GUEST);
            userRepository.save(newUser);
        }

        // id로 token 발급
        String jwtToken = jwtTokenProvider.createAccessToken(email);
        return new LoginResponse(oAuthToken.getTokenType(), jwtToken);
    }
}

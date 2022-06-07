package com.airbnb.domain.login.kakao;

import com.airbnb.domain.login.OAuthServer;
import com.airbnb.domain.login.OauthToken;
import com.airbnb.domain.login.dto.UserProfileDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Component(value = "kakao")
public class KakaoOAuthServer implements OAuthServer {

    private static final Logger log = LoggerFactory.getLogger(KakaoOAuthServer.class);

    private final String kakaoTokenServerUri;
    private final String kakaoOauthServerUri;
    private final String clientId;
    private final String redirectUri;

    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate;

    public KakaoOAuthServer(
        @Value("${jwt.token.kakao.token-server-uri}") String kakaoTokenServerUri,
        @Value("${jwt.token.kakao.oauth-server-uri}") String kakaoOauthServerUri,
        @Value("${jwt.token.kakao.client-id}") String clientId,
        @Value("${jwt.token.kakao.redirect-uri}") String redirectUri,
        ObjectMapper objectMapper, RestTemplate restTemplate) {
        this.kakaoTokenServerUri = kakaoTokenServerUri;
        this.kakaoOauthServerUri = kakaoOauthServerUri;
        this.clientId = clientId;
        this.redirectUri = redirectUri;
        this.objectMapper = objectMapper;
        this.restTemplate = restTemplate;
    }

    @Override
    public OauthToken getOAuthToken(String code) {
        HttpHeaders headers = createHeaders(null); // header 생성
        MultiValueMap<String, String> params = createParams(code); // 전달할 params

        // code 를 통해 Kakao 쪽으로 다시 Access Token을 받기 위한 요청부분
        HttpEntity<MultiValueMap<String, String>> accessTokenRequest = new HttpEntity<>(params, headers);

        ResponseEntity<String> response = restTemplate.exchange(
            kakaoTokenServerUri,
            HttpMethod.POST,
            accessTokenRequest,
            String.class
        );
        OauthToken oauthToken = null;
        try {
            oauthToken = objectMapper.readValue(response.getBody(), KakaoToken.class);
        } catch (JsonProcessingException e) {
            log.info("[ERROR] : getOauthToken 을 얻을수 없음");
        }
        return oauthToken;
    }

    @Override
    public UserProfileDto getUserProfile(OauthToken oAuthToken) {
        HttpHeaders headers = createHeaders(oAuthToken.getAccessTokenHeader()); // 헤더 만들기

        HttpEntity<MultiValueMap<String, String>> accessProfileRequest = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange( // 사용자 정보 반환받기
            kakaoOauthServerUri,
            HttpMethod.POST,
            accessProfileRequest,
            String.class
        );

        KakaoProfile kakaoProfile;

        try {
            kakaoProfile = objectMapper.readValue(response.getBody(), KakaoProfile.class);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("변환할 수 없는 User 입니다.");
        }

        Long id = kakaoProfile.getId();
        String email = kakaoProfile.kakaoAccount.getEmail();
        String username = kakaoProfile.kakaoAccount.profile.getNickname();
        return new UserProfileDto(id, email, username);
    }

    private MultiValueMap<String, String> createParams(String code) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", clientId);
        params.add("redirect_uri", redirectUri);
        params.add("code", code);
        return params;
    }

    private HttpHeaders createHeaders(String tokenHeader) {
        HttpHeaders headers = new HttpHeaders();
        if (tokenHeader != null) {
            headers.add("Authorization", tokenHeader);
        }
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
        return headers;
    }
}

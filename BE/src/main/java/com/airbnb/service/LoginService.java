package com.airbnb.service;

import com.airbnb.api.login.oauth.dto.LoginResponse;
import com.airbnb.api.login.oauth.dto.UserProfileDto;
import com.airbnb.domain.login.KakaoToken;
import com.airbnb.domain.login.OauthToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class LoginService {

    private static final String KAKAO_URI = "Https://kauth.kakao.com//oauth/token";
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public LoginService() {
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
    }

    public LoginResponse login(String code) {
        // oauthToken 받아오기
        OauthToken oauthToken = getAuthToken(code);

        // oauthToken 안의 AccessToken을 통해 사용자 정보 받아오기
        UserProfileDto userProfileDto = getUserProfile(oauthToken);

        // userProfileDto로 User 만들기
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        KakaoProfile kakaoProfile = objectMapper.readValue(userProfileDto, KakaoProfile.class);

        return null;
    }

    private OauthToken getAuthToken(String code) {
        HttpHeaders headers = createHeaders(null); // header 생성
        MultiValueMap<String, String> params = createParams(code); // 전달할 params

        // code 를 통해 Kakao 쪽으로 다시 Access Token을 받기 위한 요청부분
        HttpEntity<MultiValueMap<String, String>> accessTokenRequest = new HttpEntity<>(params, headers);

        ResponseEntity<KakaoToken> kakaoResponse = restTemplate.postForEntity(KAKAO_URI, accessTokenRequest, KakaoToken.class);
        return kakaoResponse.getBody();
    }

    private UserProfileDto getUserProfile(OauthToken token) {
        Map<String, Object> userProfileMap = getUserProfileFromOAuthServer(token)
                .orElseThrow(() -> new UserInformationNotFound("사용자 정보가 없습니다", HttpStatus.NOT_FOUND));

        return new UserProfileDto(
                String.valueOf(userProfileMap.get("id")),
                String.valueOf(userProfileMap.get("email")),
                String.valueOf(userProfileMap.get("login")));
    }

    private Optional<Map<String, Object>> getUserProfileFromOAuthServer(OauthToken token) {
        RequestEntity<Void> request = RequestEntity.get(userInfoUri)
                .header("Authorization", token.getAuthorizationValue())
                .accept(MediaType.APPLICATION_JSON)
                .build();

        ParameterizedTypeReference<HashMap<String, Object>> responseType = new ParameterizedTypeReference<>() {
        };

        Map<String, Object> userProfileMap = restTemplate.exchange(request, responseType).getBody();

        return Optional.ofNullable(userProfileMap);
    }

    private HttpHeaders createHeaders(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        if (accessToken != null) {
            headers.add("Authorization", "Bearer " + accessToken);
        }
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
        return headers;
    }

    private MultiValueMap<String, String> createParams(String code) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", "e274f62c505ebb4fe82d0feb387be030");
        params.add("redirect_uri", "http://localhost:8080/api/login/oauth/kakao/callback");
        params.add("code", code);
        return params;
    }
}

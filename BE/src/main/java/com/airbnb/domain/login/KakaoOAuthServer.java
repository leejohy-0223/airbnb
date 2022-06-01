package com.airbnb.domain.login;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.airbnb.api.login.oauth.dto.UserProfileDto;

public class KakaoOAuthServer extends OAuthServerImpl {

    private static final String KAKAO_URI = "Https://kauth.kakao.com//oauth/token";

    public KakaoOAuthServer(RestTemplate restTemplate) {
        super(restTemplate);
    }

    @Override
    public OauthToken getOAuthToken(String code) {
        HttpHeaders headers = createHeaders(null); // header 생성
        MultiValueMap<String, String> params = createParams(code); // 전달할 params

        // code 를 통해 Kakao 쪽으로 다시 Access Token을 받기 위한 요청부분
        HttpEntity<MultiValueMap<String, String>> accessTokenRequest = new HttpEntity<>(params, headers);

        ResponseEntity<KakaoToken> kakaoResponse = restTemplate.postForEntity(KAKAO_URI, accessTokenRequest,
            KakaoToken.class);
        return kakaoResponse.getBody();
    }

    @Override
    public UserProfileDto getUserProfile(OauthToken oAuthToken) {
        Map<String, Object> userProfileMap = getUserProfileFromOAuthServer(token)
            .orElseThrow(() -> new UserInformationNotFound("사용자 정보가 없습니다", HttpStatus.NOT_FOUND));

        return new UserProfileDto(
            String.valueOf(userProfileMap.get("id")),
            String.valueOf(userProfileMap.get("email")),
            String.valueOf(userProfileMap.get("login")));

    }

    private MultiValueMap<String, String> createParams(String code) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", "e274f62c505ebb4fe82d0feb387be030");
        params.add("redirect_uri", "http://localhost:8080/api/login/oauth/kakao/callback");
        params.add("code", code);
        return params;
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
}

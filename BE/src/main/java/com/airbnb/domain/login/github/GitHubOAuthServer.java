package com.airbnb.domain.login.github;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.airbnb.domain.login.OAuthServer;
import com.airbnb.domain.login.OauthToken;
import com.airbnb.domain.login.dto.UserProfileDto;

public class GitHubOAuthServer implements OAuthServer {

    private final String CLIENT_ID = "b7fe0a7f351bdafee25d";
    private final String CLIENT_SECRET = "";
    private final String ACCESS_TOKEN_URI = "https://github.com/login/oauth/access_token";
    private final String USER_INFO_URI = "https://api.github.com/user";

    protected final RestTemplate restTemplate = new RestTemplate();


    @Override
    public OauthToken getOAuthToken(String code) {
        MultiValueMap<String, String> requestPayloads = new LinkedMultiValueMap<>();
        Map<String, String> requestPayload = new HashMap<>();
        requestPayload.put("client_id", CLIENT_ID);
        requestPayload.put("client_secret", CLIENT_SECRET);
        requestPayload.put("code", code);
        requestPayloads.setAll(requestPayload);

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        Map<String, String> header = new HashMap<>();

        header.put("Accept", "application/json");
        headers.setAll(header);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(requestPayloads, headers);

        ResponseEntity<GitHubToken> response = restTemplate.postForEntity(ACCESS_TOKEN_URI, request,
            GitHubToken.class);

        return response.getBody();
    }

    @Override
    public UserProfileDto getUserProfile(OauthToken token) {
        Map<String, Object> userProfileMap = getUserProfileFromOAuthServer(token)
            .orElseThrow(() -> new IllegalArgumentException("사용자 정보가 없습니다"));

        return new UserProfileDto(
            Long.parseLong(String.valueOf(userProfileMap.get("id"))),
            String.valueOf(userProfileMap.get("email")),
            String.valueOf(userProfileMap.get("login")));
    }

    private Optional<Map<String, Object>> getUserProfileFromOAuthServer(OauthToken token) {
        RequestEntity<Void> request = RequestEntity.get(USER_INFO_URI)
            .header("Authorization", token.getAccessTokenHeader())
            .accept(MediaType.APPLICATION_JSON)
            .build();

        ParameterizedTypeReference<HashMap<String, Object>> responseType = new ParameterizedTypeReference<>() {};

        Map<String, Object> userProfileMap = restTemplate.exchange(request, responseType).getBody();

        return Optional.ofNullable(userProfileMap);
    }
}

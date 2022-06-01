package com.airbnb.domain.login;

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

public class GitHubOAuthServer extends OAuthServerImpl {

    private final String clientId = "b7fe0a7f351bdafee25d";
    private final String clientSecret = "";
    private final String accessTokenUri = "https://github.com/login/oauth/access_token";
    private final String userInfoUri = "https://api.github.com/user";

    @Override
    public OauthToken getOAuthToken(String code) {
        MultiValueMap<String, String> requestPayloads = new LinkedMultiValueMap<>();
        Map<String, String> requestPayload = new HashMap<>();
        requestPayload.put("client_id", clientId);
        requestPayload.put("client_secret", clientSecret);
        requestPayload.put("code", code);
        requestPayloads.setAll(requestPayload);

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        Map<String, String> header = new HashMap<>();

        header.put("Accept", "application/json");
        headers.setAll(header);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(requestPayloads, headers);

        ResponseEntity<GitHubToken> response = restTemplate.postForEntity(accessTokenUri, request,
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
        RequestEntity<Void> request = RequestEntity.get(userInfoUri)
            .header("Authorization", token.getAccessToken())
            .accept(MediaType.APPLICATION_JSON)
            .build();

        ParameterizedTypeReference<HashMap<String, Object>> responseType = new ParameterizedTypeReference<>() {};

        Map<String, Object> userProfileMap = restTemplate.exchange(request, responseType).getBody();

        return Optional.ofNullable(userProfileMap);
    }
}

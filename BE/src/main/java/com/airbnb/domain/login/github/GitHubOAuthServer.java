package com.airbnb.domain.login.github;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.airbnb.domain.login.OAuthServer;
import com.airbnb.domain.login.OauthToken;
import com.airbnb.domain.login.dto.UserProfileDto;

@Component(value = "github")
public class GitHubOAuthServer implements OAuthServer {

    private final String clientId;
    private final String clientSecret;
    private final String githubTokenServerUri;
    private final String githubOAuthServerUri;
    private final RestTemplate restTemplate;


    public GitHubOAuthServer(
        @Value("${jwt.token.github.client-id}") String clientId,
        @Value("${jwt.token.github.client-secret}") String clientSecret,
        @Value("${jwt.token.github.token-server-uri}") String githubTokenServerUri,
        @Value("${jwt.token.github.oauth-server-uri}") String githubOAuthServerUri,
        RestTemplate restTemplate) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.githubTokenServerUri = githubTokenServerUri;
        this.githubOAuthServerUri = githubOAuthServerUri;
        this.restTemplate = restTemplate;
    }

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

        ResponseEntity<GitHubToken> response = restTemplate.postForEntity(githubTokenServerUri, request,
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
        RequestEntity<Void> request = RequestEntity.get(githubOAuthServerUri)
            .header("Authorization", token.getAccessTokenHeader())
            .accept(MediaType.APPLICATION_JSON)
            .build();

        ParameterizedTypeReference<HashMap<String, Object>> responseType = new ParameterizedTypeReference<>() {};

        Map<String, Object> userProfileMap = restTemplate.exchange(request, responseType).getBody();

        return Optional.ofNullable(userProfileMap);
    }
}

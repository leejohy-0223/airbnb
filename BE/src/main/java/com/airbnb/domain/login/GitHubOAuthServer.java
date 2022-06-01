package com.airbnb.domain.login;

import java.util.HashMap;
import java.util.Map;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class GitHubOAuthServer extends OAuthServerImpl {

    public GitHubOAuthServer(RestTemplate restTemplate) {
        super(restTemplate);
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

        ResponseEntity<GithubToken> response = restTemplate.postForEntity(accessTokenUri, request,
            GithubToken.class);
        return response.getBody();    }

    @Override
    public UserProfileDto getUserProfile(OauthToken oAuthToken) {
        return null;
    }
}

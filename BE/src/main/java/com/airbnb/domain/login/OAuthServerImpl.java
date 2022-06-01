package com.airbnb.domain.login;

import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

public abstract class OAuthServerImpl implements OAuthServer {

    protected final RestTemplate restTemplate = new RestTemplate();

    protected HttpHeaders createHeaders(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        if (accessToken != null) {
            headers.add("Authorization", "Bearer " + accessToken);
        }
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
        return headers;
    }
}

package com.airbnb.domain.login.github;

import com.airbnb.domain.login.OauthToken;
import com.fasterxml.jackson.annotation.JsonProperty;

public class GitHubToken implements OauthToken {

    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("token_type")
    private String tokenType;

    private String scope;

    @Override
    public String getAccessToken() {
        return accessToken;
    }

    @Override
    public String getAccessTokenHeader() {
        return this.tokenType + " " + this.accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    @Override
    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

}

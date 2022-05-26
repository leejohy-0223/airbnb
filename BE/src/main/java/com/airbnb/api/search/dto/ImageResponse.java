package com.airbnb.api.search.dto;

public class ImageResponse {
    private String url;

    public ImageResponse(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

package com.airbnb.api.houses.dto;

public class ImageResponse {
    private String url;

    public ImageResponse(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}

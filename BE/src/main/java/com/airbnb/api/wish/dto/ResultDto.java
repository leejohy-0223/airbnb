package com.airbnb.api.wish.dto;

import org.springframework.http.HttpStatus;

public class ResultDto {

    private final Boolean valid;
    private final String message;

    public ResultDto(Boolean valid, String message) {
        this.valid = valid;
        this.message = message;
    }

    public static ResultDto ok() {
        return new ResultDto(true, HttpStatus.OK.name());
    }

    public static ResultDto error(Exception e) {
        return new ResultDto(false, e.getMessage());
    }

    public Boolean getValid() {
        return valid;
    }

    public String getMessage() {
        return message;
    }
}

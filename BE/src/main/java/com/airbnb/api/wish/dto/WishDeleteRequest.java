package com.airbnb.api.wish.dto;

import javax.validation.constraints.NotNull;

public class WishDeleteRequest {

    @NotNull(message = "사용자의 Id 는 필수 입니다.")
    private long userId;

    @NotNull(message = "위시 리스트의 Id 는 필수 입니다.")
    private long wishId;

    public WishDeleteRequest() {
    }

    public WishDeleteRequest(long userId, long wishId) {
        this.userId = userId;
        this.wishId = wishId;
    }

    public long getUserId() {
        return userId;
    }

    public long getWishId() {
        return wishId;
    }
}

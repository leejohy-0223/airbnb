package com.airbnb.api.wish.dto;


import javax.validation.constraints.NotNull;

public class WishCreateRequest {

    @NotNull(message = "사용자의 ID값은 필수 입니다.")
    private Long userId;

    @NotNull(message = "숙소의 ID값은 필수 입니다.")
    private Long houseId;

    public Long getUserId() {
        return userId;
    }

    public Long getHouseId() {
        return houseId;
    }
}

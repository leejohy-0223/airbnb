package com.airbnb.api.wish.dto;

import javax.validation.constraints.NotNull;
import java.util.Objects;

public class WishCreateRequest {

    @NotNull(message = "사용자의 ID값은 필수 입니다.")
    private Long userId;

    @NotNull(message = "숙소의 ID값은 필수 입니다.")
    private Long houseId;

    public WishCreateRequest(Long userId, Long houseId) {
        this.userId = userId;
        this.houseId = houseId;
    }

    public WishCreateRequest() {
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setHouseId(Long houseId) {
        this.houseId = houseId;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getHouseId() {
        return houseId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WishCreateRequest that = (WishCreateRequest) o;
        return Objects.equals(getUserId(), that.getUserId()) && Objects.equals(getHouseId(), that.getHouseId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserId(), getHouseId());
    }
}

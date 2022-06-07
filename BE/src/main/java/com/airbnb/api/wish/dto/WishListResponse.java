package com.airbnb.api.wish.dto;

import java.util.List;

public class WishListResponse {
    private List<WishResponse> wishList;

    public WishListResponse(List<WishResponse> wishList) {
        this.wishList = wishList;
    }

    public List<WishResponse> getWishList() {
        return wishList;
    }
}

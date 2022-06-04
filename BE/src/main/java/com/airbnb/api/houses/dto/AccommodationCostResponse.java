package com.airbnb.api.houses.dto;

public class AccommodationCostResponse {
    private int price;
    private int discountFee;

    public AccommodationCostResponse(int price, int discountFee) {
        this.price = price;
        this.discountFee = discountFee;
    }

    public int getPrice() {
        return price;
    }

    public int getDiscountFee() {
        return discountFee;
    }
}

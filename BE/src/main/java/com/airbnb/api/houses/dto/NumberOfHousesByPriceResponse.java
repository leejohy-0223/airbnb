package com.airbnb.api.houses.dto;

import java.util.List;

public class NumberOfHousesByPriceResponse {
    private List<NumberOfHousesByPrice> NumberOfHousesByPrice;

    public NumberOfHousesByPriceResponse(List<NumberOfHousesByPrice> NumberOfHousesByPrice) {
        this.NumberOfHousesByPrice = NumberOfHousesByPrice;
    }
}

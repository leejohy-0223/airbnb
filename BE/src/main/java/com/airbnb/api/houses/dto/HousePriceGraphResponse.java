package com.airbnb.api.houses.dto;

import java.util.List;
import java.util.Objects;

public class HousePriceGraphResponse {
    private List<NumberOfHousesByPriceResponse> NumberOfHousesByPrice;

    public HousePriceGraphResponse(List<NumberOfHousesByPriceResponse> NumberOfHousesByPrice) {
        this.NumberOfHousesByPrice = NumberOfHousesByPrice;
    }

    public List<NumberOfHousesByPriceResponse> getNumberOfHousesByPrice() {
        return NumberOfHousesByPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        HousePriceGraphResponse that = (HousePriceGraphResponse)o;
        return Objects.equals(NumberOfHousesByPrice, that.NumberOfHousesByPrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(NumberOfHousesByPrice);
    }
}

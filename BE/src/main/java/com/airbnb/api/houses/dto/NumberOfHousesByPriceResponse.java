package com.airbnb.api.houses.dto;

import java.util.List;
import java.util.Objects;

public class NumberOfHousesByPriceResponse {
    private List<NumberOfHousesByPrice> NumberOfHousesByPrice;

    public NumberOfHousesByPriceResponse(List<NumberOfHousesByPrice> NumberOfHousesByPrice) {
        this.NumberOfHousesByPrice = NumberOfHousesByPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        NumberOfHousesByPriceResponse that = (NumberOfHousesByPriceResponse)o;
        return Objects.equals(NumberOfHousesByPrice, that.NumberOfHousesByPrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(NumberOfHousesByPrice);
    }
}

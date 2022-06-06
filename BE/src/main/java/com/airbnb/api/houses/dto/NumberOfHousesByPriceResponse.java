package com.airbnb.api.houses.dto;

import java.util.Objects;

public class NumberOfHousesByPriceResponse {
    private Integer price;
    private Long count;

    public NumberOfHousesByPriceResponse(Integer price, Long count) {
        this.price = price * 10000;
        this.count = count;
    }

    public NumberOfHousesByPriceResponse() {
    }

    public Integer getPrice() {
        return price;
    }

    public Long getCount() {
        return count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        NumberOfHousesByPriceResponse that = (NumberOfHousesByPriceResponse)o;
        return Objects.equals(getPrice(), that.getPrice()) && Objects.equals(getCount(),
            that.getCount());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPrice(), getCount());
    }
}

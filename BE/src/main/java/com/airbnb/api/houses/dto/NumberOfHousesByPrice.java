package com.airbnb.api.houses.dto;

import java.util.Objects;

public class NumberOfHousesByPrice {
    private Integer price;
    private Long count;

    public NumberOfHousesByPrice(Integer price, Long count) {
        this.price = price * 10000;
        this.count = count;
    }

    public NumberOfHousesByPrice() {
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
        NumberOfHousesByPrice that = (NumberOfHousesByPrice)o;
        return Objects.equals(getPrice(), that.getPrice()) && Objects.equals(getCount(),
            that.getCount());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPrice(), getCount());
    }
}

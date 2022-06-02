package com.airbnb.api.houses.dto;

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
    public String toString() {
        return "HouseCount{" +
            "price=" + price +
            ", count=" + count +
            '}';
    }
}

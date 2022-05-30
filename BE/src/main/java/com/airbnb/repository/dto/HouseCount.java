package com.airbnb.repository.dto;

public class HouseCount {
    private Integer price;
    private Long count;

    public HouseCount(Integer price, Long count) {
        this.price = price;
        this.count = count;
    }

    public HouseCount() {
    }

    @Override
    public String toString() {
        return "HouseCount{" +
            "price=" + price +
            ", count=" + count +
            '}';
    }
}

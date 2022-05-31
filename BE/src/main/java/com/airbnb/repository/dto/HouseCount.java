package com.airbnb.repository.dto;

public class HouseCount {
    private Integer price;
    private Long count;

    public HouseCount(Integer price, Long count) {
        this.price = price * 10000;
        this.count = count;
    }

    public HouseCount() {
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

package com.airbnb.domain;

import javax.persistence.*;

@Entity
public class HouseDiscountPolicy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "house_id")
    private House house;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "discount_policy_id")
    private DiscountPolicy discountPolicy;

    public HouseDiscountPolicy() {
    }

    public HouseDiscountPolicy(House house, DiscountPolicy discountPolicy) {
        this.house = house;
        this.discountPolicy = discountPolicy;
    }

    public int getDiscountPercent() {
        return discountPolicy.getDiscountPercent();
    }
}

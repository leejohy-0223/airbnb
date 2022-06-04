package com.airbnb.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class DiscountPolicy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "discount_policy_id")
    private Long id;

    private String name;

    private int discountPercent;

    public DiscountPolicy(String name, int discountPercent) {
        this.name = name;
        this.discountPercent = discountPercent;
    }

    public DiscountPolicy() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getDiscountPercent() {
        return discountPercent;
    }
}

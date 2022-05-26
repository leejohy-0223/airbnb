package com.airbnb.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class DiscountPolicy {

    @Id
    @GeneratedValue
    @Column(name = "discount_policy_id")
    private Long id;

    private String name;

    private int discountPercent;

    @OneToMany(mappedBy = "discountPolicy")
    private List<HouseDiscountPolicy> houseDiscountPolicies = new ArrayList<>();

}

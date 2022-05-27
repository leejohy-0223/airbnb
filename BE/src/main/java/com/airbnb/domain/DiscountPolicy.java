package com.airbnb.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
public class DiscountPolicy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "discount_policy_id")
    private Long id;

    private String name;

    private int discountPercent;

    @OneToMany(mappedBy = "discountPolicy")
    private List<HouseDiscountPolicy> houseDiscountPolicies = new ArrayList<>();

}

package com.airbnb.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
public class Wish {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "house_id")
    private House house;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Wish() {
    }

    public Wish(House house, User user) {
        this.house = house;
        this.user = user;
    }

    public Wish(Long id, House house, User user) {
        this.id = id;
        this.house = house;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public House getHouse() {
        return house;
    }

    public User getUser() {
        return user;
    }
}

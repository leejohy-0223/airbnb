package com.airbnb.domain;

import org.locationtech.jts.geom.Point;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class House {

    @Id
    @GeneratedValue
    @Column(name = "house_id")
    private Long id;

    private String name;
    private int price;

    @Embedded
    private DetailInfo detailInfo;

    @Lob
    private Point point;

    @OneToMany(mappedBy = "house")
    private List<Reservation> reservations = new ArrayList<>();

    @OneToMany(mappedBy = "house")
    private List<Image> images = new ArrayList<>();

    @OneToMany(mappedBy = "house")
    private List<HouseDiscountPolicy> houseDiscountPolicies = new ArrayList<>();

    @OneToMany(mappedBy = "house")
    private List<WishList> wishLists = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "user_id", name = "host_id")
    private User host;

    public House(String name, int price, DetailInfo detailInfo, Point point) {
        this.name = name;
        this.price = price;
        this.detailInfo = detailInfo;
        this.point = point;
    }

    public House() {
    }

    public Point getPosition() {
        return point;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public DetailInfo getDetailInfo() {
        return detailInfo;
    }

    public List<String> getImagesURL() {
        return images.stream()
            .map(Image::getUrl)
            .collect(Collectors.toList());
    }

    public String getHostName() {
        return host.getName();
    }
}

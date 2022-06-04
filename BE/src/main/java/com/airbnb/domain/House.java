package com.airbnb.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.locationtech.jts.geom.Point;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class House {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "house_id")
    private Long id;

    private String name;
    private int price;

    @Embedded
    private DetailInfo detailInfo;

    private Point point;

    @JsonBackReference
    @OneToMany(mappedBy = "house")
    private List<Reservation> reservations = new ArrayList<>();

    @JsonBackReference
    @OneToMany(mappedBy = "house")
    private List<Image> images = new ArrayList<>();

    @JsonBackReference
    @OneToMany(mappedBy = "house")
    private List<HouseDiscountPolicy> houseDiscountPolicies = new ArrayList<>();

    @JsonManagedReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "user_id", name = "host_id")
    private User host;

    public House(String name, int price, DetailInfo detailInfo, Point point, User host) {
        this.name = name;
        this.price = price;
        this.detailInfo = detailInfo;
        this.point = point;
        this.host = host;
    }

    public House() {
    }

    public void addDiscountPolicy(DiscountPolicy discountPolicy) {
        HouseDiscountPolicy houseDiscountPolicy = new HouseDiscountPolicy(this, discountPolicy);
        houseDiscountPolicies.add(houseDiscountPolicy);
    }

    public Point getPoint() {
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

    public int calculateDiscountAmount(int duration) {
        int maxDiscountPercent = houseDiscountPolicies
                .stream()
                .mapToInt(HouseDiscountPolicy::getDiscountPercent)
                .max().orElse(0);

        return (int)(price * ((maxDiscountPercent) * 0.01) * duration);
    }
}

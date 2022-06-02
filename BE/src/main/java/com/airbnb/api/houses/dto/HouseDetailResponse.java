package com.airbnb.api.houses.dto;

import com.airbnb.domain.DetailInfo;
import com.airbnb.domain.House;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class HouseDetailResponse {

    private Long id;
    private String name;
    private int price;
    private DetailInfo detailInfo;
    private List<ImageResponse> images;
    private String hostName;

    public HouseDetailResponse(House house) {
        this.id = house.getId();
        this.name = house.getName();
        this.price = house.getPrice();
        this.detailInfo = house.getDetailInfo();
        this.images = house.getImagesURL()
                .stream()
                .map(ImageResponse::new)
                .collect(Collectors.toList());
        this.hostName = house.getHostName();
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

    public List<ImageResponse> getImages() {
        return images;
    }

    public String getHostName() {
        return hostName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        HouseDetailResponse that = (HouseDetailResponse)o;
        return getPrice() == that.getPrice() && Objects.equals(getId(), that.getId()) && Objects.equals(
            getName(), that.getName()) && Objects.equals(getDetailInfo(), that.getDetailInfo())
            && Objects.equals(getImages(), that.getImages()) && Objects.equals(getHostName(),
            that.getHostName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getPrice(), getDetailInfo(), getImages(), getHostName());
    }
}

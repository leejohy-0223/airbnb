package com.airbnb.api.wish.dto;

import com.airbnb.api.houses.HouseController;
import com.airbnb.domain.DetailInfo;
import com.airbnb.domain.Wish;
import org.springframework.hateoas.EntityModel;

import java.util.Objects;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class WishResponse extends EntityModel<Wish> {

    private Long userId;
    private String name;
    private int price;
    private DetailInfo detailInfo;

    public WishResponse(Wish wish) {
        super(wish);
        this.userId = wish.getUser().getId();
        this.name = wish.getHouse().getName();
        this.price = wish.getHouse().getPrice();
        this.detailInfo = wish.getHouse().getDetailInfo();

        add(linkTo(methodOn(HouseController.class).findHouseInformation(wish.getHouse().getId())).withSelfRel());
    }

    public Long getUserId() {
        return userId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        WishResponse that = (WishResponse) o;
        return getPrice() == that.getPrice() && Objects.equals(getUserId(), that.getUserId()) && Objects.equals(getName(), that.getName()) && Objects.equals(getDetailInfo(), that.getDetailInfo());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getUserId(), getName(), getPrice(), getDetailInfo());
    }
}

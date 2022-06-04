package com.airbnb.api.wish.dto;

import com.airbnb.api.wish.WishController;
import com.airbnb.domain.DetailInfo;
import com.airbnb.domain.Wish;
import org.springframework.hateoas.EntityModel;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

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

        add(linkTo(WishController.class).slash(wish.getId()).withRel("self"));
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
}

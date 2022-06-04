package com.airbnb.api.wish;

import com.airbnb.api.wish.dto.ResultDto;
import com.airbnb.api.wish.dto.WishCreateRequest;
import com.airbnb.service.WishService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/wish/")
public class WishController {

    private final WishService wishService;

    public WishController(WishService wishService) {
        this.wishService = wishService;
    }

    @PostMapping
    public ResultDto createWish(@Validated @RequestBody WishCreateRequest request) {
        wishService.addWish(request.getUserId(), request.getHouseId());
        return ResultDto.ok();
    }
}

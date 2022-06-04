package com.airbnb.api.wish;

import com.airbnb.api.wish.dto.WishCreateRequest;
import com.airbnb.api.wish.dto.WishDeleteRequest;
import com.airbnb.api.wish.dto.WishResponse;
import com.airbnb.common.ResultDto;
import com.airbnb.service.WishService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/wish")
public class WishController {

    private static final Logger log = LoggerFactory.getLogger(WishController.class);

    public static final String NOT_FOUND_USER_EXECEPTION = "해당 회원을 찾을 수 없습니다.";
    private final WishService wishService;

    public WishController(WishService wishService) {
        this.wishService = wishService;
    }

    @ExceptionHandler
    public ResultDto exJwtHandler(IllegalStateException e) {
        log.info("[WishController][JWT Token 에러 발생] : {}", e.getMessage());
        return ResultDto.error(e);
    }

    @ExceptionHandler
    public ResultDto exWishHandler(NoSuchElementException e) {
        log.info("[WishController][존재하지 않는 Wish] : {}", e.getMessage());
        return ResultDto.error(e);
    }

    @PostMapping
    public ResultDto createWish(@Validated @RequestBody WishCreateRequest request) {
        wishService.addWish(request.getUserId(), request.getHouseId());
        return ResultDto.ok();
    }

    @GetMapping
    public List<WishResponse> getWishList(HttpServletRequest request) {
        String userEmail = (String) request.getAttribute("userEmail");
        if (userEmail.isBlank()) {
            throw new NoSuchElementException(NOT_FOUND_USER_EXECEPTION);
        }
        return wishService.getWishListByEmail(userEmail);
    }

    @DeleteMapping
    public ResultDto deleteWish(@Validated @RequestBody WishDeleteRequest request) {
        wishService.deleteWish(request.getUserId(), request.getWishId());
        return ResultDto.ok();
    }
}

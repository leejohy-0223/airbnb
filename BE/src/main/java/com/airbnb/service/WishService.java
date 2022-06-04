package com.airbnb.service;

import com.airbnb.api.wish.dto.WishResponse;
import com.airbnb.domain.House;
import com.airbnb.domain.User;
import com.airbnb.domain.Wish;
import com.airbnb.repository.HouseRepository;
import com.airbnb.repository.UserRepository;
import com.airbnb.repository.WishRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@Transactional
public class WishService {
    public static final String NOT_FOUND_USER_EXCEPTION = "해당 User를 찾을 수 없습니다.";
    public static final String NOT_FOUND_HOUSE_EXCEPTION = "해당 숙소를 찾을 수 없습니다.";
    public static final String NOT_FOUND_WISH_EXCEPTION = "해당 위시 리스트를 찾을 수 없습니다.";
    private final UserRepository userRepository;
    private final HouseRepository houseRepository;
    private final WishRepository wishRepository;

    public WishService(UserRepository userRepository, HouseRepository houseRepository, WishRepository wishRepository) {
        this.userRepository = userRepository;
        this.houseRepository = houseRepository;
        this.wishRepository = wishRepository;
    }

    public Wish addWish(Long userId, Long houseId) {
        House findHouse = houseRepository.findById(houseId)
                .orElseThrow(() -> new NoSuchElementException(NOT_FOUND_HOUSE_EXCEPTION));

        User findMember = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException(NOT_FOUND_USER_EXCEPTION));

        return wishRepository.save(new Wish(findHouse, findMember));
    }

    @Transactional(readOnly = true)
    public List<WishResponse> getWishListByEmail(String userEmail) {
        List<Wish> wishList = userRepository.findUserWishListByEmail(userEmail);
        return wishList.stream()
                .map(WishResponse::new)
                .collect(Collectors.toList());
    }

    public Wish deleteWish(long userId, long wishId) {
        Wish findWish = wishRepository.findById(wishId)
                .orElseThrow(() -> new NoSuchElementException(NOT_FOUND_WISH_EXCEPTION));

        if (!isValidUser(userId, findWish.getUser())) {
            throw new NoSuchElementException(NOT_FOUND_USER_EXCEPTION);
        }

        wishRepository.deleteById(wishId);
        return findWish;
    }

    private boolean isValidUser(long userId, User user) {
        return user.isSameId(userId);
    }
}

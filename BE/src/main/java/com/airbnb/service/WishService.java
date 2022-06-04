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
                .orElseThrow(() -> new NoSuchElementException("해당 숙소를 찾을 수 없습니다."));

        User findMember = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("해당 User를 찾을 수 없습니다."));

        return wishRepository.save(new Wish(findHouse, findMember));
    }

    @Transactional(readOnly = true)
    public List<WishResponse> getWishListByEmail(String userEmail) {
        List<Wish> wishList = userRepository.findUserWishListByEmail(userEmail);
        return wishList.stream()
                .map(WishResponse::new)
                .collect(Collectors.toList());
    }
}

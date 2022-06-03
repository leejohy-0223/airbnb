package com.airbnb.service;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.io.ParseException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.airbnb.api.houses.dto.AccommodationCostResponse;
import com.airbnb.domain.DiscountPolicy;
import com.airbnb.domain.House;
import com.airbnb.repository.HouseRepository;

@ExtendWith(MockitoExtension.class)
class HouseServiceTest {

    @InjectMocks
    private HouseService houseService;

    @Mock
    private HouseRepository houseRepository;

    private House house;

    @BeforeEach
    void init() throws ParseException {
        house = new House("house1", 10000, null, null, null);
    }

    @DisplayName("할인 가격을 계산한다.")
    @Test
    void calculate_discount_fee() {
        // given
        house.addDiscountPolicy(new DiscountPolicy("정책1", 30));
        given(houseRepository.findById(any()))
                .willReturn(Optional.of(house));

        // when
        AccommodationCostResponse response = houseService.calculateFee(1L, LocalDateTime.now(),
                LocalDateTime.now().plus(10, ChronoUnit.DAYS));

        // then
        Assertions.assertThat(response.getDiscountFee()).isEqualTo(30000);
        Assertions.assertThat(response.getPrice()).isEqualTo(100000);
    }

    @DisplayName("할인 정책이 없는 경우 할인금액이 0으로 반환")
    @Test
    void calculate_without_discount_fee() {
        // given
        given(houseRepository.findById(any()))
                .willReturn(Optional.of(house));

        // when
        AccommodationCostResponse response = houseService.calculateFee(1L, LocalDateTime.now(),
                LocalDateTime.now().plus(10, ChronoUnit.DAYS));

        // then
        Assertions.assertThat(response.getDiscountFee()).isEqualTo(0);
        Assertions.assertThat(response.getPrice()).isEqualTo(100000);
    }

    @DisplayName("할인 정책이 여러개인 경우 가장 할인률이 높은것을 선택한다")
    @Test
    void calculate_max_discount_fee() {
        // given
        house.addDiscountPolicy(new DiscountPolicy("정책1", 10));
        house.addDiscountPolicy(new DiscountPolicy("정책2", 20));
        house.addDiscountPolicy(new DiscountPolicy("정책3", 30));
        house.addDiscountPolicy(new DiscountPolicy("정책4", 50));
        given(houseRepository.findById(any()))
                .willReturn(Optional.of(house));

        // when
        AccommodationCostResponse response = houseService.calculateFee(1L, LocalDateTime.now(),
                LocalDateTime.now().plus(10, ChronoUnit.DAYS));

        // then
        Assertions.assertThat(response.getDiscountFee()).isEqualTo(50000);
        Assertions.assertThat(response.getPrice()).isEqualTo(100000);
    }
}

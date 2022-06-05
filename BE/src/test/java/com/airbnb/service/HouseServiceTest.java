package com.airbnb.service;

import static org.junit.jupiter.api.Assertions.*;
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
import org.locationtech.jts.io.ParseException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.airbnb.api.houses.dto.AccommodationCostResponse;
import com.airbnb.api.houses.dto.ReservationInformationRequest;
import com.airbnb.api.houses.dto.ReservationResponse;
import com.airbnb.domain.DiscountPolicy;
import com.airbnb.domain.House;
import com.airbnb.domain.Reservation;
import com.airbnb.domain.Role;
import com.airbnb.domain.User;
import com.airbnb.repository.HouseRepository;
import com.airbnb.repository.ReservationRepository;
import com.airbnb.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class HouseServiceTest {

    @InjectMocks
    private HouseService houseService;

    @Mock
    private HouseRepository houseRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ReservationRepository reservationRepository;

    private House house;
    private User user;

    @BeforeEach
    void init() throws ParseException {
        user = new User("user1", "mail@mail", Role.GUEST);
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

    @DisplayName("유효한 숙소 id와 유저 id로 예약 요청이 오면 정상적으로 수행된다.")
    @Test
    void reservation_is_succeed() {
        // given
        ReservationInformationRequest request = new ReservationInformationRequest(
            LocalDateTime.now(), LocalDateTime.now(), 1, 10000);

        Reservation reservation = new Reservation(request.getFee(), request.getNumberOfGuests(),
            request.getStartDateTime(), request.getEndDateTime(), null, house);

        given(houseRepository.findById(anyLong()))
            .willReturn(Optional.of(house));

        given(userRepository.findUserByEmail(anyString()))
            .willReturn(Optional.of(user));

        given(reservationRepository.save(any(Reservation.class)))
            .willReturn(reservation);

        // when
        ReservationResponse result = houseService.reserveHouse(1L, request, "email");

        // then
        Assertions.assertThat(result.getHouseName()).isEqualTo("house1");
        verify(houseRepository, times(1)).findById(anyLong());
        verify(userRepository, times(1)).findUserByEmail(anyString());
        verify(reservationRepository, times(1)).save(any(Reservation.class));
    }

    @DisplayName("유효하지 않은 숙소 id라면 예약에 실패한다.")
    @Test
    void reservation_is_fail_because_house_id_invalid() {
        // given
        ReservationInformationRequest request = new ReservationInformationRequest(
            LocalDateTime.now(), LocalDateTime.now(), 1, 10000);

        given(houseRepository.findById(anyLong()))
            .willReturn(Optional.empty());

        // when
        assertThrows(IllegalStateException.class, () -> houseService.reserveHouse(1L, request, "email"));

        // then
        verify(houseRepository, times(1)).findById(anyLong());
        verify(userRepository, times(0)).findUserByEmail(anyString());
        verify(reservationRepository, times(0)).save(any(Reservation.class));
    }

    @DisplayName("유효한 숙소 id이지만 유저 id가 유효하지 않다면 예약에 실패한다.")
    @Test
    void reservation_is_fail_because_user_id_invalid() {
        // given
        ReservationInformationRequest request = new ReservationInformationRequest(
            LocalDateTime.now(), LocalDateTime.now(), 1, 10000);

        given(houseRepository.findById(anyLong()))
            .willReturn(Optional.of(house));

        given(userRepository.findUserByEmail(anyString()))
            .willReturn(Optional.empty());

        // when
        assertThrows(IllegalStateException.class, () -> houseService.reserveHouse(1L, request, "email"));

        // then
        verify(houseRepository, times(1)).findById(anyLong());
        verify(userRepository, times(1)).findUserByEmail(anyString());
        verify(reservationRepository, times(0)).save(any(Reservation.class));
    }
}

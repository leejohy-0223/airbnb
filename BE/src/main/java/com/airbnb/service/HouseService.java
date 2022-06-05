package com.airbnb.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.airbnb.api.houses.dto.AccommodationCostResponse;
import com.airbnb.api.houses.dto.HouseDetailResponse;
import com.airbnb.api.houses.dto.LocationInformationRequest;
import com.airbnb.api.houses.dto.NumberOfHousesByPrice;
import com.airbnb.api.houses.dto.NumberOfHousesByPriceResponse;
import com.airbnb.api.houses.dto.ReservationDetailResponse;
import com.airbnb.api.houses.dto.ReservationInformationRequest;
import com.airbnb.api.houses.dto.ReservationResponse;
import com.airbnb.api.houses.dto.ReservationsResponse;
import com.airbnb.api.houses.dto.SearchConditionRequest;
import com.airbnb.domain.House;
import com.airbnb.domain.Reservation;
import com.airbnb.domain.User;
import com.airbnb.repository.HouseRepository;
import com.airbnb.repository.ReservationRepository;
import com.airbnb.repository.UserRepository;

@Service
public class HouseService {

    private static final Logger log = LoggerFactory.getLogger(HouseService.class);

    private static final int DISTANCE = 1000;
    private final HouseRepository houseRepository;
    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;

    public HouseService(HouseRepository houseRepository, ReservationRepository reservationRepository,
        UserRepository userRepository) {
        this.houseRepository = houseRepository;
        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public List<HouseDetailResponse> findByCondition(SearchConditionRequest request) {
        List<House> houseList = houseRepository.searchByCondition(request.getPoint(), DISTANCE, request.getMinFee(),
            request.getMaxFee());

        return houseList
            .stream()
            .map(HouseDetailResponse::new)
            .collect(Collectors.toList());
    }

    @Transactional
    public House save(House house) {
        return houseRepository.save(house);
    }

    @Transactional(readOnly = true)
    public HouseDetailResponse findHouseInformation(Long id) {
        House house = houseRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("찾을 수 없는 숙소 정보입니다."));

        return new HouseDetailResponse(house);
    }

    @Transactional
    public NumberOfHousesByPriceResponse findHouseCountInLocation(LocationInformationRequest request) {
        List<NumberOfHousesByPrice> houseCounts = houseRepository.numberOfHousesInTheRange(request.getPoint(),
            DISTANCE);
        return new NumberOfHousesByPriceResponse(houseCounts);
    }

    @Transactional(readOnly = true)
    public AccommodationCostResponse calculateFee(Long houseId, LocalDateTime startDateTime,
        LocalDateTime endDateTime) {

        House house = houseRepository.findById(houseId)
            .orElseThrow(() -> new IllegalArgumentException("찾을 수 없는 숙소 정보입니다."));

        int duration = (int)ChronoUnit.DAYS.between(startDateTime, endDateTime);
        int discountAmount = house.calculateDiscountAmount(duration);

        log.info("duration : {}", duration);
        log.info("discountAmount : {}", discountAmount);

        return new AccommodationCostResponse(house.getPrice() * duration, discountAmount);
    }

    @Transactional
    public ReservationResponse reserveHouse(Long id, ReservationInformationRequest request, String userEmail) {
        House house = houseRepository.findById(id)
            .orElseThrow(() -> new IllegalStateException("찾을 수 없는 숙소 정보입니다."));

        User host = userRepository.findUserByEmail(userEmail)
            .orElseThrow(() -> new IllegalStateException("찾을 수 없는 유저입니다."));

        Reservation reservation = new Reservation(request.getFee(), request.getNumberOfGuests(),
            request.getStartDateTime(),
            request.getEndDateTime(), host, house);

        Reservation save = reservationRepository.save(reservation);

        return new ReservationResponse(save);
    }

    @Transactional(readOnly = true)
    public ReservationsResponse showReservations(String userEmail) {
        List<Reservation> reservations = reservationRepository.findReservationsByEmail(userEmail);

        return new ReservationsResponse(reservations.stream()
            .map(ReservationResponse::new)
            .collect(Collectors.toList()));
    }

    @Transactional(readOnly = true)
    public ReservationDetailResponse findReservation(Long id, String userEmail) {
        Reservation reservation = reservationRepository.findByIdAndEmail(id, userEmail)
            .orElseThrow(() -> new IllegalStateException("찾을 수 없는 예약입니다."));

        return new ReservationDetailResponse(reservation);
    }

    @Transactional
    public void cancelReservation(Long id, String userEmail) {
        Reservation reservation = reservationRepository.findByIdAndEmail(id, userEmail)
            .orElseThrow(() -> new IllegalStateException("찾을 수 없는 예약입니다."));
        reservationRepository.delete(reservation);
    }
}

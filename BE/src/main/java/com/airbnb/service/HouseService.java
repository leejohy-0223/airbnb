package com.airbnb.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.airbnb.api.houses.dto.AccommodationCostResponse;
import com.airbnb.api.houses.dto.NumberOfHousesByPriceResponse;
import com.airbnb.api.houses.dto.HouseDetailResponse;
import com.airbnb.api.houses.dto.LocationInformationRequest;
import com.airbnb.api.houses.dto.NumberOfHousesByPrice;
import com.airbnb.api.houses.dto.SearchConditionRequest;
import com.airbnb.domain.House;
import com.airbnb.domain.HouseDiscountPolicy;
import com.airbnb.domain.QDiscountPolicy;
import com.airbnb.repository.HouseRepository;

@Service
public class HouseService {

    private static final Logger log = LoggerFactory.getLogger(HouseService.class);

    private static final int DISTANCE = 1000;
    private final HouseRepository houseRepository;

    public HouseService(HouseRepository houseRepository) {
        this.houseRepository = houseRepository;
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
}

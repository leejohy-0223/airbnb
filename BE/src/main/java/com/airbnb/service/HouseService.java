package com.airbnb.service;

import java.util.List;

import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Service;

import com.airbnb.api.search.dto.HouseDetailResponse;
import com.airbnb.domain.House;
import com.airbnb.repository.HouseRepository;

@Service
public class HouseService {

    private final HouseRepository houseRepository;

    public HouseService(HouseRepository houseRepository) {
        this.houseRepository = houseRepository;
    }

    public List<String> findByCondition(Point point, Integer minFee, Integer maxFee) {
        List<String> houses = houseRepository.searchByConditionNativeV2(point, minFee, maxFee);
        return houses;
    }

    public House save(House house) {
        return houseRepository.save(house);
    }

    public HouseDetailResponse findHouseInformation(Long id) {
        House house = houseRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("찾을 수 없는 숙소 정보입니다."));

        return new HouseDetailResponse(house);
    }
}

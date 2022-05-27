package com.airbnb.service;

import com.airbnb.api.houses.dto.HouseDetailResponse;
import com.airbnb.domain.House;
import com.airbnb.repository.HouseRepository;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HouseService {

    private final HouseRepository houseRepository;

    public HouseService(HouseRepository houseRepository) {
        this.houseRepository = houseRepository;
    }

    public List<House> findByCondition(Point point, Integer minFee, Integer maxFee) {
        return houseRepository.searchByCondition(point, minFee, maxFee);
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

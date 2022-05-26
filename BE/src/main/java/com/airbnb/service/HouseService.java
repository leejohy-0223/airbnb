package com.airbnb.service;

import com.airbnb.domain.House;
import com.airbnb.repository.HouseRepository;
import org.locationtech.jts.geom.Point;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HouseService {

    private final HouseRepository houseRepository;

    public HouseService(HouseRepository houseRepository) {
        this.houseRepository = houseRepository;
    }

    public List<House> findByCondition(Point point, Integer minFee, Integer maxFee, Pageable pageable) {
        List<House> houses = houseRepository.searchByConditionNative(point, minFee, maxFee, pageable);
        return houses;
    }

    public House save(House house) {
        return houseRepository.save(house);
    }
}

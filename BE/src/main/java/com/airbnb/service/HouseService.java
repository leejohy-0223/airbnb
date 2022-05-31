package com.airbnb.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.airbnb.api.houses.dto.HouseCountResponse;
import com.airbnb.api.houses.dto.HouseDetailResponse;
import com.airbnb.api.houses.dto.LocationInformationRequest;
import com.airbnb.api.houses.dto.SearchConditionRequest;
import com.airbnb.domain.House;
import com.airbnb.repository.HouseRepository;
import com.airbnb.repository.dto.HouseCount;

@Service
public class HouseService {

    private static final int DISTANCE = 1000;
    private final HouseRepository houseRepository;

    public HouseService(HouseRepository houseRepository) {
        this.houseRepository = houseRepository;
    }

    @Transactional(readOnly = true)
    public List<HouseDetailResponse> findByCondition(SearchConditionRequest request) {
        List<House> houseList = houseRepository.searchByCondition(request.getPoint(), DISTANCE, request.getMinFee(), request.getMaxFee());

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
    public HouseCountResponse findHouseCountInLocation(LocationInformationRequest request) {
        List<HouseCount> houseCounts = houseRepository.numberOfHousesInTheRange(request.getPoint(), DISTANCE);
        return new HouseCountResponse(houseCounts);
    }
}

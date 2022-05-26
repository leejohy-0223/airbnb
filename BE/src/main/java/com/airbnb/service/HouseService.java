package com.airbnb.service;

import com.airbnb.api.search.dto.HouseDetailResponse;
import com.airbnb.api.search.dto.HouseListResponse;
import com.airbnb.api.search.dto.SearchConditionRequest;
import com.airbnb.domain.House;
import com.airbnb.repository.HouseRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HouseService {

    private final HouseRepository houseRepository;

    public HouseService(HouseRepository houseRepository) {
        this.houseRepository = houseRepository;
    }

    public List<HouseListResponse> findByCondition(SearchConditionRequest request, Pageable pageable) {
        return null;
    }

    public HouseDetailResponse findHouseInformation(Long id) {
        House house = houseRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("찾을 수 없는 숙소 정보입니다."));

        return new HouseDetailResponse(house);
    }
}

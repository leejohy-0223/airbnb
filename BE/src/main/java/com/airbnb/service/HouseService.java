package com.airbnb.service;

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
        List<House> houseList = houseRepository.findByCondition(request, pageable);
        return null;
    }
}

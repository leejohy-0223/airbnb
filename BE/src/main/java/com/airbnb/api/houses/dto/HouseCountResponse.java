package com.airbnb.api.houses.dto;

import java.util.List;

import com.airbnb.repository.dto.HouseCount;

public class HouseCountResponse {
    private List<HouseCount> houseCounts;

    public HouseCountResponse(List<HouseCount> houseCounts) {
        this.houseCounts = houseCounts;
    }
}

package com.airbnb.api.houses.dto;

import java.util.Map;

public class HouseCountResponse {
    private Map<Integer, Integer> priceMap;

    public HouseCountResponse(Map<Integer, Integer> priceMap) {
        this.priceMap = priceMap;


    }
}

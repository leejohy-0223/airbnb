package com.airbnb.api.search;

import com.airbnb.api.search.dto.HouseDetailResponse;
import com.airbnb.api.search.dto.SearchConditionRequest;
import com.airbnb.domain.House;
import com.airbnb.service.HouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/search")
public class SearchController {

    private final HouseService houseService;

    public SearchController(HouseService houseService) {
        this.houseService = houseService;
    }

    @GetMapping("")
    public String findHouse(@RequestBody SearchConditionRequest request, Pageable pageable) {
        List<House> houseList = houseService.findByCondition(
                request.getPosition(),
                request.getMinFee(),
                request.getMaxFee(),
                pageable
        );

        // TODO HATEAOS 적용
        return "ok";
    }

    @GetMapping("/{id}")
    public HouseDetailResponse findHouseInformation(@PathVariable Long id) {
        return houseService.findHouseInformation(id);
    }
}

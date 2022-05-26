package com.airbnb.api.search;

import com.airbnb.api.search.dto.HouseListResponse;
import com.airbnb.api.search.dto.SearchConditionRequest;
import com.airbnb.service.HouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/search")
public class SearchController {

    @Autowired
    private HouseService houseService;

    @GetMapping("")
    public List<HouseListResponse> findHouse(@RequestBody SearchConditionRequest request, Pageable pageable) {
        List<HouseListResponse> results = houseService.findByCondition(request, pageable);
        // TODO HATEAOS 적용
        return results;
    }
}

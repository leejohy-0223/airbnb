package com.airbnb.api.houses;

import com.airbnb.api.houses.dto.AccommodationCostResponse;
import com.airbnb.api.houses.dto.HouseDetailResponse;
import com.airbnb.api.houses.dto.LocationInformationRequest;
import com.airbnb.api.houses.dto.NumberOfHousesByPriceResponse;
import com.airbnb.api.houses.dto.SearchConditionRequest;
import com.airbnb.service.HouseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/houses")
public class HouseController {

    private static final Logger log = LoggerFactory.getLogger(HouseController.class);

    private final HouseService houseService;

    public HouseController(HouseService houseService) {
        this.houseService = houseService;
    }

    @GetMapping
    public List<HouseDetailResponse> findHouse(@RequestBody SearchConditionRequest request) {
        // TODO HATEAOS 적용
        return houseService.findByCondition(request);
    }

    @GetMapping("/{id}")
    public HouseDetailResponse findHouseInformation(@PathVariable Long id) {
        return houseService.findHouseInformation(id);
    }

    @GetMapping("/price")
    public NumberOfHousesByPriceResponse findHouseCount(@ModelAttribute LocationInformationRequest request) {
        return houseService.findHouseCountInLocation(request);
    }

    @GetMapping("/{id}/reservation")
    public AccommodationCostResponse calculateFee(@PathVariable Long id, @RequestParam @DateTimeFormat(pattern = "yyyyMMddHHmm") LocalDateTime startDateTime, @RequestParam @DateTimeFormat(pattern = "yyyyMMddHHmm") LocalDateTime endDateTime) {
        log.info("[time] {}, {}", startDateTime, endDateTime);
        return houseService.calculateFee(id, startDateTime, endDateTime);
    }
}

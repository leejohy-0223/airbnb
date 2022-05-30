package com.airbnb.api.houses;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.airbnb.api.houses.dto.HouseCountResponse;
import com.airbnb.api.houses.dto.HouseDetailResponse;
import com.airbnb.api.houses.dto.LocationInformationRequest;
import com.airbnb.api.houses.dto.SearchConditionRequest;
import com.airbnb.service.HouseService;

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

    /**
     * description : 가격 그래프를 그리기 위한 가격대 별 숙소 개수를 반환합니다.
     * HTTP Method : GET
     * 요청 API : /api/houses/price
     * request : 위치 정보
     * response : 가격대 별 숙소 개수 (10,000 ~ 1,000,000까지 만원 단위)
     */
    @GetMapping("/price")
    public HouseCountResponse findHouseCount(@ModelAttribute LocationInformationRequest request) {
        return houseService.findHouseCountInLocation(request);
    }

    @GetMapping("/test")
    public HouseCountResponse findHouseCount2() {
        log.info("test is called");
        return houseService.findTest();
    }

    @GetMapping("/hello")
    public String hello() {
        return "OK!";
    }
}

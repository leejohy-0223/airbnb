package com.airbnb.api.houses;

import com.airbnb.api.houses.dto.AccommodationCostResponse;
import com.airbnb.api.houses.dto.HouseDetailResponse;
import com.airbnb.api.houses.dto.LocationInformationRequest;
import com.airbnb.api.houses.dto.NumberOfHousesByPriceResponse;
import com.airbnb.api.houses.dto.ReservationDetailResponse;
import com.airbnb.api.houses.dto.ReservationInformationRequest;
import com.airbnb.api.houses.dto.ReservationResponse;
import com.airbnb.api.houses.dto.ReservationsResponse;
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

    @GetMapping("/{id}/calculate")
    public AccommodationCostResponse calculateFee(@PathVariable Long id,
        @RequestParam @DateTimeFormat(pattern = "yyyyMMddHHmm") LocalDateTime startDateTime,
        @RequestParam @DateTimeFormat(pattern = "yyyyMMddHHmm") LocalDateTime endDateTime) {
        log.info("[time] {}, {}", startDateTime, endDateTime);
        return houseService.calculateFee(id, startDateTime, endDateTime);
    }

    /**
     * 예약 진행 API
     * POST /api/houses/{id}/reservation
     * 입력 : start ~ endTime, 최종 요금, 인원
     * 반환 : 숙소 이름, 사진, 체크인, 체크아웃, 호스트 정보, 가격 반환
     */
    @PostMapping("/{id}/reservation")
    public ReservationResponse reserveHouse(@PathVariable Long id, @RequestBody ReservationInformationRequest request,
        @RequestAttribute("userEmail") String userEmail) {
        return houseService.reserveHouse(id, request, userEmail);
    }

    /**
     * 예약 확인 리스트 API
     * GET /api/houses/reservation
     * 입력 : void
     * 반환 : List<Reservation> -> ReservationListResponse
     */
    @GetMapping("/reservation")
    public ReservationsResponse ListReservation(@RequestAttribute("userEmail") String userEmail) {
        return houseService.showReservations(userEmail);
    }

    /**
     * 예약 단건 조회 API
     * GET /api/houses/{id}/reservation
     */
    @GetMapping("/{id}/reservation")
    public ReservationDetailResponse findReservation(@PathVariable Long id) {
        return houseService.findReservation(id);
    }


    /**
     * 예약 단건 취소 API
     * DELETE /api/houses/{id}/reservation
     */
}

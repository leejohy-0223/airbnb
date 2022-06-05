package com.airbnb.api.houses;

import com.airbnb.api.houses.dto.*;
import com.airbnb.common.ResultDto;
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
    public List<HouseDetailResponse> findHouse(@ModelAttribute SearchConditionRequest request) {
        log.info("[HouseController.findHouse] request : {}", request);
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

    @PostMapping("/{id}/reservation")
    public ReservationResponse reserveHouse(@PathVariable Long id, @RequestBody ReservationInformationRequest request,
                                            @RequestAttribute("userEmail") String userEmail) {
        return houseService.reserveHouse(id, request, userEmail);
    }

    @GetMapping("/reservation")
    public ReservationsResponse ListReservation(@RequestAttribute("userEmail") String userEmail) {
        return houseService.showReservations(userEmail);
    }

    /**
     * TODO: id를 통해 예약을 조회하는게 나을지? 아니면 userEmail도 함께 전달해서 체크해야 하는지?
     */
    @GetMapping("/{id}/reservation")
    public ReservationDetailResponse findReservation(@PathVariable Long id, @RequestAttribute String userEmail) {
        return houseService.findReservation(id, userEmail);
    }

    @DeleteMapping("/{id}/reservation")
    public ResultDto cancelReservation(@PathVariable Long id, @RequestAttribute String userEmail) {
        houseService.cancelReservation(id, userEmail);
        return ResultDto.ok();
    }
}

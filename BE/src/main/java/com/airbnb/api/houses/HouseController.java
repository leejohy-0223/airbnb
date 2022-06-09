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
        return houseService.findByCondition(request);
    }

    @GetMapping("/{id}")
    public HouseDetailResponse findHouseInformation(@PathVariable Long id) {
        return houseService.findHouseInformation(id);
    }

    @GetMapping("/price")
    public HousePriceGraphResponse findHouseCount(@ModelAttribute LocationInformationRequest request) {
        return houseService.findHouseCountInLocation(request);
    }

    @GetMapping("/{id}/calculate")
    public AccommodationCostResponse calculateFee(@PathVariable Long id,
                                                  @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime startDateTime,
                                                  @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime endDateTime) {
        return houseService.calculateFee(id, startDateTime, endDateTime);
    }

    @PostMapping("/{id}/reservations")
    public ReservationResponse reserveHouse(@PathVariable Long id, @RequestBody ReservationInformationRequest request,
                                            @RequestAttribute("userEmail") String userEmail) {
        return houseService.reserveHouse(id, request, userEmail);
    }

    @GetMapping("/reservations")
    public ReservationsResponse ListReservation(@RequestAttribute("userEmail") String userEmail) {
        return houseService.showReservations(userEmail);
    }

    @GetMapping("/{id}/reservations")
    public ReservationDetailResponse findReservation(@PathVariable Long id, @RequestAttribute String userEmail) {
        return houseService.findReservation(id, userEmail);
    }

    @DeleteMapping("/{id}/reservations")
    public ResultDto cancelReservation(@PathVariable Long id, @RequestAttribute String userEmail) {
        houseService.cancelReservation(id, userEmail);
        return ResultDto.ok();
    }
}

package com.airbnb.api.users;

import java.time.LocalDateTime;

import com.airbnb.api.houses.HouseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import com.airbnb.api.houses.dto.AccommodationCostResponse;
import com.airbnb.service.HouseService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final HouseService houseService;

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    public UserController(HouseService houseService) {
        this.houseService = houseService;
    }

    @GetMapping("/reservation")
    public AccommodationCostResponse calculateFee(@RequestParam Long houseId, @RequestParam @DateTimeFormat(pattern = "yyyyMMddHHmm") LocalDateTime startDateTime, @RequestParam @DateTimeFormat(pattern = "yyyyMMddHHmm") LocalDateTime endDateTime) {
        log.info("[time] {}, {}", startDateTime, endDateTime);
        return houseService.calculateFee(houseId, startDateTime, endDateTime);
    }

//    @PostMapping("/reservation")
//    public String bookHouse()
}

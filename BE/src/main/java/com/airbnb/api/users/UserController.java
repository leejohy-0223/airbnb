package com.airbnb.api.users;

import com.airbnb.api.houses.dto.AccommodationCostResponse;
import com.airbnb.service.HouseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

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
}

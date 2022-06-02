package com.airbnb.api.users;

import java.time.LocalDateTime;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.airbnb.api.houses.dto.AccommodationCostResponse;
import com.airbnb.service.HouseService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final HouseService houseService;

    public UserController(HouseService houseService) {
        this.houseService = houseService;
    }

    @GetMapping("/reservation")
    public AccommodationCostResponse calculateFee(@RequestParam Long houseId, @RequestParam LocalDateTime startDateTime, @RequestParam LocalDateTime endDateTime) {
        return houseService.calculateFee(houseId, startDateTime, endDateTime);
    }
}

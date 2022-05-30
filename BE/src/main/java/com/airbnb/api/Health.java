package com.airbnb.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class Health {

    @GetMapping("/health")
    public String healtCheck() {
        return "OK!";
    }
}

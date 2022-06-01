package com.airbnb.api.login.oauth;

import com.airbnb.api.login.oauth.dto.LoginResponse;
import com.airbnb.service.LoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/login/oauth")
public class OAuthLoginController {

    private static final Logger log = LoggerFactory.getLogger(OAuthLoginController.class);
    private final LoginService loginService;

    public OAuthLoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @GetMapping("/kakao/callback")
    public LoginResponse OAuthLogin(@RequestParam String code) {
        LoginResponse loginResponse = loginService.login(code);
    }
}

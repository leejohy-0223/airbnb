package com.airbnb.api.login.oauth;

import com.airbnb.api.login.oauth.dto.LoginResponse;
import com.airbnb.service.LoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/login/oauth")
public class OAuthLoginController {

    private static final Logger log = LoggerFactory.getLogger(OAuthLoginController.class);

    private final LoginService loginService;

    public OAuthLoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @GetMapping("/{vendor}/callback")
    public LoginResponse OAuthLogin(@RequestParam String code, @PathVariable String vendor) {
        log.info("[OAuthLogin] : {} OAuth서버에 login을 요청하였습니다, code : {}", vendor, code);
        return loginService.login(code, vendor);
    }

}

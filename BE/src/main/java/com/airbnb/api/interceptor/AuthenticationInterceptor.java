package com.airbnb.api.interceptor;

import com.airbnb.utils.oauth.JwtTokenProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Component
public class AuthenticationInterceptor implements HandlerInterceptor {

    private static final Logger log = LoggerFactory.getLogger(AuthenticationInterceptor.class);
    private final JwtTokenProvider tokenProvider;

    public AuthenticationInterceptor(JwtTokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Optional<String> optionalToken = resolveToken(request);
        if (optionalToken.isEmpty()) {
            throw new IllegalStateException("토큰이 없습니다. 로그인 먼저 해주세요.");
        }
        String userEmail = tokenProvider.parsePayload(optionalToken.get());
        log.debug("token is : {}", optionalToken.get());
        log.debug("userId is : {}", userEmail);
        request.setAttribute("userEmail", userEmail);
        return true;
    }

    private Optional<String> resolveToken(HttpServletRequest request) {
        String authorizationInfo = request.getHeader("Authorization");
        if (authorizationInfo == null) {
            return Optional.empty();
        }
        String[] parts = authorizationInfo.split(" ");
        if (parts.length == 2 && parts[0].equals("Bearer")) {
            return Optional.ofNullable(parts[1]);
        }
        return Optional.empty();
    }
}

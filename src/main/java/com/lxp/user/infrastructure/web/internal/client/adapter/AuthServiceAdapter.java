package com.lxp.user.infrastructure.web.internal.client.adapter;

import com.lxp.user.application.port.required.AuthServicePort;
import com.lxp.user.application.port.required.dto.AuthRegeneratedTokenRequest;
import com.lxp.user.application.port.required.query.AuthRegeneratedTokenResult;
import com.lxp.user.domain.common.exception.UserErrorCode;
import com.lxp.user.domain.common.exception.UserException;
import com.lxp.user.infrastructure.web.internal.client.AuthServiceFeignClient;
import com.lxp.user.infrastructure.web.internal.client.dto.RegenerateTokenRequest;
import com.lxp.user.infrastructure.web.internal.client.dto.RevokeTokenRequest;
import com.lxp.user.infrastructure.web.internal.client.dto.TokenResponse;
import com.lxp.user.infrastructure.web.internal.client.support.ResponseUnwrapper;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthServiceAdapter implements AuthServicePort {

    private final AuthServiceFeignClient authServiceFeignClient;

    @Override
    @CircuitBreaker(name = "authService", fallbackMethod = "regenerateTokenFallBack")
    public AuthRegeneratedTokenResult getRegeneratedToken(AuthRegeneratedTokenRequest request) {
        TokenResponse tokenResponse = ResponseUnwrapper.unwrapResponse(authServiceFeignClient.regenerateToken(
            new RegenerateTokenRequest(request.role())
        ));

        return new AuthRegeneratedTokenResult(tokenResponse.accessToken(), tokenResponse.expiresIn());
    }

    @Override
    @CircuitBreaker(name = "authService", fallbackMethod = "revokeTokenFallBack")
    public void revokeToken(String token) {
        ResponseUnwrapper.unwrapResponse(authServiceFeignClient.revokeToken(new RevokeTokenRequest(token)));
        log.info("Revoke token successfully.");
    }

    private AuthRegeneratedTokenResult regenerateTokenFallBack(AuthRegeneratedTokenRequest request, Throwable t) {
        log.warn("regenerateToken fallback. role={}, reason={}", request.role(), t.toString());
        throw new UserException(UserErrorCode.EXTERNAL_SERVICE_ERROR, "Auth service unavailable", t);
    }

    private void revokeTokenFallBack(String token, Throwable t) {
        log.warn("revokeToken fallback. role={}, reason={}", token, t.toString());
        throw new UserException(UserErrorCode.EXTERNAL_SERVICE_ERROR, "Auth service unavailable", t);
    }
}

package com.lxp.user.infrastructure.web.external.client;

import com.lxp.user.infrastructure.web.external.client.dto.RegenerateTokenRequest;
import com.lxp.user.infrastructure.web.external.client.dto.RevokeTokenRequest;
import com.lxp.user.infrastructure.web.external.client.dto.TokenResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "auth-service", url = "${services.auth-service.url}")
public interface AuthServiceFeignClient {

    @PostMapping("/internal/api-v1/auth/regenerate")
    ResponseEntity<TokenResponse> regenerateToken(@RequestBody RegenerateTokenRequest request);

    @PostMapping("/internal/api-v1/auth/revoke")
    ResponseEntity<Void> revokeToken(@RequestBody RevokeTokenRequest request);
}

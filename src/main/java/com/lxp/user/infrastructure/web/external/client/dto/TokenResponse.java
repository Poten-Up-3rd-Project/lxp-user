package com.lxp.user.infrastructure.web.external.client.dto;

public record TokenResponse(String accessToken, long expiresIn) {
}

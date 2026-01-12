package com.lxp.user.infrastructure.web.internal.client.dto;

public record TokenResponse(String accessToken, long expiresIn) {
}

package com.lxp.user.application.port.provided.dto;

public record AuthTokenResult(String accessToken, long expiresIn) {
}

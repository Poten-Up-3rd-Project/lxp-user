package com.lxp.user.application.port.required.query;

public record AuthRegeneratedTokenResult(String token, long expiresIn) {
}

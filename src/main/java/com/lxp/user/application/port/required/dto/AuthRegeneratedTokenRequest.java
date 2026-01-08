package com.lxp.user.application.port.required.dto;

import java.util.List;

public record AuthRegeneratedTokenRequest(
    String email, String token, List<String> role
) {
}

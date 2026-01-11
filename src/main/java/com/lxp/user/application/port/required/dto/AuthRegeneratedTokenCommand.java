package com.lxp.user.application.port.required.dto;

import java.util.List;

public record AuthRegeneratedTokenCommand(
    String email, String token, List<String> role
) {
}

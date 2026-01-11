package com.lxp.user.infrastructure.web.internal.client.dto;

import java.util.List;

public record RegenerateTokenRequest(
    String email,
    String token,
    List<String> roles
) {
}

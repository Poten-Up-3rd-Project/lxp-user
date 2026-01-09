package com.lxp.user.infrastructure.web.internal.client.dto;

import java.util.List;

public record RegenerateTokenRequest(
    List<String> roles
) {
}

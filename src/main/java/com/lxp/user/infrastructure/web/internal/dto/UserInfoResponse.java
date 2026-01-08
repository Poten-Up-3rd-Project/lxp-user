package com.lxp.user.infrastructure.web.internal.dto;

import java.util.List;

public record UserInfoResponse(
    String id,
    String name,
    String email,
    String role,
    List<Long> tagIds,
    String level
) {
}

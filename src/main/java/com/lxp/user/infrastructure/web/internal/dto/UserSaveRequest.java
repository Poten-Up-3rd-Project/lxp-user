package com.lxp.user.infrastructure.web.internal.dto;

import java.util.List;

public record UserSaveRequest(
    String userId,
    String name,
    String email,
    String role,
    List<Long> tagIds,
    String level
) {
}

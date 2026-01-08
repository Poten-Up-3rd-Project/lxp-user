package com.lxp.user.application.port.provided.dto;

import java.util.List;

public record UserInfoInternalResult(
    String id,
    String name,
    String email,
    String role,
    List<Long> tagIds,
    String level
) {
}

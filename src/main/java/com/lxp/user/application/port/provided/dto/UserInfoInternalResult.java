package com.lxp.user.application.port.provided.dto;

import java.time.LocalDateTime;

public record UserInfoInternalResult(
    String id,
    String name,
    String email,
    String role,
    String status,
    LocalDateTime deletedAt
) {
}

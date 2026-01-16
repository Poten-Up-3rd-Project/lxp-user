package com.lxp.user.application.port.provided.dto;

import java.time.LocalDateTime;

public record UserRoleInternalResult(String role, String status, LocalDateTime deletedAt) {
}

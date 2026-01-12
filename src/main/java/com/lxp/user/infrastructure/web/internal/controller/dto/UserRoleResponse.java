package com.lxp.user.infrastructure.web.internal.controller.dto;

import java.time.LocalDateTime;

public record UserRoleResponse(String role, String status, LocalDateTime deletedAt) {
}

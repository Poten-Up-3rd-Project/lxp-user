package com.lxp.user.infrastructure.persistence.read.dto;

import com.lxp.user.infrastructure.persistence.vo.InfraUserRole;
import com.lxp.user.infrastructure.persistence.vo.InfraUserStatus;

import java.time.LocalDateTime;

public record UserSummaryDto(
    String id,
    String name,
    String email,
    InfraUserRole role,
    InfraUserStatus userStatus,
    LocalDateTime deletedAt
) {
}

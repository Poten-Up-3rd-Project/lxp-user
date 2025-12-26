package com.lxp.user.infrastructure.persistence.read.dto;

import com.lxp.user.infrastructure.persistence.vo.InfraLevel;
import com.lxp.user.infrastructure.persistence.vo.InfraUserRole;
import com.lxp.user.infrastructure.persistence.vo.InfraUserStatus;

import java.time.LocalDateTime;
import java.util.List;

public record UserDetailDto(
    String id,
    String name,
    String email,
    InfraUserRole role,
    InfraUserStatus status,
    InfraLevel level,
    List<Long> tags,
    LocalDateTime createdAt,
    LocalDateTime deletedAt
) {
}

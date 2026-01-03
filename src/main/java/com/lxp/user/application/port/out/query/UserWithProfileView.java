package com.lxp.user.application.port.out.query;

import com.lxp.user.domain.common.model.vo.Level;
import com.lxp.user.domain.common.model.vo.UserId;
import com.lxp.user.domain.user.model.vo.UserEmail;
import com.lxp.user.domain.user.model.vo.UserName;
import com.lxp.user.domain.user.model.vo.UserRole;
import com.lxp.user.domain.user.model.vo.UserStatus;

import java.time.LocalDateTime;
import java.util.List;

public record UserWithProfileView(
    UserId userId,
    UserName name,
    UserEmail email,
    UserRole role,
    UserStatus status,
    Level level,
    List<Long> tagIds,
    LocalDateTime createdAt,
    LocalDateTime deletedAt
) {
}

package com.lxp.user.application.port.required.query;

import com.lxp.user.domain.common.model.vo.UserId;
import com.lxp.user.domain.user.model.vo.UserEmail;
import com.lxp.user.domain.user.model.vo.UserName;
import com.lxp.user.domain.user.model.vo.UserRole;
import com.lxp.user.domain.user.model.vo.UserStatus;

import java.time.LocalDateTime;

public record UserView(
    UserId userId,
    UserName name,
    UserEmail email,
    UserRole role,
    UserStatus userStatus,
    LocalDateTime deletedAt
) {
}

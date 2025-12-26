package com.lxp.user.infrastructure.persistence.vo;

import com.lxp.user.domain.user.model.vo.UserStatus;

public enum InfraUserStatus {
    ACTIVE,
    DELETED;

    public static InfraUserStatus from(UserStatus domain) {
        return valueOf(domain.name());
    }

    public UserStatus toDomain() {
        return UserStatus.valueOf(this.name());
    }
}

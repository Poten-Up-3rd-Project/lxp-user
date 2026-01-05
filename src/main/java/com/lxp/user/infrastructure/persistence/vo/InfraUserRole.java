package com.lxp.user.infrastructure.persistence.vo;

import com.lxp.user.domain.user.model.vo.UserRole;

public enum InfraUserRole {

    LEARNER("학습자"), INSTRUCTOR("강사"), ADMIN("관리자");

    private final String description;

    InfraUserRole(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static InfraUserRole from(UserRole domain) {
        return valueOf(domain.name());
    }

    public UserRole toDomain() {
        return UserRole.valueOf(this.name());
    }
}

package com.lxp.user.infrastructure.persistence.vo;

import com.lxp.user.domain.common.model.vo.Level;

public enum InfraLevel {
    JUNIOR("주니어"),
    MIDDLE("미들"),
    SENIOR("시니어"),
    EXPERT("익스퍼트");

    private final String description;

    InfraLevel(String description) {
        this.description = description;
    }

    public String description() {
        return description;
    }

    // ✅ Domain → Infrastructure
    public static InfraLevel from(Level domain) {
        return valueOf(domain.name());
    }

    // ✅ Infrastructure → Domain
    public Level toDomain() {
        return Level.valueOf(this.name());
    }
}

package com.lxp.user.domain.user.service.spec;

import com.lxp.user.domain.user.model.vo.UserRole;

import java.util.List;

public record UserSaveSpec(
    String userId,
    String email,
    String name,
    UserRole role,
    List<Long> tagIds,
    String level
) {
}

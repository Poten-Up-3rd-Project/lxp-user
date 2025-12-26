package com.lxp.user.infrastructure.persistence.read.dto;

import com.lxp.user.infrastructure.persistence.vo.InfraUserStatus;

public interface UserStatusProjection {
    String getId();

    InfraUserStatus getUserStatus();
}

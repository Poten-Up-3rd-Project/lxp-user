package com.lxp.user.application.port.in.command;

import com.lxp.common.application.cqrs.Command;
import com.lxp.user.domain.user.exception.UserRoleNotFoundException;
import com.lxp.user.domain.user.model.vo.UserRole;
import com.lxp.user.domain.user.service.spec.UserSaveSpec;

import java.util.List;

public record UserSaveCommand(
    String userId,
    String email,
    String name,
    String role,

    List<Long> tagIds,
    String level
) implements Command {

    public UserSaveSpec toSpec() {
        UserRole userRole = UserRole.fromString(role).orElseThrow(UserRoleNotFoundException::new);
        return new UserSaveSpec(
            this.userId,
            this.email,
            this.name,
            userRole,
            this.tagIds,
            level
        );
    }

}


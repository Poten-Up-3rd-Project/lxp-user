package com.lxp.user.application.port.provided.usecase;

import com.lxp.common.application.port.in.CommandWithResultUseCase;
import com.lxp.user.application.port.provided.command.UserRoleUpdateCommand;
import com.lxp.user.application.port.required.query.AuthRegeneratedTokenResult;

@FunctionalInterface
public interface UserRoleUpdateUseCase extends CommandWithResultUseCase<UserRoleUpdateCommand, AuthRegeneratedTokenResult> {
}

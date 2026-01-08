package com.lxp.user.application.port.provided.usecase;

import com.lxp.common.application.port.in.CommandUseCase;
import com.lxp.user.application.port.provided.command.UserSaveInternalCommand;

@FunctionalInterface
public interface UserSaveUseCase extends CommandUseCase<UserSaveInternalCommand> {
}

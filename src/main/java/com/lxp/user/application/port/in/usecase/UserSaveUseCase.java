package com.lxp.user.application.port.in.usecase;

import com.lxp.common.application.port.in.CommandWithResultUseCase;
import com.lxp.user.application.port.in.command.UserSaveCommand;

public interface UserSaveUseCase extends CommandWithResultUseCase<UserSaveCommand, Void> {
}

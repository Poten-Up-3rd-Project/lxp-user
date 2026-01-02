package com.lxp.user.application.in.usecase;

import com.lxp.common.application.port.in.CommandWithResultUseCase;
import com.lxp.user.application.in.command.UserSaveCommand;

public interface UserSaveUseCase extends CommandWithResultUseCase<UserSaveCommand, Void> {
}

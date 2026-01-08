package com.lxp.user.application.port.provided.usecase;

import com.lxp.common.application.port.in.CommandWithResultUseCase;
import com.lxp.user.application.port.provided.command.UserUpdateCommand;
import com.lxp.user.application.port.provided.dto.UserInfoResult;

@FunctionalInterface
public interface UserUpdateUseCase extends CommandWithResultUseCase<UserUpdateCommand, UserInfoResult> {
}

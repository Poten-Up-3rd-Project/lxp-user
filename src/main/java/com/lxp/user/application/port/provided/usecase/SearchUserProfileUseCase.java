package com.lxp.user.application.port.provided.usecase;

import com.lxp.common.application.port.in.CommandWithResultUseCase;
import com.lxp.user.application.port.provided.command.UserSearchCommand;
import com.lxp.user.application.port.provided.dto.UserInfoResult;

@FunctionalInterface
public interface SearchUserProfileUseCase extends CommandWithResultUseCase<UserSearchCommand, UserInfoResult> {
}

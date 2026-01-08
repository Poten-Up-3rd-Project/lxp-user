package com.lxp.user.application.port.provided.usecase;

import com.lxp.common.application.port.in.CommandWithResultUseCase;
import com.lxp.user.application.port.provided.dto.UserInfoInternalResult;

@FunctionalInterface
public interface UserFindInternalUseCase extends CommandWithResultUseCase<String, UserInfoInternalResult> {
}

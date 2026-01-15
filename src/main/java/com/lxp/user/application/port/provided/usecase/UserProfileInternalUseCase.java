package com.lxp.user.application.port.provided.usecase;

import com.lxp.common.application.port.in.QueryUseCase;
import com.lxp.user.application.port.provided.dto.UserProfileInternalResult;

@FunctionalInterface
public interface UserProfileInternalUseCase extends QueryUseCase<String, UserProfileInternalResult> {
}

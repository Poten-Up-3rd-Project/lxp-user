package com.lxp.user.application.port.provided.usecase;

import com.lxp.common.application.port.in.QueryUseCase;
import com.lxp.user.application.port.provided.dto.UserRoleInternalResult;

@FunctionalInterface
public interface UserFindRoleInternalUseCase extends QueryUseCase<String, UserRoleInternalResult> {
}

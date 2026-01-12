package com.lxp.user.application.port.provided.usecase;

import com.lxp.common.application.port.in.QueryUseCase;
import com.lxp.user.application.port.provided.dto.UserInfoInternalResult;

@FunctionalInterface
public interface UserFindInternalUseCase extends QueryUseCase<String, UserInfoInternalResult> {
}

package com.lxp.user.application.port.provided.usecase;

import com.lxp.common.application.port.in.QueryUseCase;
import com.lxp.user.application.port.provided.command.UserSearchCommand;
import com.lxp.user.application.port.provided.dto.UserSearchQuery;

@FunctionalInterface
public interface SearchUserProfileUseCase extends QueryUseCase<UserSearchCommand, UserSearchQuery> {
}

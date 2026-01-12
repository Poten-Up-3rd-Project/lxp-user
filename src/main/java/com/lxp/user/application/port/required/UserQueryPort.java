package com.lxp.user.application.port.required;

import com.lxp.user.application.port.required.query.UserView;
import com.lxp.user.application.port.required.query.UserWithProfileView;
import com.lxp.user.domain.common.model.vo.UserId;
import com.lxp.user.domain.user.model.vo.UserEmail;
import com.lxp.user.domain.user.model.vo.UserStatus;

import java.util.Optional;

public interface UserQueryPort {

    Optional<UserView> getUserById(UserId id);

    Optional<UserView> getUserByEmail(UserEmail email);

    Optional<UserStatus> findUserStatusById(UserId userId);

    Optional<UserWithProfileView> findAggregateUserById(UserId userId);

    Optional<UserWithProfileView> findAggregateUserByEmail(UserEmail email);
}

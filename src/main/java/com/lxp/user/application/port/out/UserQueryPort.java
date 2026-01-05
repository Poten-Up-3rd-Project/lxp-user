package com.lxp.user.application.port.out;

import com.lxp.user.application.port.out.query.UserView;
import com.lxp.user.application.port.out.query.UserWithProfileView;
import com.lxp.user.domain.common.model.vo.UserId;
import com.lxp.user.domain.user.model.vo.UserStatus;

import java.util.Optional;

public interface UserQueryPort {

    Optional<UserView> getUserById(String id);

    Optional<UserView> getUserByEmail(String email);

    Optional<UserStatus> findUserStatusById(UserId userId);

    Optional<UserWithProfileView> findAggregateUserById(UserId userId);

    Optional<UserWithProfileView> findAggregateUserByEmail(String email);
}

package com.lxp.user.application.out;

import com.lxp.user.domain.common.model.vo.UserId;
import com.lxp.user.domain.user.model.entity.User;
import com.lxp.user.domain.user.model.vo.UserStatus;

import java.util.Optional;

public interface UserQueryPort {

    Optional<User> getUserById(String id);

    Optional<User> getUserByEmail(String email);

    Optional<UserStatus> findUserStatusById(UserId userId);

    Optional<User> findAggregateUserById(UserId userId);

    Optional<User> findAggregateUserByEmail(String email);
}

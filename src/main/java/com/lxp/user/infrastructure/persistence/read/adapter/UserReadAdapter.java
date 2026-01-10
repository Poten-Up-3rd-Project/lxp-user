package com.lxp.user.infrastructure.persistence.read.adapter;

import com.lxp.user.application.port.required.UserQueryPort;
import com.lxp.user.application.port.required.query.UserView;
import com.lxp.user.application.port.required.query.UserWithProfileView;
import com.lxp.user.domain.common.model.vo.UserId;
import com.lxp.user.domain.user.model.vo.UserEmail;
import com.lxp.user.domain.user.model.vo.UserStatus;
import com.lxp.user.infrastructure.persistence.read.repository.UserReadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserReadAdapter implements UserQueryPort {

    private final UserReadRepository userReadRepository;
    private final UserReadMapper userReadMapper;

    @Override
    public Optional<UserView> getUserById(UserId id) {
        return userReadRepository.findUserSummaryById(id.asString()).map(userReadMapper::toUserView);
    }

    @Override
    public Optional<UserView> getUserByEmail(UserEmail email) {
        return userReadRepository.findUserSummaryByEmail(email.value()).map(userReadMapper::toUserView);
    }

    @Override
    public Optional<UserStatus> findUserStatusById(UserId userId) {
        return userReadRepository.findUserStatusById(userId.asString())
            .map(status -> status.getUserStatus().toDomain());
    }

    @Override
    public Optional<UserWithProfileView> findAggregateUserById(UserId userId) {
        return userReadRepository.findUserDetailById(userId.asString())
            .map(detailDto -> userReadMapper.toUserWithProfileView(
                detailDto,
                userReadRepository.findTagsByUserId(userId.asString())
            ));
    }

    @Override
    public Optional<UserWithProfileView> findAggregateUserByEmail(UserEmail email) {
        return userReadRepository.findUserDetailByEmail(email.value())
            .map(detailDto -> userReadMapper.toUserWithProfileView(
                detailDto,
                userReadRepository.findTagsByUserId(detailDto.id())
            ));
    }

}

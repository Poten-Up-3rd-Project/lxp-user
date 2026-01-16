package com.lxp.user.application.service.internal;

import com.lxp.user.application.port.provided.usecase.UserProfileInternalUseCase;
import com.lxp.user.application.port.required.UserQueryPort;
import com.lxp.user.application.port.provided.dto.UserProfileInternalResult;
import com.lxp.user.application.port.required.query.UserWithProfileView;
import com.lxp.user.application.service.mapper.UserServiceMapper;
import com.lxp.user.domain.common.exception.UserNotFoundException;
import com.lxp.user.domain.common.model.vo.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserProfileInternalService implements UserProfileInternalUseCase {

    private final UserQueryPort userQueryPort;
    private final UserServiceMapper userServiceMapper;

    @Override
    public UserProfileInternalResult execute(String userId) {
        UserWithProfileView userWithProfileView = userQueryPort.findAggregateUserById(UserId.of(userId))
            .orElseThrow(UserNotFoundException::new);
        return userServiceMapper.toUserProfileInternalResult(userWithProfileView);
    }
}

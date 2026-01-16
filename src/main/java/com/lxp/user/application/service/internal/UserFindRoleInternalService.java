package com.lxp.user.application.service.internal;

import com.lxp.user.application.port.provided.dto.UserRoleInternalResult;
import com.lxp.user.application.port.provided.usecase.UserFindRoleInternalUseCase;
import com.lxp.user.application.port.required.UserQueryPort;
import com.lxp.user.application.port.required.query.UserView;
import com.lxp.user.application.service.mapper.UserServiceMapper;
import com.lxp.user.domain.common.exception.UserNotFoundException;
import com.lxp.user.domain.common.model.vo.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserFindRoleInternalService implements UserFindRoleInternalUseCase {

    private final UserQueryPort userQueryPort;
    private final UserServiceMapper userServiceMapper;

    @Override
    public UserRoleInternalResult execute(String userId) {
        UserView userView = userQueryPort.getUserById(UserId.of(userId)).orElseThrow(UserNotFoundException::new);
        return userServiceMapper.toUserRoleInternalResult(userView);
    }
}

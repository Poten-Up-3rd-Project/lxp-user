package com.lxp.user.application.service.internal;

import com.lxp.user.application.port.provided.dto.UserInfoInternalResult;
import com.lxp.user.application.port.provided.usecase.UserInfoInternalUseCase;
import com.lxp.user.application.port.required.UserQueryPort;
import com.lxp.user.application.port.required.query.UserView;
import com.lxp.user.application.service.mapper.UserServiceMapper;
import com.lxp.user.domain.common.exception.UserNotFoundException;
import com.lxp.user.domain.common.model.vo.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserInfoInternalService implements UserInfoInternalUseCase {

    private final UserQueryPort userQueryPort;
    private final UserServiceMapper userServiceMapper;

    @Override
    public UserInfoInternalResult execute(String userId) {
        UserView view = userQueryPort.getUserById(UserId.of(userId)).orElseThrow(UserNotFoundException::new);
        return userServiceMapper.toUserInfoInternalResult(view);
    }
}

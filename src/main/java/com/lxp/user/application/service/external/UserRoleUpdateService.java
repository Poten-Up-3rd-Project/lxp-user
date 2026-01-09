package com.lxp.user.application.service.external;

import com.lxp.user.application.port.provided.command.UserRoleUpdateCommand;
import com.lxp.user.application.port.provided.usecase.UserRoleUpdateUseCase;
import com.lxp.user.application.port.required.AuthServicePort;
import com.lxp.user.application.port.required.UserCommandPort;
import com.lxp.user.application.port.required.UserQueryPort;
import com.lxp.user.application.port.required.dto.AuthRegeneratedTokenRequest;
import com.lxp.user.application.port.required.query.AuthRegeneratedTokenResult;
import com.lxp.user.application.port.required.query.UserView;
import com.lxp.user.application.service.mapper.UserServiceMapper;
import com.lxp.user.domain.common.exception.UserNotFoundException;
import com.lxp.user.domain.common.model.vo.UserId;
import com.lxp.user.domain.user.model.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Throwable.class)
public class UserRoleUpdateService implements UserRoleUpdateUseCase {

    private final UserQueryPort userQueryPort;
    private final UserCommandPort userCommandPort;
    private final UserServiceMapper userServiceMapper;
    private final AuthServicePort authServicePort;

    @Override
    public AuthRegeneratedTokenResult execute(UserRoleUpdateCommand command) {
        UserView view = userQueryPort.getUserById(UserId.of(command.userId()))
            .orElseThrow(UserNotFoundException::new);
        User user = userServiceMapper.toDomain(view);
        user.makeInstructor();
        userCommandPort.save(user);

        return authServicePort.getRegeneratedToken(
            new AuthRegeneratedTokenRequest(user.email(), command.token(), List.of(user.role().name()))
        );
    }
}

package com.lxp.user.application.service.external;

import com.lxp.user.application.port.provided.command.UserWithdrawnCommand;
import com.lxp.user.application.port.provided.usecase.UserWithdrawnUseCase;
import com.lxp.user.application.port.required.AuthServicePort;
import com.lxp.user.application.port.required.UserCommandPort;
import com.lxp.user.application.port.required.UserQueryPort;
import com.lxp.user.application.port.required.query.UserView;
import com.lxp.user.application.service.mapper.UserServiceMapper;
import com.lxp.user.domain.common.exception.UserNotFoundException;
import com.lxp.user.domain.common.model.vo.UserId;
import com.lxp.user.domain.user.model.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Throwable.class)
public class UserWithdrawnService implements UserWithdrawnUseCase {

    private final UserQueryPort userQueryPort;
    private final UserCommandPort userCommandPort;
    private final AuthServicePort authServicePort;
    private final UserServiceMapper userServiceMapper;

    @Override
    public void execute(UserWithdrawnCommand command) {
        UserView userView = userQueryPort.getUserById(UserId.of(command.userId())).orElseThrow(UserNotFoundException::new);
        User user = userServiceMapper.toDomain(userView);
        user.withdraw();
        userCommandPort.deactivate(user);

        authServicePort.revokeToken(command.token());
    }
}

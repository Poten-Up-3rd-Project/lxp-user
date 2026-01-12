package com.lxp.user.application.service.external;

import com.lxp.user.application.port.provided.command.UserRoleUpdateCommand;
import com.lxp.user.application.port.provided.usecase.UserRoleUpdateUseCase;
import com.lxp.user.application.port.required.AuthServicePort;
import com.lxp.user.application.port.required.UserPort;
import com.lxp.user.application.port.required.dto.AuthRegeneratedTokenCommand;
import com.lxp.user.application.port.required.query.AuthRegeneratedTokenResult;
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

    private final UserPort userPort;
    private final AuthServicePort authServicePort;

    @Override
    public AuthRegeneratedTokenResult execute(UserRoleUpdateCommand command) {
        User user = userPort.loadById(UserId.of(command.userId()));
        user.makeInstructor();
        userPort.save(user);

        return authServicePort.getRegeneratedToken(
            new AuthRegeneratedTokenCommand(user.email(), command.token(), List.of(user.role().name()))
        );
    }
}

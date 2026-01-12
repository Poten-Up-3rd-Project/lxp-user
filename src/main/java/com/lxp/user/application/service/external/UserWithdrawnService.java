package com.lxp.user.application.service.external;

import com.lxp.user.application.port.provided.command.UserWithdrawnCommand;
import com.lxp.user.application.port.provided.usecase.UserWithdrawnUseCase;
import com.lxp.user.application.port.required.AuthServicePort;
import com.lxp.user.application.port.required.UserPort;
import com.lxp.user.domain.common.model.vo.UserId;
import com.lxp.user.domain.user.model.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Throwable.class)
public class UserWithdrawnService implements UserWithdrawnUseCase {

    private final UserPort userPort;
    private final AuthServicePort authServicePort;

    @Override
    public void execute(UserWithdrawnCommand command) {
        User user = userPort.loadById(UserId.of(command.userId()));
        user.withdraw();
        userPort.deactivate(user);

        authServicePort.revokeToken(command.token());
    }
}

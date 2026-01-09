package com.lxp.user.application.service.internal;

import com.lxp.user.application.port.provided.command.UserSaveInternalCommand;
import com.lxp.user.application.port.provided.usecase.UserSaveUseCase;
import com.lxp.user.application.port.required.UserCommandPort;
import com.lxp.user.domain.user.model.entity.User;
import com.lxp.user.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Throwable.class)
public class UserSaveInternalService implements UserSaveUseCase {

    private final UserService userService;
    private final UserCommandPort userCommandPort;

    @Override
    public void execute(UserSaveInternalCommand command) {
        User user = userService.create(command.toSpec());
        userCommandPort.saveWithProfile(user);
    }
}

package com.lxp.user.application.service;

import com.lxp.user.application.port.in.command.UserSaveCommand;
import com.lxp.user.application.port.in.usecase.UserSaveUseCase;
import com.lxp.user.domain.user.model.entity.User;
import com.lxp.user.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserSaveService implements UserSaveUseCase {

    private final UserService userService;

    @Override
    public Void execute(UserSaveCommand command) {
        try {
            User user = userService.create(command.toSpec());
        } catch (Exception e) {

        }

        return null;
    }
}

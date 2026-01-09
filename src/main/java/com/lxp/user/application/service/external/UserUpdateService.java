package com.lxp.user.application.service.external;

import com.lxp.user.application.port.provided.command.UserUpdateCommand;
import com.lxp.user.application.port.provided.dto.UserInfoResult;
import com.lxp.user.application.port.provided.usecase.UserUpdateUseCase;
import com.lxp.user.application.port.required.TagServicePort;
import com.lxp.user.application.port.required.UserCommandPort;
import com.lxp.user.application.port.required.UserQueryPort;
import com.lxp.user.application.port.required.query.TagResult;
import com.lxp.user.application.port.required.query.UserWithProfileView;
import com.lxp.user.application.service.mapper.UserServiceMapper;
import com.lxp.user.domain.common.exception.UserNotFoundException;
import com.lxp.user.domain.user.model.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Throwable.class)
public class UserUpdateService implements UserUpdateUseCase {

    private final UserQueryPort userQueryPort;
    private final UserCommandPort userCommandPort;
    private final UserServiceMapper userServiceMapper;
    private final TagServicePort tagServicePort;

    @Override
    public UserInfoResult execute(UserUpdateCommand command) {
        UserWithProfileView view = userQueryPort.findAggregateUserById(command.userId())
            .orElseThrow(UserNotFoundException::new);

        User user = userServiceMapper.toDomain(view);

        user.update(
            command.name(),
            command.level(),
            command.tagIds()
        );

        List<TagResult> tags;
        if (user.hasProfile()) {
            userCommandPort.saveWithProfile(user);
            tags = tagServicePort.findTags(user.profile().tags().values());
        } else {
            userCommandPort.save(user);
            tags = new ArrayList<>();
        }

        return userServiceMapper.toUserInfoDto(user, tags);
    }
}

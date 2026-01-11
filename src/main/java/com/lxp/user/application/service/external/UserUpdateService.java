package com.lxp.user.application.service.external;

import com.lxp.user.application.port.provided.command.UserUpdateCommand;
import com.lxp.user.application.port.provided.dto.UserSearchQuery;
import com.lxp.user.application.port.provided.usecase.UserUpdateUseCase;
import com.lxp.user.application.port.required.TagServicePort;
import com.lxp.user.application.port.required.UserPort;
import com.lxp.user.application.port.required.query.TagResult;
import com.lxp.user.application.service.mapper.UserServiceMapper;
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

    private final UserPort userPort;
    private final UserServiceMapper userServiceMapper;
    private final TagServicePort tagServicePort;

    @Override
    public UserSearchQuery execute(UserUpdateCommand command) {
        User user = userPort.loadById(command.userId());
        user.update(command.name(), command.level(), command.tagIds());

        List<TagResult> tags;
        if (user.hasProfile()) {
            userPort.saveWithProfile(user);
            tags = tagServicePort.findTags(user.profile().tags().values());
        } else {
            userPort.save(user);
            tags = new ArrayList<>();
        }

        return userServiceMapper.toUserInfoDto(user, tags);
    }
}

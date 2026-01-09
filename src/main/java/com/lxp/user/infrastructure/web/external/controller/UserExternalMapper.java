package com.lxp.user.infrastructure.web.external.controller;

import com.lxp.user.application.port.provided.command.UserUpdateCommand;
import com.lxp.user.application.port.provided.dto.UserSearchQuery;
import com.lxp.user.domain.common.model.vo.Level;
import com.lxp.user.domain.common.model.vo.UserId;
import com.lxp.user.domain.profile.exception.LearnerLevelNotFoundException;
import com.lxp.user.domain.user.model.vo.UserName;
import com.lxp.user.infrastructure.web.external.controller.dto.request.UserUpdateRequest;
import com.lxp.user.infrastructure.web.external.controller.dto.response.UserProfileResponse;
import org.springframework.stereotype.Component;

@Component
public class UserExternalMapper {

    public UserProfileResponse toUserProfileResponse(UserSearchQuery result) {
        return new UserProfileResponse(
            result.id(), result.email(), result.name(), result.tags(), result.level()
        );
    }

    public UserUpdateCommand toUserUpdateCommand(String userId, UserUpdateRequest request) {
        Level level = Level.fromString(request.level()).orElseThrow(LearnerLevelNotFoundException::new);
        return new UserUpdateCommand(
            UserId.of(userId), UserName.of(request.name()), level, request.tagIds()
        );
    }

}

package com.lxp.user.infrastructure.web.internal.controller.mapper;

import com.lxp.user.application.port.provided.command.UserSaveInternalCommand;
import com.lxp.user.application.port.provided.dto.UserInfoInternalResult;
import com.lxp.user.infrastructure.web.internal.controller.dto.UserInfoResponse;
import com.lxp.user.infrastructure.web.internal.controller.dto.UserSaveRequest;
import org.springframework.stereotype.Component;

@Component
public class UserInternalMapper {

    public UserSaveInternalCommand toUserSaveCommand(UserSaveRequest request) {
        return new UserSaveInternalCommand(
            request.userId(),
            request.email(),
            request.name(),
            request.role(),
            request.tagIds(),
            request.level()
        );
    }

    public UserInfoResponse toUserInfoResponse(UserInfoInternalResult userInfoResult) {
        return new UserInfoResponse(
            userInfoResult.id(),
            userInfoResult.name(),
            userInfoResult.email(),
            userInfoResult.role(),
            userInfoResult.tagIds(),
            userInfoResult.level()
        );
    }

}

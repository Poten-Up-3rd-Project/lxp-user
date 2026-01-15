package com.lxp.user.application.service.mapper;

import com.lxp.user.application.port.provided.dto.TagExternalResult;
import com.lxp.user.application.port.provided.dto.UserInfoInternalResult;
import com.lxp.user.application.port.provided.dto.UserProfileInternalResult;
import com.lxp.user.application.port.provided.dto.UserRoleInternalResult;
import com.lxp.user.application.port.provided.dto.UserSearchQuery;
import com.lxp.user.application.port.required.query.TagResult;
import com.lxp.user.application.port.required.query.UserView;
import com.lxp.user.application.port.required.query.UserWithProfileView;
import com.lxp.user.domain.user.model.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserServiceMapper {

    public UserSearchQuery toUserInfoDto(User user, List<TagResult> tagResults) {
        return new UserSearchQuery(
            user.id().asString(),
            user.name(),
            user.email(),
            user.role().name(),
            tagResults.stream().map(tagResult ->
                new TagExternalResult(tagResult.id(), tagResult.content(), tagResult.color(), tagResult.variant())
            ).toList(),
            user.profile().level().name()
        );
    }

    public UserProfileInternalResult toUserProfileInternalResult(UserWithProfileView view) {
        return new UserProfileInternalResult(
            view.userId().asString(),
            view.tagIds(),
            view.role().name()
        );
    }

    public UserRoleInternalResult toUserRoleInternalResult(UserView view) {
        return new UserRoleInternalResult(
            view.role().name(),
            view.userStatus().name(),
            view.deletedAt()
        );
    }

    public UserInfoInternalResult toUserInfoInternalResult(UserView view) {
        return new UserInfoInternalResult(
            view.userId().asString(),
            view.name().value(),
            view.email().value(),
            view.role().name(),
            view.userStatus().name(),
            view.deletedAt());
    }

}

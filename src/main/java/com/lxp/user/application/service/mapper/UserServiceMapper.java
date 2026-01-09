package com.lxp.user.application.service.mapper;

import com.lxp.user.application.port.provided.dto.TagExternalResult;
import com.lxp.user.application.port.provided.dto.UserInfoInternalResult;
import com.lxp.user.application.port.provided.dto.UserSearchQuery;
import com.lxp.user.application.port.required.query.TagResult;
import com.lxp.user.application.port.required.query.UserView;
import com.lxp.user.application.port.required.query.UserWithProfileView;
import com.lxp.user.domain.profile.model.entity.UserProfile;
import com.lxp.user.domain.profile.model.vo.Tags;
import com.lxp.user.domain.user.model.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserServiceMapper {

    public User toDomain(UserWithProfileView view) {
        return User.of(
            view.userId(),
            view.name(),
            view.email(),
            view.role(),
            view.status(),
            UserProfile.create(
                view.userId(),
                view.level(),
                new Tags(view.tagIds())
            ),
            view.deletedAt()
        );
    }

    public User toDomain(UserView view) {
        return User.of(
            view.userId(),
            view.name(),
            view.email(),
            view.role(),
            view.userStatus(),
            null,
            view.deletedAt()
        );
    }

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

    public UserInfoInternalResult toUserInfoInternalResult(UserWithProfileView view) {
        return new UserInfoInternalResult(
            view.userId().asString(),
            view.name().value(),
            view.email().value(),
            view.role().name(),
            view.tagIds(),
            view.level().name()
        );
    }

}

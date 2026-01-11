package com.lxp.user.application.service.external;

import com.lxp.user.application.port.provided.command.UserSearchCommand;
import com.lxp.user.application.port.provided.dto.TagExternalResult;
import com.lxp.user.application.port.provided.dto.UserSearchQuery;
import com.lxp.user.application.port.provided.usecase.SearchUserProfileUseCase;
import com.lxp.user.application.port.required.TagServicePort;
import com.lxp.user.application.port.required.UserQueryPort;
import com.lxp.user.application.port.required.query.UserWithProfileView;
import com.lxp.user.domain.common.exception.UserNotFoundException;
import com.lxp.user.domain.common.model.vo.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchUserProfileService implements SearchUserProfileUseCase {

    private final UserQueryPort userQueryPort;
    private final TagServicePort tagServicePort;

    @Override
    public UserSearchQuery execute(UserSearchCommand command) {
        UserWithProfileView view = userQueryPort.findAggregateUserById(UserId.of(command.userId()))
            .orElseThrow(UserNotFoundException::new);

        List<TagExternalResult> results = tagServicePort.findTags(view.tagIds()).stream().map(tag ->
            new TagExternalResult(tag.id(), tag.content(), tag.color(), tag.variant())
        ).toList();
        return new UserSearchQuery(
            command.userId(),
            view.name().value(),
            view.email().value(),
            view.role().name(),
            results,
            view.level().name()
        );
    }
}

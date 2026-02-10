package com.lxp.user.application.event.mapper;

import com.lxp.common.application.event.IntegrationEvent;
import com.lxp.user.application.event.integration.UserRegisteredRecommendIntegrationEvent;
import com.lxp.user.application.event.payload.UserCreatedPayload;
import com.lxp.user.domain.user.event.UserCreatedEvent;
import org.springframework.stereotype.Component;

@Component
public class UserCreatedEventMapper implements EventToIntegrationMapper<UserCreatedEvent> {

    @Override
    public IntegrationEvent map(UserCreatedEvent event) {
        return new UserRegisteredRecommendIntegrationEvent(
            event.getEventId(),
            event.getOccurredAt(),
            event.getEventId(),
            null,
            new UserCreatedPayload(
                event.getAggregateId(),
                event.getTags(),
                event.getLevel()
            )
        );
    }
}

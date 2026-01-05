package com.lxp.user.infrastructure.messaging.mapper;

import com.lxp.user.application.event.UserCreationFailedIntegrationEvent;
import com.lxp.user.application.event.UserRegisteredIntegrationEvent;
import com.lxp.user.application.port.in.command.UserSaveCommand;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class IntegrationEventMapper {

    public UserCreationFailedIntegrationEvent toCompensationEvent(
        UserRegisteredIntegrationEvent originalEvent,
        String errorReason
    ) {
        return new UserCreationFailedIntegrationEvent(
            UUID.randomUUID().toString(),
            LocalDateTime.now(),
            "user-service",
            originalEvent.getCorrelationId(),
            originalEvent.getEventId(),
            1,
            originalEvent.getData().userId(),
            errorReason
        );
    }

    public UserSaveCommand toSaveCommand(UserRegisteredIntegrationEvent event) {
        UserRegisteredIntegrationEvent.Payload data = event.getData();
        return new UserSaveCommand(
            data.userId(),
            data.email(),
            data.name(),
            data.role(),
            data.tagIds(),
            data.level()
        );
    }
}

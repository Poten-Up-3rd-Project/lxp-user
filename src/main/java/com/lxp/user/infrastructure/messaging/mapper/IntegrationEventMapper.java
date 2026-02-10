package com.lxp.user.infrastructure.messaging.mapper;

import com.lxp.common.application.event.IntegrationEvent;
import com.lxp.common.domain.event.BaseDomainEvent;
import com.lxp.user.application.event.integration.UserRegisteredRecommendIntegrationEvent;
import com.lxp.user.application.event.integration.UserUpdatedIntegrationEvent;
import com.lxp.user.application.event.integration.UserWithdrawnRecommendIntegrationEvent;
import com.lxp.user.application.event.payload.UserCreatedPayload;
import com.lxp.user.application.event.payload.UserUpdatedPayload;
import com.lxp.user.application.event.payload.UserWithdrawnPayload;
import com.lxp.user.application.port.required.DomainEventToIntegrationEventConverter;
import com.lxp.user.domain.user.event.UserCreatedEvent;
import com.lxp.user.domain.user.event.UserUpdatedEvent;
import com.lxp.user.domain.user.event.UserWithdrawEvent;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component("userIntegrationEventMapper")
public class IntegrationEventMapper implements DomainEventToIntegrationEventConverter {

    @Override
    public IntegrationEvent convert(BaseDomainEvent event) {
        return toIntegration(event);
    }

    /**
     * CrudEvent를 IntegrationEvent로 변환
     *
     * `@TransactionalEventListener`에서 호출됨
     */
    public List<IntegrationEvent> toIntegrationEvents(BaseDomainEvent event) {
        return List.of(toIntegrationEvent(event));
    }

    public IntegrationEvent toIntegrationEvent(BaseDomainEvent event) {
        return toIntegration(event);
    }

    public IntegrationEvent toIntegration(BaseDomainEvent event) {
        if (event instanceof UserCreatedEvent userCreatedEvent) {
            return toUserCreatedIntegration(userCreatedEvent);
        } else if (event instanceof UserUpdatedEvent userUpdatedEvent) {
            return toUserUpdatedIntegration(userUpdatedEvent);
        } else if (event instanceof UserWithdrawEvent userWithdrawEvent) {
            return toUserWithdrawnIntegration(userWithdrawEvent);
        } else {
            throw new IllegalArgumentException("Unsupported event type: " + event.getClass().getSimpleName());
        }
    }

    private UserRegisteredRecommendIntegrationEvent toUserCreatedIntegration(UserCreatedEvent event) {
        return new UserRegisteredRecommendIntegrationEvent(
            UUID.randomUUID().toString(),
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

    private UserUpdatedIntegrationEvent toUserUpdatedIntegration(UserUpdatedEvent event) {
        return toUserUpdatedIntegrationEvent(event);
    }

    private UserUpdatedIntegrationEvent toUserUpdatedIntegrationEvent(UserUpdatedEvent event) {
        return new UserUpdatedIntegrationEvent(
            UUID.randomUUID().toString(),
            event.getOccurredAt(),
            event.getEventId(),
            null,
            new UserUpdatedPayload(
                event.getAggregateId(),
                event.getEmail(),
                event.getName(),
                event.getTags(),
                event.getLevel()
            )
        );
    }

    private UserWithdrawnRecommendIntegrationEvent toUserWithdrawnIntegration(UserWithdrawEvent event) {
        return new UserWithdrawnRecommendIntegrationEvent(
            event.getEventId(),
            event.getOccurredAt(),
            event.getEventId(),
            null,
            new UserWithdrawnPayload(event.getAggregateId())
        );
    }
}

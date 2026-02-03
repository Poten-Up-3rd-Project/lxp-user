package com.lxp.user.infrastructure.messaging.mapper;

import com.lxp.common.domain.event.BaseDomainEvent;
import com.lxp.common.application.event.IntegrationEvent;
import java.util.UUID;
import com.lxp.user.application.event.integration.UserRegisteredRecommendIntegrationEvent;
import com.lxp.user.application.event.integration.UserUpdatedCourseIntegrationEvent;
import com.lxp.user.application.event.integration.UserUpdatedRecommendIntegrationEvent;
import com.lxp.user.application.event.integration.UserWithdrawnRecommendIntegrationEvent;
import com.lxp.user.application.event.payload.UserCoursePayload;
import com.lxp.user.application.event.payload.UserRecommendPayload;
import com.lxp.user.application.event.payload.UserWithdrawnPayload;
import com.lxp.user.application.port.required.DomainEventToIntegrationEventConverter;
import com.lxp.user.domain.user.event.UserCreatedEvent;
import com.lxp.user.domain.user.event.UserUpdatedEvent;
import com.lxp.user.domain.user.event.UserWithdrawEvent;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component("userIntegrationEventMapper")
public class IntegrationEventMapper implements DomainEventToIntegrationEventConverter {

    @Override
    public IntegrationEvent convert(BaseDomainEvent event) {
        return toIntegration(event);
    }

    /**
     * CrudEvent를 IntegrationEvent로 변환
     * @TransactionalEventListener에서 호출됨
     * UserUpdatedEvent는 recommend는 항상 + course는 UpdateType에 따라 선택적
     */
    public List<IntegrationEvent> toIntegrationEvents(BaseDomainEvent event) {
        if (event instanceof UserUpdatedEvent userUpdatedEvent) {
            List<IntegrationEvent> events = new ArrayList<>();
            
            // recommend는 항상 보냄
            events.add(toUserUpdatedRecommendIntegration(userUpdatedEvent));
            
            // course는 profile 업데이트가 되면 발송 (USER 만 돐된 경우 제외)
            if (userUpdatedEvent.getUpdateType() != UserUpdatedEvent.UpdateType.USER) {
                events.add(toUserUpdatedCourseIntegration(userUpdatedEvent));
            }
            
            return events;
        }
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
            new UserRecommendPayload(
                event.getAggregateId(),
                event.getTags(),
                event.getLevel()
            )
        );
    }

    private UserUpdatedRecommendIntegrationEvent toUserUpdatedIntegration(UserUpdatedEvent event) {
        return toUserUpdatedRecommendIntegration(event);
    }

    private UserUpdatedRecommendIntegrationEvent toUserUpdatedRecommendIntegration(UserUpdatedEvent event) {
        return new UserUpdatedRecommendIntegrationEvent(
            UUID.randomUUID().toString(),
            event.getOccurredAt(),
            event.getEventId(),
            null,
            new UserRecommendPayload(
                event.getAggregateId(),
                event.getTags(),
                event.getLevel()
            )
        );
    }

    private UserUpdatedCourseIntegrationEvent toUserUpdatedCourseIntegration(UserUpdatedEvent event) {
        return new UserUpdatedCourseIntegrationEvent(
            UUID.randomUUID().toString(),
            event.getOccurredAt(),
            event.getEventId(),
            null,
            new UserCoursePayload(
                event.getAggregateId(),
                event.getEmail(),      // User update 또는 All update 시에만 포함, 아니면 null
                event.getName()        // User update 또는 All update 시에만 포함, 아니면 null
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

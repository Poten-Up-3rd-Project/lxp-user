package com.lxp.user.infrastructure.outbox;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lxp.common.application.event.IntegrationEvent;
import com.lxp.common.infrastructure.persistence.OutboxEvent;
import com.lxp.user.application.event.integration.UserRegisteredRecommendIntegrationEvent;
import com.lxp.user.application.event.integration.UserUpdatedCourseIntegrationEvent;
import com.lxp.user.application.event.integration.UserUpdatedRecommendIntegrationEvent;
import com.lxp.user.application.event.integration.UserWithdrawnRecommendIntegrationEvent;
import com.lxp.user.infrastructure.messaging.exception.EventSerializationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OutboxEventSerializer {

    private final ObjectMapper objectMapper;

    public String serialize(IntegrationEvent event) {
        try {
            return objectMapper.writeValueAsString(event);
        } catch (Exception e) {
            throw new EventSerializationException("Serialization failed", e);
        }
    }

    public IntegrationEvent deserialize(OutboxEvent outbox) {
        try {
            Class<? extends IntegrationEvent> eventClass = resolveEventClass(outbox.getEventType());
            return objectMapper.readValue(outbox.getPayload(), eventClass);
        } catch (Exception e) {
            throw new EventSerializationException("Deserialization failed", e);
        }
    }

    Class<? extends IntegrationEvent> resolveEventClass(String eventType) {
        return switch (eventType) {
            case "user.created" -> UserRegisteredRecommendIntegrationEvent.class;
            case "user.updated.recommend" -> UserUpdatedRecommendIntegrationEvent.class;
            case "user.updated.course" -> UserUpdatedCourseIntegrationEvent.class;
            case "user.deleted" -> UserWithdrawnRecommendIntegrationEvent.class;
            default -> throw new IllegalArgumentException("Unknown event type: " + eventType);
        };
    }

}

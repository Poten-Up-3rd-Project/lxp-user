package com.lxp.user.application.event;

import com.lxp.common.application.event.BaseIntegrationEvent;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class UserRegisteredIntegrationEvent extends BaseIntegrationEvent {
    private final String eventType;
    private final Payload data;

    public UserRegisteredIntegrationEvent(String eventId, LocalDateTime occurredAt, String source, String correlationId, String causationId, int version, String eventType, Payload payload) {
        super(eventId, occurredAt, source, correlationId, causationId, version);
        this.eventType = eventType;
        this.data = payload;
    }

    public record Payload(String userId, String name, String email, String role, List<Long> tagIds, String level) {
    }
}

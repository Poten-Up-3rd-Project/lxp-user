package com.lxp.user.application.event;

import com.lxp.common.application.event.BaseIntegrationEvent;

import java.time.LocalDateTime;

public class UserCreationFailedIntegrationEvent extends BaseIntegrationEvent {
    String userId;
    String reason;

    public UserCreationFailedIntegrationEvent(String eventId, LocalDateTime occurredAt, String source, String correlationId, String causationId, int version, String userId, String reason) {
        super(eventId, occurredAt, source, correlationId, causationId, version);
        this.userId = userId;
        this.reason = reason;
    }
}

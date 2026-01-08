package com.lxp.user.application.event;

import com.lxp.common.application.event.BaseIntegrationEventEnvelope;
import com.lxp.user.application.event.payload.UserPayload;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UserRoleUpdatedIntegrationEvent extends BaseIntegrationEventEnvelope<UserPayload> {

    public UserRoleUpdatedIntegrationEvent(String eventId, LocalDateTime occurredAt, String source, String correlationId, String causationId, UserPayload payload) {
        super(eventId, occurredAt, source, correlationId, causationId, payload);
    }
}

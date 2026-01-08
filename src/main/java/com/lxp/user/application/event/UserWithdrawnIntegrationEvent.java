package com.lxp.user.application.event;

import com.lxp.common.application.event.BaseIntegrationEventEnvelope;
import com.lxp.user.application.event.payload.UserWithdrawnPayload;

import java.time.LocalDateTime;

public class UserWithdrawnIntegrationEvent extends BaseIntegrationEventEnvelope<UserWithdrawnPayload> {

    protected UserWithdrawnIntegrationEvent(String eventId, LocalDateTime occurredAt, String source, String correlationId, String causationId, UserWithdrawnPayload payload) {
        super(eventId, occurredAt, source, correlationId, causationId, payload);
    }
}

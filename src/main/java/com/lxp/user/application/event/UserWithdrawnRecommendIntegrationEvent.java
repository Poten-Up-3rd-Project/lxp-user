package com.lxp.user.application.event;

import com.lxp.common.application.event.BaseIntegrationEventEnvelope;
import com.lxp.user.application.event.payload.UserWithdrawnPayload;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UserWithdrawnRecommendIntegrationEvent extends BaseIntegrationEventEnvelope<UserWithdrawnPayload> {

    protected UserWithdrawnRecommendIntegrationEvent(String eventId, LocalDateTime occurredAt, String source, String correlationId, String causationId, UserWithdrawnPayload payload) {
        super(eventId, occurredAt, source, correlationId, causationId, payload);
    }
}

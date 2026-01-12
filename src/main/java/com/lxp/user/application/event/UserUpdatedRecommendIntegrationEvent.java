package com.lxp.user.application.event;

import com.lxp.common.application.event.BaseIntegrationEventEnvelope;
import com.lxp.user.application.event.payload.UserRecommendPayload;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UserUpdatedRecommendIntegrationEvent extends BaseIntegrationEventEnvelope<UserRecommendPayload> {

    public UserUpdatedRecommendIntegrationEvent(String eventId, LocalDateTime occurredAt, String source, String correlationId, String causationId, UserRecommendPayload payload) {
        super(eventId, occurredAt, source, correlationId, causationId, payload);
    }
}

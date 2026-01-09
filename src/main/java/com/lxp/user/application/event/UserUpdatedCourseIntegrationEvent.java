package com.lxp.user.application.event;

import com.lxp.common.application.event.BaseIntegrationEventEnvelope;
import com.lxp.user.application.event.payload.UserCoursePayload;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UserUpdatedCourseIntegrationEvent extends BaseIntegrationEventEnvelope<UserCoursePayload> {

    protected UserUpdatedCourseIntegrationEvent(String eventId, LocalDateTime occurredAt, String source, String correlationId, String causationId, UserCoursePayload payload) {
        super(eventId, occurredAt, source, correlationId, causationId, payload);
    }
}

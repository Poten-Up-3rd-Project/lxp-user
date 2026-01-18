package com.lxp.user.application.event.integration;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.lxp.common.application.event.BaseIntegrationEventEnvelope;
import com.lxp.user.application.event.payload.UserCoursePayload;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserUpdatedCourseIntegrationEvent extends BaseIntegrationEventEnvelope<UserCoursePayload> {

    private static final String SOURCE = "lxp.user.service";

    @JsonCreator
    public UserUpdatedCourseIntegrationEvent(@JsonProperty("eventId") String eventId,
                                             @JsonProperty("occurredAt") LocalDateTime occurredAt,
                                             @JsonProperty("correlationId") String correlationId,
                                             @JsonProperty("causationId") String causationId,
                                             @JsonProperty("payload") UserCoursePayload payload) {
        super(eventId, occurredAt, SOURCE, correlationId, causationId, payload);
    }

    @Override
    public String getEventType() {
        return "user.updated.course";
    }
}

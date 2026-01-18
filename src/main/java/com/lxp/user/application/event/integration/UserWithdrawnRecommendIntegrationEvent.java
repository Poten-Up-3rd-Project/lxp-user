package com.lxp.user.application.event.integration;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.lxp.common.application.event.BaseIntegrationEventEnvelope;
import com.lxp.user.application.event.payload.UserWithdrawnPayload;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserWithdrawnRecommendIntegrationEvent extends BaseIntegrationEventEnvelope<UserWithdrawnPayload> {

    private static final String SOURCE = "lxp.user.service";

    @JsonCreator
    public UserWithdrawnRecommendIntegrationEvent(@JsonProperty("eventId") String eventId,
                                                  @JsonProperty("occurredAt") LocalDateTime occurredAt,
                                                  @JsonProperty("correlationId") String correlationId,
                                                  @JsonProperty("causationId") String causationId,
                                                  @JsonProperty("payload") UserWithdrawnPayload payload) {
        super(eventId, occurredAt, SOURCE, correlationId, causationId, payload);
    }

    @Override
    public String getEventType() {
        return "user.deleted";
    }
}

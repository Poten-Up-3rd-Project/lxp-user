package com.lxp.user.application.event.payload;

import java.util.List;

public record UserCreatedPayload(
    String userId, List<Long> tagIds, String level
) {
}

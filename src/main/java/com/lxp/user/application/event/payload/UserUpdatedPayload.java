package com.lxp.user.application.event.payload;

import java.util.List;

public record UserUpdatedPayload(
    String userId, String email, String name, List<Long> tagIds, String level
) {
}

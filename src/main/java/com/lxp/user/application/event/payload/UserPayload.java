package com.lxp.user.application.event.payload;

import java.util.List;

public record UserPayload(
    String userId, String name, String email, String role, List<Long> tagIds, String level
) {
}

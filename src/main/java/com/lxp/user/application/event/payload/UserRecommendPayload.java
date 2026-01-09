package com.lxp.user.application.event.payload;

import java.util.List;

public record UserRecommendPayload(
    String userId, String role, List<Long> tagIds, String level
) {
}

package com.lxp.user.application.event.payload;

import java.util.List;

public record UserRecommendPayload(
    String userId, List<Long> tagIds, String level
) {
}

package com.lxp.user.application.port.provided.dto;

import java.util.List;

public record UserProfileInternalResult(
    String userId,
    List<Long> interestTagIds,
    String learnerLevel
) {
}

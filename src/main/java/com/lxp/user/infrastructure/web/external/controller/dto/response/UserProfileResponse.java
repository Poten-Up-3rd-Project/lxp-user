package com.lxp.user.infrastructure.web.external.controller.dto.response;

import com.lxp.user.application.port.provided.dto.TagExternalResult;

import java.util.List;

public record UserProfileResponse(
    String userId,
    String email,
    String name,
    List<TagExternalResult> tags,
    String level
) {
}

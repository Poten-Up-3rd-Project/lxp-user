package com.lxp.user.application.port.provided.dto;

import java.util.List;

public record UserSearchQuery(
    String id,
    String name,
    String email,
    String role,
    List<TagExternalResult> tags,
    String level
) {
}

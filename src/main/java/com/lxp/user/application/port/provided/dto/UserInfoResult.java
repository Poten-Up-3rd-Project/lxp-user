package com.lxp.user.application.port.provided.dto;

import java.util.List;

public record UserInfoResult(
    String id,
    String name,
    String email,
    String role,
    List<TagExternalResult> tags,
    String level
) {
}

package com.lxp.user.infrastructure.web.external.controller.dto.request;

import java.util.List;

public record UserUpdateRequest(String name, String level, List<Long> tagIds) {
}

package com.lxp.user.infrastructure.web.internal.client.dto;

import java.util.List;

public record TagListResponse(
    List<TagInfo> tags
) {
}

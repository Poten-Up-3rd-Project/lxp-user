package com.lxp.user.infrastructure.web.external.client.dto;

import java.util.List;

public record TagListResponse(
    List<TagInfo> tags
) {
}

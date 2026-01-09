package com.lxp.user.infrastructure.web.internal.client.dto;

public record TagInfo(
    Long id,
    String name,
    String color,
    String variant
) {
}

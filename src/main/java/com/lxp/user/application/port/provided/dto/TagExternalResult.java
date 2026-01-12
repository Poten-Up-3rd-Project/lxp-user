package com.lxp.user.application.port.provided.dto;

public record TagExternalResult(
    Long id,
    String content,
    String color,
    String variant
) {
}

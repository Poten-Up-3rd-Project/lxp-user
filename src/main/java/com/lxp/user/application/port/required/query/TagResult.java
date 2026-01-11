package com.lxp.user.application.port.required.query;

public record TagResult(
    Long id,
    String content,
    String color,
    String variant
) {
}

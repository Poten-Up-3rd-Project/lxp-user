package com.lxp.user.application.port.required.query;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record TagResult(
    Long id,
    String content,
    String color,
    String variant
) {
}

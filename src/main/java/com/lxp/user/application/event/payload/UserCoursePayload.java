package com.lxp.user.application.event.payload;

public record UserCoursePayload(
    String userId, String email, String name
) {
}

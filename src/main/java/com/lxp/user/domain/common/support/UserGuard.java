package com.lxp.user.domain.common.support;

import com.lxp.user.domain.common.exception.UserErrorCode;
import com.lxp.user.domain.common.exception.UserException;

import java.util.Objects;

public class UserGuard {

    public static <T> T requireNonNull(T obj, String message) {
        if (Objects.isNull(obj)) {
            throw missing(message);
        }
        return obj;
    }

    public static String requireNonBlank(String value, String message) {
        if (value == null || value.isBlank()) {
            throw missing(message);
        }
        return value;
    }

    private static UserException missing(String message) {
        return new UserException(UserErrorCode.MISSING_REQUIRED_FIELD, message);
    }

}

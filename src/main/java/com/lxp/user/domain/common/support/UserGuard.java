package com.lxp.user.domain.common.support;

import com.lxp.user.domain.common.exception.UserErrorCode;
import com.lxp.user.domain.common.exception.UserException;

import java.util.Objects;

public class UserGuard {

    public static <T> T requireNonNull(T obj, String message) {
        if (Objects.isNull(obj)) {
            throw new UserException(UserErrorCode.MISSING_REQUIRED_FIELD, message);
        }
        return obj;
    }

}

package com.lxp.user.domain.user.model.vo;

import com.lxp.user.domain.common.exception.UserErrorCode;
import com.lxp.user.domain.common.exception.UserException;

import java.util.Objects;

public record UserName(String value) {

    private static final int MAX_LENGTH = 5;

    public UserName {
        if (Objects.isNull(value) || value.isBlank()) {
            throw new UserException(UserErrorCode.MISSING_REQUIRED_FIELD, "사용자 이름은 필수입니다.");
        }
        if (value.length() > MAX_LENGTH) {
            throw new UserException(UserErrorCode.SIZE_CONSTRAINT_VIOLATION, "사용자 이름은 5자를 초과할 수 없습니다.");
        }
    }

    public static UserName of(String value) {
        return new UserName(value);
    }

}

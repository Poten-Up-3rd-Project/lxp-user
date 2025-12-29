package com.lxp.user.domain.user.model.vo;

import com.lxp.user.domain.common.exception.UserErrorCode;
import com.lxp.user.domain.common.exception.UserException;

import java.util.regex.Pattern;

import static java.util.Objects.isNull;

public record UserEmail(String value) {

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$");

    public UserEmail {
        if (isNull(value) || !EMAIL_PATTERN.matcher(value).matches()) {
            throw new UserException(UserErrorCode.MISSING_REQUIRED_FIELD, "유효하지 않은 이메일 형식입니다: " + value);
        }
    }

    public static UserEmail of(String value) {
        return new UserEmail(value);
    }

}

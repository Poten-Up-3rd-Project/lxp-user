package com.lxp.user.domain.user.policy;

import com.lxp.user.domain.common.exception.UserException;
import com.lxp.user.domain.user.service.spec.UserSaveSpec;

import java.util.Objects;

import static com.lxp.user.domain.common.exception.UserErrorCode.MISSING_REQUIRED_FIELD;
import static com.lxp.user.domain.user.model.vo.UserRole.ADMIN;

public class UserCreationPolicy {

    public void validate(UserSaveSpec spec) throws UserException {
        if (Objects.isNull(spec)) {
            throw new UserException(MISSING_REQUIRED_FIELD, "사용자 생성 정보가 필요합니다.");
        }

        if (spec.role() != ADMIN) {
            if (spec.level() == null || spec.tagIds() == null) {
                throw new UserException(MISSING_REQUIRED_FIELD, "LEARNER 및 INSTRUCTOR는 level과 tagIds가 필요합니다.");
            }
        }
    }
}

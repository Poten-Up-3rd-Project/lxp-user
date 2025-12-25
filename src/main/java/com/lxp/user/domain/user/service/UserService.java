package com.lxp.user.domain.user.service;

import com.lxp.common.domain.annotation.DomainService;
import com.lxp.user.domain.common.exception.UserErrorCode;
import com.lxp.user.domain.common.exception.UserException;
import com.lxp.user.domain.common.model.vo.Level;
import com.lxp.user.domain.common.model.vo.UserId;
import com.lxp.user.domain.profile.model.entity.UserProfile;
import com.lxp.user.domain.profile.model.vo.Tags;
import com.lxp.user.domain.user.model.entity.User;
import com.lxp.user.domain.user.model.vo.UserEmail;
import com.lxp.user.domain.user.model.vo.UserName;
import com.lxp.user.domain.user.model.vo.UserRole;
import com.lxp.user.domain.user.policy.UserCreationPolicy;
import com.lxp.user.domain.user.service.spec.UserSaveSpec;

import static com.lxp.user.domain.user.model.vo.UserRole.ADMIN;

@DomainService
public class UserService {

    public final UserCreationPolicy userCreationPolicy = new UserCreationPolicy();

    public User create(UserSaveSpec spec) {
        userCreationPolicy.validate(spec);

        UserRole role = spec.role();
        UserId userId = UserId.of(spec.userId());
        UserProfile userProfile = null;

        if (role != ADMIN) {
            userProfile = createUserProfile(userId, spec);
        }

        return switch (role) {
            case LEARNER ->
                User.createLearner(userId, UserName.of(spec.name()), UserEmail.of(spec.email()), userProfile);
            case INSTRUCTOR ->
                User.createInstructor(userId, UserName.of(spec.name()), UserEmail.of(spec.email()), userProfile);
            case ADMIN -> User.createAdmin(userId, UserName.of(spec.name()), UserEmail.of(spec.email()));
        };
    }

    private UserProfile createUserProfile(UserId userId, UserSaveSpec spec) {
        Level level = Level.fromString(spec.level())
            .orElseThrow(() -> new UserException(UserErrorCode.LEVEL_NOT_FOUND));

        return UserProfile.create(userId, level, new Tags(spec.tagIds()));
    }

}

package com.lxp.user.domain.user.model.entity;

import com.lxp.common.domain.event.AggregateRoot;
import com.lxp.user.domain.common.model.vo.Level;
import com.lxp.user.domain.common.model.vo.UserId;
import com.lxp.user.domain.common.support.UserGuard;
import com.lxp.user.domain.profile.model.entity.UserProfile;
import com.lxp.user.domain.user.event.UserCreatedEvent;
import com.lxp.user.domain.user.event.UserUpdatedEvent;
import com.lxp.user.domain.user.event.UserWithdrawEvent;
import com.lxp.user.domain.user.model.vo.UserEmail;
import com.lxp.user.domain.user.model.vo.UserName;
import com.lxp.user.domain.user.model.vo.UserRole;
import com.lxp.user.domain.user.model.vo.UserStatus;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.Objects.nonNull;

public class User extends AggregateRoot<UserId> {

    private UserId id;

    private UserName name;

    private UserEmail email;

    private UserRole role;

    private UserStatus userStatus;

    private UserProfile userProfile;

    private LocalDateTime deletedAt;

    private User(UserId id, UserName name, UserEmail email, UserRole userRole, UserStatus userStatus, UserProfile userProfile, LocalDateTime deletedAt) {
        this.id = UserGuard.requireNonNull(id, "userId는 null일 수 없습니다.");
        this.name = UserGuard.requireNonNull(name, "userName은 null일 수 없습니다.");
        this.email = UserGuard.requireNonNull(email, "userEmail은 null일 수 없습니다.");
        this.role = UserGuard.requireNonNull(userRole, "userRole은 null일 수 없습니다.");
        this.userStatus = userStatus;
        this.userProfile = userProfile;
        this.deletedAt = deletedAt;
    }

    public static User of(UserId id, UserName name, UserEmail email, UserRole userRole, UserStatus userStatus, UserProfile userProfile, LocalDateTime deletedAt) {
        return new User(id, name, email, userRole, userStatus, userProfile, deletedAt);
    }

    public static User createLearner(UserId id, UserName name, UserEmail email, UserProfile userProfile) {
        User user = new User(id, name, email, UserRole.LEARNER, UserStatus.ACTIVE, userProfile, null);
        user.registerEvent(new UserCreatedEvent(user.id.asString(), user.profile().tags().values(), user.profile().level().name()));
        return user;
    }

    public static User createInstructor(UserId id, UserName name, UserEmail email, UserProfile userProfile) {
        User user = new User(id, name, email, UserRole.INSTRUCTOR, UserStatus.ACTIVE, userProfile, null);
        user.registerEvent(new UserCreatedEvent(user.id.asString(), user.profile().tags().values(), user.profile().level().name()));
        return user;
    }

    public static User createAdmin(UserId id, UserName name, UserEmail email) {
        return new User(id, name, email, UserRole.ADMIN, UserStatus.ACTIVE, null, null);
    }

    public void update(UserName name, Level level, List<Long> tags) {
        if (!isActive()) {
            return;
        }

        boolean userUpdated = updateName(name);
        boolean profileUpdated = updateProfile(level, tags);

        if (userUpdated || profileUpdated) {
            this.registerEvent(new UserUpdatedEvent(
                this.id.asString(),
                this.name.value(),
                this.email.value(),
                this.profile().tags().values(),
                this.profile().level().name(),
                UserUpdatedEvent.findType(userUpdated, profileUpdated)
            ));
        }
    }

    private boolean updateName(UserName name) {
        boolean result = false;
        if (nonNull(name) && !this.name.equals(name)) {
            this.name = name;
            result = true;
        }
        return result;
    }

    private boolean updateProfile(Level level, List<Long> tags) {
        boolean result = false;
        if (nonNull(this.userProfile) && (nonNull(level) || nonNull(tags))) {
            this.userProfile.update(level, tags);
            result = true;
        }
        return result;
    }

    public void makeInstructor() {
        if (this.role == UserRole.LEARNER && this.userStatus == UserStatus.ACTIVE) {
            this.role = UserRole.INSTRUCTOR;
        }
    }

    public boolean hasProfile() {
        return this.userProfile != null;
    }

    public boolean canManageOwnCourse() {
        return this.role == UserRole.INSTRUCTOR || this.role == UserRole.ADMIN;
    }

    public boolean canManageAllCourses() {
        return this.role == UserRole.ADMIN;
    }

    public boolean isActive() {
        return this.userStatus == UserStatus.ACTIVE;
    }

    public void withdraw() {
        if (!this.userStatus.catTransitionTo(UserStatus.DELETED)) {
            return;
        }
        this.userStatus = UserStatus.DELETED;
        this.deletedAt = LocalDateTime.now();
        this.registerEvent(new UserWithdrawEvent(this.id.asString()));
    }

    public UserId id() {
        return this.id;
    }

    public String name() {
        return this.name.value();
    }

    public String email() {
        return this.email.value();
    }

    public UserRole role() {
        return this.role;
    }

    public UserStatus userStatus() {
        return this.userStatus;
    }

    public UserProfile profile() {
        return this.userProfile;
    }

    public LocalDateTime deletedAt() {
        return this.deletedAt;
    }

    @Override
    public UserId getId() {
        return this.id;
    }

}

package com.lxp.user.domain.user.model.entity;

import com.lxp.user.domain.common.exception.UserException;
import com.lxp.user.domain.common.model.vo.Level;
import com.lxp.user.domain.common.model.vo.UserId;
import com.lxp.user.domain.profile.model.entity.UserProfile;
import com.lxp.user.domain.profile.model.vo.Tags;
import com.lxp.user.domain.user.model.vo.UserEmail;
import com.lxp.user.domain.user.model.vo.UserName;
import com.lxp.user.domain.user.model.vo.UserRole;
import com.lxp.user.domain.user.model.vo.UserStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("User 도메인 엔티티 테스트")
class UserTest {

    @Test
    @DisplayName("Learner 역할의 User를 생성한다")
    void shouldCreateLearnerUser() {
        // given
        UserId userId = UserId.of(UUID.randomUUID());
        UserName userName = UserName.of("홍길동");
        UserEmail userEmail = UserEmail.of("test@example.com");
        UserProfile profile = UserProfile.create(
            userId,
            Level.JUNIOR,
            new Tags(new ArrayList<>(List.of(1L, 2L, 3L)))
        );

        // when
        User user = User.createLearner(userId, userName, userEmail, profile);

        // then
        assertThat(user).isNotNull();
        assertThat(user.id()).isEqualTo(userId);
        assertThat(user.name()).isEqualTo("홍길동");
        assertThat(user.email()).isEqualTo("test@example.com");
        assertThat(user.role()).isEqualTo(UserRole.LEARNER);
        assertThat(user.userStatus()).isEqualTo(UserStatus.ACTIVE);
        assertThat(user.profile()).isEqualTo(profile);
        assertThat(user.isActive()).isTrue();
    }

    @Test
    @DisplayName("Instructor 역할의 User를 생성한다")
    void shouldCreateInstructorUser() {
        // given
        UserId userId = UserId.of(UUID.randomUUID());
        UserName userName = UserName.of("강사님");
        UserEmail userEmail = UserEmail.of("instructor@example.com");
        UserProfile profile = UserProfile.create(
            userId,
            Level.SENIOR,
            new Tags(List.of(4L, 5L, 6L))
        );

        // when
        User user = User.createInstructor(userId, userName, userEmail, profile);

        // then
        assertThat(user.role()).isEqualTo(UserRole.INSTRUCTOR);
        assertThat(user.userStatus()).isEqualTo(UserStatus.ACTIVE);
        assertThat(user.canManageOwnCourse()).isTrue();
    }

    @Test
    @DisplayName("Admin 역할의 User를 생성한다")
    void shouldCreateAdminUser() {
        // given
        UserId userId = UserId.of(UUID.randomUUID());
        UserName userName = UserName.of("관리자");
        UserEmail userEmail = UserEmail.of("admin@example.com");

        // when
        User user = User.createAdmin(userId, userName, userEmail);

        // then
        assertThat(user.role()).isEqualTo(UserRole.ADMIN);
        assertThat(user.userStatus()).isEqualTo(UserStatus.ACTIVE);
        assertThat(user.profile()).isNull();
        assertThat(user.canManageAllCourses()).isTrue();
    }

    @Test
    @DisplayName("User 생성 시 필수값이 null이면 예외가 발생한다")
    void shouldThrowExceptionWhenRequiredFieldsAreNull() {
        // given
        UserId userId = UserId.of(UUID.randomUUID());
        UserName userName = UserName.of("테스트");
        UserEmail userEmail = UserEmail.of("test@example.com");

        // then
        assertThatThrownBy(() ->
            User.of(null, userName, userEmail, UserRole.LEARNER, UserStatus.ACTIVE, null, null)
        ).isInstanceOf(UserException.class)
            .hasMessageContaining("userId는 null일 수 없습니다");

        assertThatThrownBy(() ->
            User.of(userId, null, userEmail, UserRole.LEARNER, UserStatus.ACTIVE, null, null)
        ).isInstanceOf(UserException.class)
            .hasMessageContaining("userName은 null일 수 없습니다");

        assertThatThrownBy(() ->
            User.of(userId, userName, null, UserRole.LEARNER, UserStatus.ACTIVE, null, null)
        ).isInstanceOf(UserException.class)
            .hasMessageContaining("userEmail은 null일 수 없습니다");

        assertThatThrownBy(() ->
            User.of(userId, userName, userEmail, null, UserStatus.ACTIVE, null, null)
        ).isInstanceOf(UserException.class)
            .hasMessageContaining("userRole은 null일 수 없습니다");
    }

    @Test
    @DisplayName("User 정보를 업데이트한다")
    void shouldUpdateUserInformation() {
        // given
        UserId userId = UserId.of(UUID.randomUUID());
        UserProfile profile = UserProfile.create(
            userId,
            Level.JUNIOR,
            new Tags(List.of(1L, 2L, 3L))
        );
        User user = User.createLearner(
            userId,
            UserName.of("홍길동"),
            UserEmail.of("test@example.com"),
            profile
        );

        // when
        user.update(UserName.of("김철수"), Level.MIDDLE, List.of(4L, 5L, 6L));

        // then
        assertThat(user.name()).isEqualTo("김철수");
        assertThat(user.profile().level()).isEqualTo(Level.MIDDLE);
        assertThat(user.profile().tags().values()).containsExactly(4L, 5L, 6L);
    }

    @Test
    @DisplayName("비활성 User는 업데이트되지 않는다")
    void shouldNotUpdateInactiveUser() {
        // given
        UserId userId = UserId.of(UUID.randomUUID());
        UserProfile profile = UserProfile.create(
            userId,
            Level.JUNIOR,
            new Tags(List.of(1L, 2L, 3L))
        );
        User user = User.createLearner(
            userId,
            UserName.of("홍길동"),
            UserEmail.of("test@example.com"),
            profile
        );
        user.withdraw();

        String originalName = user.name();
        Level originalLevel = user.profile().level();

        // when
        user.update(UserName.of("김철수"), Level.MIDDLE, List.of(4L, 5L, 6L));

        // then - 업데이트되지 않음
        assertThat(user.name()).isEqualTo(originalName);
        assertThat(user.profile().level()).isEqualTo(originalLevel);
    }

    @Test
    @DisplayName("Learner를 Instructor로 승급시킨다")
    void shouldPromoteLearnerToInstructor() {
        // given
        User user = User.createLearner(
            UserId.of(UUID.randomUUID()),
            UserName.of("홍길동"),
            UserEmail.of("test@example.com"),
            UserProfile.create(
                UserId.of(UUID.randomUUID()),
                Level.SENIOR,
                new Tags(List.of(1L, 2L, 3L))
            )
        );

        // when
        user.makeInstructor();

        // then
        assertThat(user.role()).isEqualTo(UserRole.INSTRUCTOR);
    }

    @Test
    @DisplayName("Admin이나 이미 Instructor인 경우 makeInstructor는 변경하지 않는다")
    void shouldNotChangeRoleIfNotLearner() {
        // given
        User admin = User.createAdmin(
            UserId.of(UUID.randomUUID()),
            UserName.of("관리자"),
            UserEmail.of("admin@example.com")
        );

        // when
        admin.makeInstructor();

        // then
        assertThat(admin.role()).isEqualTo(UserRole.ADMIN);
    }

    @Test
    @DisplayName("User를 탈퇴 처리한다")
    void shouldWithdrawUser() {
        // given
        User user = User.createLearner(
            UserId.of(UUID.randomUUID()),
            UserName.of("홍길동"),
            UserEmail.of("test@example.com"),
            UserProfile.create(
                UserId.of(UUID.randomUUID()),
                Level.JUNIOR,
                new Tags(List.of(1L, 2L, 3L))
            )
        );

        // when
        user.withdraw();

        // then
        assertThat(user.userStatus()).isEqualTo(UserStatus.DELETED);
        assertThat(user.isActive()).isFalse();
        assertThat(user.deletedAt()).isNotNull();
    }

    @Test
    @DisplayName("Instructor와 Admin은 자신의 코스를 관리할 수 있다")
    void shouldAllowInstructorAndAdminToManageOwnCourse() {
        // given
        User instructor = User.createInstructor(
            UserId.of(UUID.randomUUID()),
            UserName.of("강사"),
            UserEmail.of("instructor@example.com"),
            UserProfile.create(
                UserId.of(UUID.randomUUID()),
                Level.EXPERT,
                new Tags(List.of(1L, 2L, 3L))
            )
        );
        User admin = User.createAdmin(
            UserId.of(UUID.randomUUID()),
            UserName.of("관리자"),
            UserEmail.of("admin@example.com")
        );

        // then
        assertThat(instructor.canManageOwnCourse()).isTrue();
        assertThat(admin.canManageOwnCourse()).isTrue();
    }

    @Test
    @DisplayName("Learner는 자신의 코스를 관리할 수 없다")
    void shouldNotAllowLearnerToManageOwnCourse() {
        // given
        User learner = User.createLearner(
            UserId.of(UUID.randomUUID()),
            UserName.of("학습자"),
            UserEmail.of("learner@example.com"),
            UserProfile.create(
                UserId.of(UUID.randomUUID()),
                Level.JUNIOR,
                new Tags(List.of(1L, 2L, 3L))
            )
        );

        // then
        assertThat(learner.canManageOwnCourse()).isFalse();
    }

    @Test
    @DisplayName("Admin만 모든 코스를 관리할 수 있다")
    void shouldOnlyAllowAdminToManageAllCourses() {
        // given
        User admin = User.createAdmin(
            UserId.of(UUID.randomUUID()),
            UserName.of("관리자"),
            UserEmail.of("admin@example.com")
        );
        User instructor = User.createInstructor(
            UserId.of(UUID.randomUUID()),
            UserName.of("강사"),
            UserEmail.of("instructor@example.com"),
            UserProfile.create(
                UserId.of(UUID.randomUUID()),
                Level.EXPERT,
                new Tags(List.of(1L, 2L, 3L))
            )
        );

        // then
        assertThat(admin.canManageAllCourses()).isTrue();
        assertThat(instructor.canManageAllCourses()).isFalse();
    }
}

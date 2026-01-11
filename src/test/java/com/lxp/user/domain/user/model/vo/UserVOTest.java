package com.lxp.user.domain.user.model.vo;

import com.lxp.user.domain.common.exception.UserException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("User VO 테스트")
class UserVOTest {

    @Nested
    @DisplayName("UserName 테스트")
    class UserNameTest {

        @Test
        @DisplayName("유효한 이름으로 UserName을 생성한다")
        void shouldCreateValidUserName() {
            // when
            UserName userName = UserName.of("홍길동");

            // then
            assertThat(userName.value()).isEqualTo("홍길동");
        }

        @Test
        @DisplayName("5자 이하의 이름은 생성할 수 있다")
        void shouldCreateUserNameWithMaxLength() {
            // when
            UserName userName = UserName.of("12345");

            // then
            assertThat(userName.value()).isEqualTo("12345");
        }

        @Test
        @DisplayName("6자 이상의 이름은 예외를 발생시킨다")
        void shouldThrowExceptionWhenNameExceedsMaxLength() {
            // then
            assertThatThrownBy(() -> UserName.of("123456"))
                .isInstanceOf(UserException.class)
                .hasMessageContaining("5자를 초과할 수 없습니다");
        }

        @Test
        @DisplayName("null 이름은 예외를 발생시킨다")
        void shouldThrowExceptionWhenNameIsNull() {
            // then
            assertThatThrownBy(() -> UserName.of(null))
                .isInstanceOf(UserException.class)
                .hasMessageContaining("필수입니다");
        }

        @Test
        @DisplayName("빈 문자열 이름은 예외를 발생시킨다")
        void shouldThrowExceptionWhenNameIsBlank() {
            // then
            assertThatThrownBy(() -> UserName.of("   "))
                .isInstanceOf(UserException.class)
                .hasMessageContaining("필수입니다");
        }
    }

    @Nested
    @DisplayName("UserEmail 테스트")
    class UserEmailTest {

        @Test
        @DisplayName("유효한 이메일로 UserEmail을 생성한다")
        void shouldCreateValidUserEmail() {
            // when
            UserEmail email = UserEmail.of("test@example.com");

            // then
            assertThat(email.value()).isEqualTo("test@example.com");
        }

        @Test
        @DisplayName("다양한 형식의 유효한 이메일을 생성한다")
        void shouldCreateVariousValidEmails() {
            assertThat(UserEmail.of("user@domain.com").value()).isEqualTo("user@domain.com");
            assertThat(UserEmail.of("user.name@domain.com").value()).isEqualTo("user.name@domain.com");
            assertThat(UserEmail.of("user+tag@domain.co.kr").value()).isEqualTo("user+tag@domain.co.kr");
            assertThat(UserEmail.of("user_123@sub.domain.com").value()).isEqualTo("user_123@sub.domain.com");
        }

        @Test
        @DisplayName("잘못된 형식의 이메일은 예외를 발생시킨다")
        void shouldThrowExceptionForInvalidEmail() {
            assertThatThrownBy(() -> UserEmail.of("invalid"))
                .isInstanceOf(UserException.class);
            
            assertThatThrownBy(() -> UserEmail.of("@domain.com"))
                .isInstanceOf(UserException.class);
            
            assertThatThrownBy(() -> UserEmail.of("user@"))
                .isInstanceOf(UserException.class);
            
            assertThatThrownBy(() -> UserEmail.of("user@domain"))
                .isInstanceOf(UserException.class);
        }

        @Test
        @DisplayName("null 이메일은 예외를 발생시킨다")
        void shouldThrowExceptionWhenEmailIsNull() {
            // then
            assertThatThrownBy(() -> UserEmail.of(null))
                .isInstanceOf(UserException.class);
        }
    }

    @Nested
    @DisplayName("UserRole 테스트")
    class UserRoleTest {

        @Test
        @DisplayName("UserRole은 올바른 description을 반환한다")
        void shouldReturnCorrectDescription() {
            assertThat(UserRole.LEARNER.getDescription()).isEqualTo("학습자");
            assertThat(UserRole.INSTRUCTOR.getDescription()).isEqualTo("강사");
            assertThat(UserRole.ADMIN.getDescription()).isEqualTo("관리자");
        }

        @Test
        @DisplayName("문자열로부터 UserRole을 찾는다")
        void shouldFindUserRoleFromString() {
            assertThat(UserRole.fromString("LEARNER")).isPresent().contains(UserRole.LEARNER);
            assertThat(UserRole.fromString("learner")).isPresent().contains(UserRole.LEARNER);
            assertThat(UserRole.fromString("INSTRUCTOR")).isPresent().contains(UserRole.INSTRUCTOR);
            assertThat(UserRole.fromString("ADMIN")).isPresent().contains(UserRole.ADMIN);
        }

        @Test
        @DisplayName("존재하지 않는 Role은 Empty를 반환한다")
        void shouldReturnEmptyForInvalidRole() {
            assertThat(UserRole.fromString("INVALID")).isEmpty();
            assertThat(UserRole.fromString("")).isEmpty();
        }
    }

    @Nested
    @DisplayName("UserStatus 테스트")
    class UserStatusTest {

        @Test
        @DisplayName("문자열로부터 UserStatus를 찾는다")
        void shouldFindUserStatusFromString() {
            assertThat(UserStatus.fromString("ACTIVE")).isPresent().contains(UserStatus.ACTIVE);
            assertThat(UserStatus.fromString("active")).isPresent().contains(UserStatus.ACTIVE);
            assertThat(UserStatus.fromString("DELETED")).isPresent().contains(UserStatus.DELETED);
        }

        @Test
        @DisplayName("ACTIVE 상태는 활성 상태이다")
        void shouldReturnTrueForActiveStatus() {
            assertThat(UserStatus.ACTIVE.isActive()).isTrue();
        }

        @Test
        @DisplayName("DELETED 상태는 비활성 상태이다")
        void shouldReturnFalseForDeletedStatus() {
            assertThat(UserStatus.DELETED.isActive()).isFalse();
        }

        @Test
        @DisplayName("ACTIVE는 ACTIVE나 DELETED로 전환할 수 있다")
        void shouldAllowTransitionFromActive() {
            assertThat(UserStatus.ACTIVE.catTransitionTo(UserStatus.ACTIVE)).isTrue();
            assertThat(UserStatus.ACTIVE.catTransitionTo(UserStatus.DELETED)).isTrue();
        }

        @Test
        @DisplayName("DELETED는 다른 상태로 전환할 수 없다")
        void shouldNotAllowTransitionFromDeleted() {
            assertThat(UserStatus.DELETED.catTransitionTo(UserStatus.ACTIVE)).isFalse();
            assertThat(UserStatus.DELETED.catTransitionTo(UserStatus.DELETED)).isFalse();
        }
    }
}

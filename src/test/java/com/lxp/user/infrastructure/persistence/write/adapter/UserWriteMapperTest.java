package com.lxp.user.infrastructure.persistence.write.adapter;

import com.lxp.user.domain.common.model.vo.Level;
import com.lxp.user.domain.common.model.vo.UserId;
import com.lxp.user.domain.profile.model.entity.UserProfile;
import com.lxp.user.domain.profile.model.vo.Tags;
import com.lxp.user.domain.user.model.entity.User;
import com.lxp.user.domain.user.model.vo.UserEmail;
import com.lxp.user.domain.user.model.vo.UserName;
import com.lxp.user.domain.user.model.vo.UserRole;
import com.lxp.user.domain.user.model.vo.UserStatus;
import com.lxp.user.infrastructure.persistence.vo.InfraLevel;
import com.lxp.user.infrastructure.persistence.vo.InfraUserRole;
import com.lxp.user.infrastructure.persistence.vo.InfraUserStatus;
import com.lxp.user.infrastructure.persistence.write.entity.UserJpaEntity;
import com.lxp.user.infrastructure.persistence.write.entity.UserProfileJpaEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("UserWriteMapper 단위 테스트")
class UserWriteMapperTest {

    private UserWriteMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new UserWriteMapper();
    }

    @Test
    @DisplayName("Domain User를 JPA Entity로 변환한다")
    void shouldConvertDomainUserToEntity() {
        // given
        UserId userId = UserId.of(UUID.randomUUID());
        User user = User.of(
            userId,
            UserName.of("테스트유저"),
            UserEmail.of("test@example.com"),
            UserRole.LEARNER,
            UserStatus.ACTIVE,
            null,
            null
        );

        // when
        UserJpaEntity entity = mapper.toEntity(user);

        // then
        assertThat(entity).isNotNull();
        assertThat(entity.getId()).isEqualTo(userId.asString());
        assertThat(entity.getName()).isEqualTo("테스트유저");
        assertThat(entity.getEmail()).isEqualTo("test@example.com");
        assertThat(entity.getRole()).isEqualTo(InfraUserRole.LEARNER);
        assertThat(entity.getUserStatus()).isEqualTo(InfraUserStatus.ACTIVE);
    }

    @Test
    @DisplayName("Domain UserProfile을 JPA Entity로 변환한다")
    void shouldConvertDomainProfileToEntity() {
        // given
        UserId userId = UserId.of(UUID.randomUUID());
        UserProfile profile = UserProfile.create(
            userId,
            Level.JUNIOR,
            new Tags(List.of(1L, 2L, 3L))
        );

        // when
        UserProfileJpaEntity entity = mapper.toEntity(profile);

        // then
        assertThat(entity).isNotNull();
        assertThat(entity.getLevel()).isEqualTo(InfraLevel.JUNIOR);
        assertThat(entity.getTags()).containsExactly(1L, 2L, 3L);
    }

    @Test
    @DisplayName("Domain User를 기존 JPA Entity에 업데이트한다")
    void shouldUpdateEntityFromDomain() {
        // given
        UserJpaEntity entity = UserJpaEntity.of(
            UUID.randomUUID(),
            "기존이름",
            "old@example.com",
            InfraUserRole.LEARNER,
            InfraUserStatus.ACTIVE,
            null
        );

        User updatedUser = User.of(
            UserId.of(UUID.fromString(entity.getId())),
            UserName.of("새로운이름"),
            UserEmail.of("old@example.com"),
            UserRole.INSTRUCTOR,
            UserStatus.ACTIVE,
            null,
            null
        );

        // when
        mapper.updateUserFromDomain(updatedUser, entity);

        // then
        assertThat(entity.getName()).isEqualTo("새로운이름");
        assertThat(entity.getRole()).isEqualTo(InfraUserRole.INSTRUCTOR);
    }

    @Test
    @DisplayName("Domain Profile을 기존 JPA Entity에 업데이트한다")
    void shouldUpdateProfileEntityFromDomain() {
        // given
        UserProfileJpaEntity entity = UserProfileJpaEntity.builder()
            .level(InfraLevel.JUNIOR)
            .tags(new ArrayList<>(List.of(1L, 2L)))
            .build();

        UserProfile updatedProfile = UserProfile.create(
            UserId.of(UUID.randomUUID()),
            Level.SENIOR,
            new Tags(new ArrayList<>(List.of(5L, 6L, 7L)))
        );

        // when
        mapper.updateProfileEntityFromDomain(updatedProfile, entity);

        // then
        assertThat(entity.getLevel()).isEqualTo(InfraLevel.SENIOR);
        assertThat(entity.getTags()).containsExactly(5L, 6L, 7L);
    }

    @Test
    @DisplayName("Domain Role이 Infrastructure Role로 올바르게 변환된다")
    void shouldConvertRoleCorrectly() {
        // given
        User learner = User.of(
            UserId.of(UUID.randomUUID()),
            UserName.of("학습자"),
            UserEmail.of("learner@test.com"),
            UserRole.LEARNER,
            UserStatus.ACTIVE,
            null,
            null
        );

        User instructor = User.of(
            UserId.of(UUID.randomUUID()),
            UserName.of("강사"),
            UserEmail.of("instructor@test.com"),
            UserRole.INSTRUCTOR,
            UserStatus.ACTIVE,
            null,
            null
        );

        User admin = User.of(
            UserId.of(UUID.randomUUID()),
            UserName.of("관리자"),
            UserEmail.of("admin@test.com"),
            UserRole.ADMIN,
            UserStatus.ACTIVE,
            null,
            null
        );

        // when
        UserJpaEntity learnerEntity = mapper.toEntity(learner);
        UserJpaEntity instructorEntity = mapper.toEntity(instructor);
        UserJpaEntity adminEntity = mapper.toEntity(admin);

        // then
        assertThat(learnerEntity.getRole()).isEqualTo(InfraUserRole.LEARNER);
        assertThat(instructorEntity.getRole()).isEqualTo(InfraUserRole.INSTRUCTOR);
        assertThat(adminEntity.getRole()).isEqualTo(InfraUserRole.ADMIN);
    }
}

package com.lxp.user.infrastructure.persistence.read.repository;

import com.lxp.user.infrastructure.persistence.read.dto.UserDetailDto;
import com.lxp.user.infrastructure.persistence.read.dto.UserStatusProjection;
import com.lxp.user.infrastructure.persistence.read.dto.UserSummaryDto;
import com.lxp.user.infrastructure.persistence.vo.InfraLevel;
import com.lxp.user.infrastructure.persistence.vo.InfraUserRole;
import com.lxp.user.infrastructure.persistence.vo.InfraUserStatus;
import com.lxp.user.infrastructure.persistence.write.entity.UserJpaEntity;
import com.lxp.user.infrastructure.persistence.write.entity.UserProfileJpaEntity;
import com.lxp.user.infrastructure.persistence.write.repository.UserProfileWriteRepository;
import com.lxp.user.infrastructure.persistence.write.repository.UserWriteRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@EnableJpaAuditing
@ActiveProfiles("test")
@EntityScan(basePackages = {"com.lxp.user", "com.lxp.common.infrastructure.persistence"})
@EnableJpaRepositories(basePackages = {"com.lxp.user", "com.lxp.common"})
@DisplayName("UserReadRepository 통합 테스트")
class UserReadRepositoryIntegrationTest {

    @Autowired
    private UserReadRepository userReadRepository;

    @Autowired
    private UserWriteRepository userWriteRepository;

    @Autowired
    private UserProfileWriteRepository profileWriteRepository;

    @Test
    @DisplayName("UserSummaryDto Projection으로 User를 조회한다")
    void shouldFindUserSummaryById() {
        // given
        UserJpaEntity user = createAndSaveUser("테스트유저", "test@example.com", InfraUserRole.LEARNER);

        // when
        Optional<UserSummaryDto> result = userReadRepository.findUserSummaryById(user.getId());

        // then
        assertThat(result).isPresent();
        assertThat(result.get().id()).isEqualTo(user.getId());
        assertThat(result.get().name()).isEqualTo("테스트유저");
        assertThat(result.get().email()).isEqualTo("test@example.com");
        assertThat(result.get().role()).isEqualTo(InfraUserRole.LEARNER);
    }

    @Test
    @DisplayName("UserDetailDto Projection으로 User + Profile을 조회한다")
    void shouldFindUserDetailWithProfile() {
        // given
        UserJpaEntity user = createAndSaveUser("프로필유저", "profile@example.com", InfraUserRole.INSTRUCTOR);
        UserProfileJpaEntity profile = createAndSaveProfile(user, InfraLevel.MIDDLE, List.of(1L, 2L, 3L));

        // when
        Optional<UserDetailDto> result = userReadRepository.findUserDetailById(user.getId());
        List<Long> tagsByUserId = userReadRepository.findTagsByUserId(user.getId());

        // then
        assertThat(result).isPresent();
        UserDetailDto dto = result.get();
        assertThat(dto.id()).isEqualTo(user.getId());
        assertThat(dto.name()).isEqualTo("프로필유저");
        assertThat(dto.level()).isEqualTo(InfraLevel.MIDDLE);
        assertThat(tagsByUserId).containsExactly(1L, 2L, 3L);
    }

    @Test
    @DisplayName("Profile이 없는 User를 조회할 때 null Profile로 반환한다")
    void shouldFindUserDetailWithoutProfile() {
        // given
        UserJpaEntity user = createAndSaveUser("프로필없음", "no-profile@example.com", InfraUserRole.ADMIN);

        // when
        Optional<UserDetailDto> result = userReadRepository.findUserDetailById(user.getId());
        List<Long> tagsByUserId = userReadRepository.findTagsByUserId(user.getId());

        // then
        assertThat(result).isPresent();
        assertThat(result.get().level()).isNull();
        assertThat(tagsByUserId).isEmpty();
    }

    @Test
    @DisplayName("UserStatusProjection으로 UserStatus만 조회한다")
    void shouldFindUserStatusById() {
        // given
        UserJpaEntity user = createAndSaveUser("상태조회", "status@example.com", InfraUserRole.LEARNER);

        // when
        Optional<UserStatusProjection> result = userReadRepository.findUserStatusById(user.getId());

        // then
        assertThat(result).isPresent();
        assertThat(result.get().getId()).isEqualTo(user.getId());
        assertThat(result.get().getUserStatus()).isEqualTo(InfraUserStatus.ACTIVE);
    }

    @Test
    @DisplayName("이메일로 User를 조회한다")
    void shouldFindUserByEmail() {
        // given
        String email = "unique@example.com";
        UserJpaEntity user = createAndSaveUser("이메일조회", email, InfraUserRole.LEARNER);

        // when
        Optional<UserSummaryDto> result = userReadRepository.findUserSummaryByEmail(email);

        // then
        assertThat(result).isPresent();
        assertThat(result.get().email()).isEqualTo(email);
    }

    @Test
    @DisplayName("Status로 User 목록을 조회한다")
    void shouldFindAllByStatus() {
        // given
        createAndSaveUser("활성유저1", "active1@example.com", InfraUserRole.LEARNER);
        createAndSaveUser("활성유저2", "active2@example.com", InfraUserRole.INSTRUCTOR);

        // when
        List<UserSummaryDto> results = userReadRepository.findAllByStatus(InfraUserStatus.ACTIVE);

        // then
        assertThat(results).hasSizeGreaterThanOrEqualTo(2);
        assertThat(results).allMatch(dto -> dto.userStatus() == InfraUserStatus.ACTIVE);
    }

    @Test
    @DisplayName("존재하지 않는 User를 조회하면 Empty를 반환한다")
    void shouldReturnEmptyWhenUserNotFound() {
        // given
        String nonExistentId = UUID.randomUUID().toString();

        // when
        Optional<UserSummaryDto> result = userReadRepository.findUserSummaryById(nonExistentId);

        // then
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("DTO Projection은 Entity를 영속화하지 않는다")
    void shouldNotPersistEntityWhenProjection() {
        // given
        UserJpaEntity user = createAndSaveUser("영속화테스트", "persist@example.com", InfraUserRole.LEARNER);

        // when
        UserSummaryDto dto = userReadRepository.findUserSummaryById(user.getId()).orElseThrow();

        // then - DTO는 Entity가 아니므로 영속성 컨텍스트에 없음
        assertThat(dto).isNotNull();
        assertThat(dto).isInstanceOf(UserSummaryDto.class);
    }

    // Helper Methods
    private UserJpaEntity createAndSaveUser(String name, String email, InfraUserRole role) {
        UserJpaEntity user = UserJpaEntity.of(
            UUID.randomUUID(),
            name,
            email,
            role,
            InfraUserStatus.ACTIVE,
            null
        );
        return userWriteRepository.save(user);
    }

    private UserProfileJpaEntity createAndSaveProfile(UserJpaEntity user, InfraLevel level, List<Long> tags) {
        UserProfileJpaEntity profile = UserProfileJpaEntity.builder()
            .user(user)
            .level(level)
            .tags(tags)
            .build();
        return profileWriteRepository.save(profile);
    }
}

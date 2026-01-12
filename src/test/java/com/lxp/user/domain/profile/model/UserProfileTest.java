package com.lxp.user.domain.profile.model;

import com.lxp.user.domain.common.exception.UserException;
import com.lxp.user.domain.common.model.vo.Level;
import com.lxp.user.domain.common.model.vo.UserId;
import com.lxp.user.domain.profile.exception.TagSizeConstraintViolationException;
import com.lxp.user.domain.profile.model.entity.UserProfile;
import com.lxp.user.domain.profile.model.vo.Tags;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("UserProfile 도메인 테스트")
class UserProfileTest {

    @Test
    @DisplayName("UserProfile을 생성한다")
    void shouldCreateUserProfile() {
        // given
        UserId userId = UserId.of(UUID.randomUUID());
        Level level = Level.JUNIOR;
        Tags tags = new Tags(List.of(1L, 2L, 3L));

        // when
        UserProfile profile = UserProfile.create(userId, level, tags);

        // then
        assertThat(profile).isNotNull();
        assertThat(profile.userId()).isEqualTo(userId);
        assertThat(profile.level()).isEqualTo(Level.JUNIOR);
        assertThat(profile.tags().values()).containsExactly(1L, 2L, 3L);
    }

    @Test
    @DisplayName("UserProfile 생성 시 필수값이 null이면 예외가 발생한다")
    void shouldThrowExceptionWhenRequiredFieldsAreNull() {
        // given
        UserId userId = UserId.of(UUID.randomUUID());
        Level level = Level.JUNIOR;
        Tags tags = new Tags(List.of(1L, 2L, 3L));

        // then
        assertThatThrownBy(() ->
            UserProfile.create(null, level, tags)
        ).isInstanceOf(UserException.class)
            .hasMessageContaining("userId는 null일 수 없습니다");

        assertThatThrownBy(() ->
            UserProfile.create(userId, null, tags)
        ).isInstanceOf(UserException.class)
            .hasMessageContaining("level은 null일 수 없습니다");

        assertThatThrownBy(() ->
            UserProfile.create(userId, level, null)
        ).isInstanceOf(UserException.class)
            .hasMessageContaining("tags는 null일 수 없습니다");
    }

    @Test
    @DisplayName("UserProfile의 level과 tags를 업데이트한다")
    void shouldUpdateUserProfile() {
        // given
        UserId userId = UserId.of(UUID.randomUUID());
        UserProfile profile = UserProfile.create(
            userId,
            Level.JUNIOR,
            new Tags(List.of(1L, 2L, 3L))
        );

        // when
        profile.update(Level.MIDDLE, List.of(4L, 5L, 6L));

        // then
        assertThat(profile.level()).isEqualTo(Level.MIDDLE);
        assertThat(profile.tags().values()).containsExactly(4L, 5L, 6L);
    }

    @Test
    @DisplayName("UserProfile 업데이트 시 null 값은 기존 값을 유지한다")
    void shouldKeepExistingValueWhenUpdateWithNull() {
        // given
        UserId userId = UserId.of(UUID.randomUUID());
        UserProfile profile = UserProfile.create(
            userId,
            Level.JUNIOR,
            new Tags(List.of(1L, 2L, 3L))
        );

        // when
        profile.update(null, null);

        // then
        assertThat(profile.level()).isEqualTo(Level.JUNIOR);
        assertThat(profile.tags().values()).containsExactly(1L, 2L, 3L);
    }

    @Test
    @DisplayName("Tags는 최소 3개 이상이어야 한다")
    void shouldRequireMinimumThreeTags() {
        // then
        assertThatThrownBy(() -> new Tags(List.of(1L, 2L)))
            .isInstanceOf(TagSizeConstraintViolationException.class);
    }

    @Test
    @DisplayName("Tags는 최대 5개까지만 허용한다")
    void shouldAllowMaximumFiveTags() {
        // then
        assertThatThrownBy(() -> new Tags(List.of(1L, 2L, 3L, 4L, 5L, 6L)))
            .isInstanceOf(TagSizeConstraintViolationException.class);
    }

    @Test
    @DisplayName("Tags는 3~5개 사이의 값을 허용한다")
    void shouldAllowTagsBetweenThreeAndFive() {
        // given & when & then
        assertThat(new Tags(List.of(1L, 2L, 3L)).values()).hasSize(3);
        assertThat(new Tags(List.of(1L, 2L, 3L, 4L)).values()).hasSize(4);
        assertThat(new Tags(List.of(1L, 2L, 3L, 4L, 5L)).values()).hasSize(5);
    }

    @Test
    @DisplayName("Tags는 불변 객체이다")
    void shouldBeImmutableTags() {
        // given
        List<Long> originalList = List.of(1L, 2L, 3L);
        Tags tags = new Tags(originalList);

        // when
        List<Long> retrievedTags = tags.values();

        // then
        assertThatThrownBy(() -> retrievedTags.add(4L))
            .isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    @DisplayName("Tags는 새로운 태그 리스트로 교체할 수 있다")
    void shouldReplaceTagsWithNewList() {
        // given
        Tags tags = new Tags(List.of(1L, 2L, 3L));

        // when
        Tags newTags = tags.withTags(List.of(4L, 5L, 6L));

        // then
        assertThat(tags.values()).containsExactly(1L, 2L, 3L);
        assertThat(newTags.values()).containsExactly(4L, 5L, 6L);
    }

    @Test
    @DisplayName("Level은 올바른 description을 반환한다")
    void shouldReturnCorrectLevelDescription() {
        assertThat(Level.JUNIOR.description()).isEqualTo("주니어");
        assertThat(Level.MIDDLE.description()).isEqualTo("미들");
        assertThat(Level.SENIOR.description()).isEqualTo("시니어");
        assertThat(Level.EXPERT.description()).isEqualTo("익스퍼트");
    }

    @Test
    @DisplayName("문자열로부터 Level을 찾는다")
    void shouldFindLevelFromString() {
        assertThat(Level.fromString("JUNIOR")).isPresent().contains(Level.JUNIOR);
        assertThat(Level.fromString("junior")).isPresent().contains(Level.JUNIOR);
        assertThat(Level.fromString("MIDDLE")).isPresent().contains(Level.MIDDLE);
        assertThat(Level.fromString("SENIOR")).isPresent().contains(Level.SENIOR);
        assertThat(Level.fromString("EXPERT")).isPresent().contains(Level.EXPERT);
    }

    @Test
    @DisplayName("존재하지 않는 Level은 Empty를 반환한다")
    void shouldReturnEmptyForInvalidLevel() {
        assertThat(Level.fromString("INVALID")).isEmpty();
        assertThat(Level.fromString("")).isEmpty();
    }
}

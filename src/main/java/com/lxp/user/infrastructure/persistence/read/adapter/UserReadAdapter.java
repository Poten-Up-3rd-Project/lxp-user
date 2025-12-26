package com.lxp.user.infrastructure.persistence.read.adapter;

import com.lxp.user.application.out.UserQueryPort;
import com.lxp.user.domain.common.model.vo.UserId;
import com.lxp.user.domain.user.model.entity.User;
import com.lxp.user.domain.user.model.vo.UserStatus;
import com.lxp.user.infrastructure.persistence.read.repository.UserReadRepository;
import com.lxp.user.infrastructure.persistence.write.entity.UserJpaEntity;
import com.lxp.user.infrastructure.persistence.write.entity.UserProfileJpaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserReadAdapter implements UserQueryPort {

    private final UserReadRepository userReadRepository;
    private final UserReadMapper userReadMapper;

    @Override
    public Optional<User> getUserById(String id) {
        return userReadRepository.findUserSummaryById(id).map(userReadMapper::toDomain);
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return userReadRepository.findUserSummaryByEmail(email).map(userReadMapper::toDomain);
    }

    @Override
    public Optional<UserStatus> findUserStatusById(UserId userId) {
        return userReadRepository.findUserStatusById(userId.asString())
            .map(status -> status.getUserStatus().toDomain());
    }

    @Override
    public Optional<User> findAggregateUserById(UserId userId) {
        return userReadRepository.findUserDetailById(userId.asString())
            .map(userReadMapper::toDomainWithProfile);
    }

    /**
     * [Performance Alternative] EntityGraph 기반 Aggregate 로딩 로직
     *
     * @implNote 가이드라인에 따른 'JPQL DTO Projection' 방식 사용 중, @ElementCollection(tags) 로딩 시
     * N+1 문제나 성능 저하가 발생할 경우를 대비한 대안 로직입니다.
     * <p>
     * @구조 프로필이 없는 유저를 고려하여 User를 우선 조회한 뒤, Profile과 Tags를 추가 로딩하는 1+1 전략을 사용합니다.
     * @장점 @EntityGraph를 활용해 Profile 조회 시 Tags를 Fetch Join으로 가져오므로,
     * 태그 개수만큼 쿼리가 발생하는 N+1 문제를 방지합니다.
     * @특징 'Projection 우선' 원칙보다 성능 최적화와 도메인 Aggregate(User-Profile-Tags)의
     * 완전한 상태 보존에 우선순위를 둔 방식입니다.
     */
    @Deprecated
    public Optional<User> findAggregateUserByIdWithEntityGraph(UserId userId) {
        UserJpaEntity userEntity = userReadRepository.findById(userId.asString())
            .orElse(null);

        if (userEntity == null) {
            return Optional.empty();
        }

        UserProfileJpaEntity profileEntity = userReadRepository
            .findProfileWithTagsByUserId(userId.asString())
            .orElse(null);

        return Optional.of(userReadMapper.toDomain(userEntity, profileEntity));
    }

    @Override
    public Optional<User> findAggregateUserByEmail(String email) {
        return userReadRepository.findUserDetailByEmail(email)
            .map(userReadMapper::toDomainWithProfile);
    }

    /**
     * [Performance Alternative] EntityGraph 기반 Aggregate 로딩 로직
     *
     * @implNote 기본 방식인 JPQL DTO Projection(findUserDetailByEmailJpql) 방식에서
     * `@ElementCollection(tags)` 로딩 시 성능 저하가 발생하거나,
     * 복잡한 도메인 Aggregate 변환 로직이 필요할 때 사용하기 위한 예비 로직입니다.
     * <p>
     * @구조 프로필이 없는 유저를 고려하여 User를 우선 조회한 뒤, Profile과 Tags를 추가 로딩하는 1+1 전략을 사용합니다.
     * @장점 @EntityGraph를 활용해 Profile 조회 시 Tags를 Fetch Join으로 가져오므로,
     * 태그 개수만큼 쿼리가 발생하는 N+1 문제를 방지합니다.
     * @특징 'Projection 우선' 원칙보다 성능 최적화와 도메인 Aggregate(User-Profile-Tags)의
     * 완전한 상태 보존에 우선순위를 둔 방식입니다.
     */
    @Deprecated
    public Optional<User> findAggregateUserByEmailWithEntityGraph(String email) {
        UserJpaEntity userEntity = userReadRepository.findByEmail(email)
            .orElse(null);

        if (userEntity == null) {
            return Optional.empty();
        }

        UserProfileJpaEntity profileEntity = userReadRepository
            .findProfileWithTagsByEmail(email)
            .orElse(null);

        return Optional.of(userReadMapper.toDomain(userEntity, profileEntity));
    }

}

package com.lxp.user.infrastructure.persistence.read.repository;

import com.lxp.user.infrastructure.persistence.read.dto.UserDetailDto;
import com.lxp.user.infrastructure.persistence.read.dto.UserStatusProjection;
import com.lxp.user.infrastructure.persistence.read.dto.UserSummaryDto;
import com.lxp.user.infrastructure.persistence.vo.InfraUserStatus;
import com.lxp.user.infrastructure.persistence.write.entity.UserJpaEntity;
import com.lxp.user.infrastructure.persistence.write.entity.UserProfileJpaEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserReadRepository extends JpaRepository<UserJpaEntity, String> {

    @Query("""
        SELECT new com.lxp.user.infrastructure.persistence.read.dto.UserSummaryDto(
            u.id, u.name, u.email, u.role, u.userStatus, u.deletedAt
        )
        FROM UserJpaEntity u
        WHERE u.userStatus = :status
        """)
    List<UserSummaryDto> findAllByStatus(@Param("status") InfraUserStatus status);

    @Query(value = """
        SELECT new com.lxp.user.infrastructure.persistence.read.dto.UserDetailDto(
            u.id, u.name, u.email, u.role, u.userStatus,p.level, p.tags, u.createdAt, u.deletedAt
        )
        FROM UserJpaEntity u
        LEFT JOIN UserProfileJpaEntity p ON p.user.id = u.id
        WHERE u.id = :userId
        """)
    Optional<UserDetailDto> findUserDetailById(@Param("userId") String userId);

    @Query("SELECT u.id as id, u.userStatus as userStatus FROM UserJpaEntity u WHERE u.id = :id")
    Optional<UserStatusProjection> findUserStatusById(@Param("id") String id);

    @Query("""
        SELECT new com.lxp.user.infrastructure.persistence.read.dto.UserDetailDto(
            u.id, u.name, u.email, u.role, u.userStatus, p.level, p.tags, u.createdAt, u.deletedAt
        )
        FROM UserJpaEntity u
        LEFT JOIN UserProfileJpaEntity p ON p.user.id = u.id
        WHERE u.email = :email
        """)
    Optional<UserDetailDto> findUserDetailByEmail(@Param("email") String email);

    @Query("""
        SELECT new com.lxp.user.infrastructure.persistence.read.dto.UserSummaryDto(
            u.id, u.name, u.email, u.role, u.userStatus, u.deletedAt
        )
        FROM UserJpaEntity u
        WHERE u.id = :id
        """)
    Optional<UserSummaryDto> findUserSummaryById(@Param("id") String id);

    @Query("""
        SELECT new com.lxp.user.infrastructure.persistence.read.dto.UserSummaryDto(
            u.id, u.name, u.email, u.role, u.userStatus, u.deletedAt
        )
        FROM UserJpaEntity u
        WHERE u.email = :email
        """)
    Optional<UserSummaryDto> findUserSummaryByEmail(@Param("email") String email);

    @EntityGraph(attributePaths = {"user", "tags"})
    @Query("SELECT p FROM UserProfileJpaEntity p WHERE p.user.id = :userId")
    Optional<UserProfileJpaEntity> findProfileWithTagsByUserId(@Param("userId") String userId);

    @EntityGraph(attributePaths = {"user", "tags"})
    @Query("SELECT p FROM UserProfileJpaEntity p WHERE p.user.email = :email")
    Optional<UserProfileJpaEntity> findProfileWithTagsByEmail(@Param("email") String email);

    Optional<UserJpaEntity> findByEmail(String email);
}

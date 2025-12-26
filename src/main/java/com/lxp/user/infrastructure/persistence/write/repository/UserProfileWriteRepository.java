package com.lxp.user.infrastructure.persistence.write.repository;

import com.lxp.user.infrastructure.persistence.write.entity.UserJpaEntity;
import com.lxp.user.infrastructure.persistence.write.entity.UserProfileJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserProfileWriteRepository extends JpaRepository<UserProfileJpaEntity, Long> {

    @Query("SELECT p FROM UserProfileJpaEntity p " +
        "LEFT JOIN FETCH p.tags " +
        "WHERE p.user = :user")
    Optional<UserProfileJpaEntity> findByUserWithTags(@Param("user") UserJpaEntity user);

}

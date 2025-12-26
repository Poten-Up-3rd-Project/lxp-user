package com.lxp.user.infrastructure.persistence.write.repository;

import com.lxp.user.infrastructure.persistence.write.entity.UserJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserWriteRepository extends JpaRepository<UserJpaEntity, String> {
}

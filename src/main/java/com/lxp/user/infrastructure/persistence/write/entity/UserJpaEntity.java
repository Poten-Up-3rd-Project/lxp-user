package com.lxp.user.infrastructure.persistence.write.entity;

import com.lxp.common.infrastructure.persistence.BaseUuidJpaEntity;
import com.lxp.user.infrastructure.persistence.vo.InfraUserRole;
import com.lxp.user.infrastructure.persistence.vo.InfraUserStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserJpaEntity extends BaseUuidJpaEntity {

    @Column(name = "name", nullable = false, length = 10)
    private String name;

    @Column(name = "email", nullable = false, unique = true, length = 50)
    private String email;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private InfraUserRole role;

    @Column(name = "user_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private InfraUserStatus userStatus;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Builder
    public UserJpaEntity(String id, String name, String email, InfraUserRole role, InfraUserStatus userStatus, LocalDateTime deletedAt) {
        super.setId(id);
        this.name = name;
        this.email = email;
        this.role = role;
        this.userStatus = userStatus;
        this.deletedAt = deletedAt;
    }

    public static UserJpaEntity of(UUID id, String name, String email, InfraUserRole role, InfraUserStatus userStatus, LocalDateTime deletedAt) {
        return new UserJpaEntity(id.toString(), name, email, role, userStatus, deletedAt);
    }

    public void update(String name, InfraUserRole role, InfraUserStatus userStatus, LocalDateTime deletedAt) {
        this.name = name;
        this.role = role;
        this.userStatus = userStatus;
        this.deletedAt = deletedAt;
    }
}

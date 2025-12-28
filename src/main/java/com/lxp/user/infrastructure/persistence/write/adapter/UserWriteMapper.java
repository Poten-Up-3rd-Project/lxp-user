package com.lxp.user.infrastructure.persistence.write.adapter;

import com.lxp.user.domain.profile.model.entity.UserProfile;
import com.lxp.user.domain.user.model.entity.User;
import com.lxp.user.infrastructure.persistence.vo.InfraLevel;
import com.lxp.user.infrastructure.persistence.vo.InfraUserRole;
import com.lxp.user.infrastructure.persistence.vo.InfraUserStatus;
import com.lxp.user.infrastructure.persistence.write.entity.UserJpaEntity;
import com.lxp.user.infrastructure.persistence.write.entity.UserProfileJpaEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserWriteMapper {

    public UserJpaEntity toEntity(User user) {
        return UserJpaEntity.of(
            user.getId().value(),
            user.name(),
            user.email(),
            InfraUserRole.from(user.role()),
            InfraUserStatus.from(user.userStatus()),
            user.deletedAt()
        );
    }

    public UserProfileJpaEntity toEntity(UserProfile profile) {
        return UserProfileJpaEntity.builder()
            .level(InfraLevel.from(profile.level()))
            .tags(new ArrayList<>(profile.tags().values()))
            .build();
    }

    public void updateUserFromDomain(User user, UserJpaEntity userJpaEntity) {
        userJpaEntity.update(
            user.name(),
            InfraUserRole.from(user.role()),
            InfraUserStatus.from(user.userStatus()),
            user.deletedAt()
        );
    }

    public void updateProfileEntityFromDomain(UserProfile profile, UserProfileJpaEntity existingEntity) {
        existingEntity.setLevel(InfraLevel.from(profile.level()));
        existingEntity.changeTags(profile.tags().values());
    }
}

package com.lxp.user.infrastructure.persistence.read.adapter;

import com.lxp.user.domain.common.model.vo.UserId;
import com.lxp.user.domain.profile.model.entity.UserProfile;
import com.lxp.user.domain.profile.model.vo.Tags;
import com.lxp.user.domain.user.model.entity.User;
import com.lxp.user.domain.user.model.vo.UserEmail;
import com.lxp.user.domain.user.model.vo.UserName;
import com.lxp.user.infrastructure.persistence.read.dto.UserDetailDto;
import com.lxp.user.infrastructure.persistence.read.dto.UserSummaryDto;
import com.lxp.user.infrastructure.persistence.write.entity.UserJpaEntity;
import com.lxp.user.infrastructure.persistence.write.entity.UserProfileJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class UserReadMapper {

    public User toDomain(UserSummaryDto userSummaryDto) {
        return User.of(
            UserId.of(userSummaryDto.id()),
            UserName.of(userSummaryDto.name()),
            UserEmail.of(userSummaryDto.email()),
            userSummaryDto.role().toDomain(),
            userSummaryDto.userStatus().toDomain(),
            null,
            userSummaryDto.deletedAt()
        );
    }

    public User toDomainWithProfile(UserDetailDto userDetailDto) {
        UserId userId = UserId.of(userDetailDto.id());
        UserProfile userProfile = toProfile(userDetailDto, userId);

        return User.of(
            userId,
            UserName.of(userDetailDto.name()),
            UserEmail.of(userDetailDto.email()),
            userDetailDto.role().toDomain(),
            userDetailDto.status().toDomain(),
            userProfile,
            userDetailDto.deletedAt()
        );
    }


    public UserProfile toProfile(UserDetailDto userDetailDto, UserId userId) {
        return UserProfile.create(
            userId,
            userDetailDto.level().toDomain(),
            new Tags(userDetailDto.tags()));
    }

    public User toDomain(UserJpaEntity userEntity, UserProfileJpaEntity profileEntity) {
        UserId userId = UserId.of(userEntity.getId());

        UserProfile userProfile = null;
        if (profileEntity != null) {
            userProfile = UserProfile.create(
                userId,
                profileEntity.getLevel().toDomain(),
                new Tags(profileEntity.getTags())
            );
        }

        return User.of(
            userId,
            UserName.of(userEntity.getName()),
            UserEmail.of(userEntity.getEmail()),
            userEntity.getRole().toDomain(),
            userEntity.getUserStatus().toDomain(),
            userProfile,
            userEntity.getDeletedAt()
        );
    }
}

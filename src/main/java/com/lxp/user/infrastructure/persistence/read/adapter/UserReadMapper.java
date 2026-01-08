package com.lxp.user.infrastructure.persistence.read.adapter;

import com.lxp.user.application.port.required.query.UserView;
import com.lxp.user.application.port.required.query.UserWithProfileView;
import com.lxp.user.domain.common.model.vo.UserId;
import com.lxp.user.domain.user.model.vo.UserEmail;
import com.lxp.user.domain.user.model.vo.UserName;
import com.lxp.user.infrastructure.persistence.read.dto.UserDetailDto;
import com.lxp.user.infrastructure.persistence.read.dto.UserSummaryDto;
import com.lxp.user.infrastructure.persistence.write.entity.UserJpaEntity;
import com.lxp.user.infrastructure.persistence.write.entity.UserProfileJpaEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserReadMapper {

    public UserView toUserView(UserSummaryDto userSummaryDto) {
        return new UserView(
            UserId.of(userSummaryDto.id()),
            UserName.of(userSummaryDto.name()),
            UserEmail.of(userSummaryDto.email()),
            userSummaryDto.role().toDomain(),
            userSummaryDto.userStatus().toDomain(),
            userSummaryDto.deletedAt()
        );
    }

    public UserWithProfileView toUserWithProfileView(UserDetailDto userDetailDto, List<Long> tags) {
        return new UserWithProfileView(
            UserId.of(userDetailDto.id()),
            UserName.of(userDetailDto.name()),
            UserEmail.of(userDetailDto.email()),
            userDetailDto.role().toDomain(),
            userDetailDto.status().toDomain(),
            userDetailDto.level().toDomain(),
            tags,
            userDetailDto.createdAt(),
            userDetailDto.deletedAt()
        );
    }

    public UserWithProfileView toUserWithProfileView(UserJpaEntity userEntity, UserProfileJpaEntity profileEntity) {
        return new UserWithProfileView(
            UserId.of(userEntity.getId()),
            UserName.of(userEntity.getName()),
            UserEmail.of(userEntity.getEmail()),
            userEntity.getRole().toDomain(),
            userEntity.getUserStatus().toDomain(),
            profileEntity.getLevel().toDomain(),
            profileEntity.getTags(),
            userEntity.getCreatedAt(),
            userEntity.getDeletedAt()
        );
    }
}

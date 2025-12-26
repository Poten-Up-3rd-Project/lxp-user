package com.lxp.user.infrastructure.persistence.write.adapter;

import com.lxp.user.application.out.UserCommandPort;
import com.lxp.user.domain.common.exception.UserNotFoundException;
import com.lxp.user.domain.profile.exception.ProfileNotFoundException;
import com.lxp.user.domain.user.model.entity.User;
import com.lxp.user.infrastructure.persistence.write.entity.UserJpaEntity;
import com.lxp.user.infrastructure.persistence.write.entity.UserProfileJpaEntity;
import com.lxp.user.infrastructure.persistence.write.repository.UserProfileWriteRepository;
import com.lxp.user.infrastructure.persistence.write.repository.UserWriteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
@Transactional
public class UserWriteAdapter implements UserCommandPort {

    private final UserWriteRepository userWriteRepository;
    private final UserDomainMapper userDomainMapper;
    private final UserProfileWriteRepository userProfileWriteRepository;

    @Override
    public void save(User user) {
        userWriteRepository.findById(user.id().asString()).ifPresentOrElse(
            existingUser -> userDomainMapper.updateUserFromDomain(user, existingUser),
            () -> userWriteRepository.save(userDomainMapper.toEntity(user))
        );
    }

    @Override
    public void saveWithProfile(User user) {
        UserJpaEntity userEntity = userWriteRepository.findById(user.id().asString())
            .orElse(null);

        if (Objects.nonNull(userEntity)) {
            userDomainMapper.updateUserFromDomain(user, userEntity);
            UserProfileJpaEntity profileEntity = userProfileWriteRepository.findByUserWithTags(userEntity)
                .orElseThrow(ProfileNotFoundException::new);

            userDomainMapper.updateProfileEntityFromDomain(user.profile(), profileEntity);
        } else {
            UserJpaEntity newUser = userDomainMapper.toEntity(user);
            userWriteRepository.save(newUser);

            UserProfileJpaEntity newProfile = userDomainMapper.toEntity(user.profile());
            newProfile.setUser(newUser);
            userProfileWriteRepository.save(newProfile);
        }
    }

    @Override
    public void deactivate(User user) {
        UserJpaEntity userEntity = userWriteRepository.findById(user.id().asString())
            .orElseThrow(UserNotFoundException::new);
        userDomainMapper.updateUserFromDomain(user, userEntity);
    }

}

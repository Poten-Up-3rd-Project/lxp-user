package com.lxp.user.infrastructure.web.internal.controller;

import com.lxp.user.application.port.provided.dto.UserInfoInternalResult;
import com.lxp.user.application.port.provided.dto.UserProfileInternalResult;
import com.lxp.user.application.port.provided.dto.UserRoleInternalResult;
import com.lxp.user.application.port.provided.usecase.UserFindRoleInternalUseCase;
import com.lxp.user.application.port.provided.usecase.UserInfoInternalUseCase;
import com.lxp.user.application.port.provided.usecase.UserProfileInternalUseCase;
import com.lxp.user.application.port.provided.usecase.UserSaveUseCase;
import com.lxp.user.infrastructure.web.internal.controller.dto.UserSaveRequest;
import com.lxp.user.infrastructure.web.internal.controller.mapper.UserInternalMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("/internal/api-v1/users")
@RequiredArgsConstructor
public class UserInternalController {

    @Value("${internal.auth-token}")
    private String internalAuthToken;

    private final UserSaveUseCase userSaveUseCase;
    private final UserProfileInternalUseCase userProfileInternalUseCase;
    private final UserFindRoleInternalUseCase userFindRoleInternalUseCase;
    private final UserInfoInternalUseCase userInfoInternalUseCase;
    private final UserInternalMapper userInternalMapper;

    @PostMapping
    public ResponseEntity<Void> register(@RequestHeader(name = "Authorization") String token,
                                         @RequestBody UserSaveRequest request) {
        if (!isValidInternalToken(token)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        userSaveUseCase.execute(userInternalMapper.toUserSaveCommand(request));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{userId}/role")
    public ResponseEntity<UserRoleInternalResult> getRole(@RequestHeader(name = "Authorization") String token,
                                                          @PathVariable String userId) {
        if (!isValidInternalToken(token)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(userFindRoleInternalUseCase.execute(userId));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserInfoInternalResult> getUserInfo(@PathVariable String userId) {
        return ResponseEntity.ok(userInfoInternalUseCase.execute(userId));
    }

    @GetMapping("/{userId}/profile}")
    public ResponseEntity<UserProfileInternalResult> getUserProfile(@PathVariable String userId) {
        return ResponseEntity.ok(userProfileInternalUseCase.execute(userId));
    }

    private boolean isValidInternalToken(String token) {
        if (Objects.isNull(token) || !token.startsWith("Bearer ")) {
            return false;
        }

        return token.substring(7).equals(internalAuthToken);
    }

}

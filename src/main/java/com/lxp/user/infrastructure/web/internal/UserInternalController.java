package com.lxp.user.infrastructure.web.internal;

import com.lxp.user.application.port.provided.usecase.UserFindInternalUseCase;
import com.lxp.user.application.port.provided.usecase.UserSaveUseCase;
import com.lxp.user.application.port.provided.dto.UserInfoInternalResult;
import com.lxp.user.infrastructure.web.internal.dto.UserInfoResponse;
import com.lxp.user.infrastructure.web.internal.dto.UserSaveRequest;
import com.lxp.user.infrastructure.web.internal.mapper.UserInternalMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/internal/api-v1/users")
@RequiredArgsConstructor
public class UserInternalController {

    private final UserSaveUseCase userSaveUseCase;
    private final UserFindInternalUseCase userFindInternalUseCase;
    private final UserInternalMapper userInternalMapper;

    @PostMapping
    public ResponseEntity<Void> register(@RequestBody UserSaveRequest request) {
        userSaveUseCase.execute(userInternalMapper.toUserSaveCommand(request));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<UserInfoResponse> getUserInfo(@AuthenticationPrincipal String userId) {
        UserInfoInternalResult execute = userFindInternalUseCase.execute(userId);
        userInternalMapper.toUserInfoResponse(execute);
        return ResponseEntity.ok(userInternalMapper.toUserInfoResponse(execute));
    }

}

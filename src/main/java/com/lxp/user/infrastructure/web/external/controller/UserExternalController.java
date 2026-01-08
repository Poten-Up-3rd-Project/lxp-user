package com.lxp.user.infrastructure.web.external.controller;

import com.lxp.common.infrastructure.exception.ApiResponse;
import com.lxp.user.application.port.provided.command.UserRoleUpdateCommand;
import com.lxp.user.application.port.provided.command.UserSearchCommand;
import com.lxp.user.application.port.provided.command.UserWithdrawnCommand;
import com.lxp.user.application.port.provided.dto.UserInfoResult;
import com.lxp.user.application.port.provided.usecase.SearchUserProfileUseCase;
import com.lxp.user.application.port.provided.usecase.UserRoleUpdateUseCase;
import com.lxp.user.application.port.provided.usecase.UserUpdateUseCase;
import com.lxp.user.application.port.provided.usecase.UserWithdrawnUseCase;
import com.lxp.user.application.port.required.query.AuthRegeneratedTokenResult;
import com.lxp.user.infrastructure.constants.CookieConstants;
import com.lxp.user.infrastructure.web.external.controller.dto.request.UserUpdateRequest;
import com.lxp.user.infrastructure.web.external.controller.dto.response.UserProfileResponse;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api-v1/users")
@RequiredArgsConstructor
public class UserExternalController {

    private final SearchUserProfileUseCase searchUserProfileUseCase;
    private final UserUpdateUseCase userUpdateUseCase;
    private final UserRoleUpdateUseCase userRoleUpdateUseCase;
    private final UserWithdrawnUseCase userWithdrawnUseCase;
    private final UserExternalMapper userExternalMapper;

    @GetMapping
    public ResponseEntity<UserProfileResponse> getUserInfo(@AuthenticationPrincipal String userId) {
        UserInfoResult userInfoResult = searchUserProfileUseCase.execute(new UserSearchCommand(userId));
        return ResponseEntity.ok(userExternalMapper.toUserProfileResponse(userInfoResult));
    }

    @PatchMapping
    public ResponseEntity<UserProfileResponse> updateUserInfo(
        @AuthenticationPrincipal String userId,
        @RequestBody UserUpdateRequest request
    ) {
        UserInfoResult userInfoDto = userUpdateUseCase.execute(userExternalMapper.toUserUpdateCommand(userId, request));
        return ResponseEntity.ok(userExternalMapper.toUserProfileResponse(userInfoDto));
    }

    @PreAuthorize("hasAuthority('ROLE_LEARNER')")
    @PutMapping("/role")
    public ResponseEntity<ApiResponse<Void>> updateUserToInstructor(
        @AuthenticationPrincipal String userId,
        @CookieValue(value = CookieConstants.ACCESS_TOKEN_NAME) String token,
        HttpServletResponse response
    ) {
        AuthRegeneratedTokenResult execute = userRoleUpdateUseCase.execute(new UserRoleUpdateCommand(userId, token));
        ResponseCookie cookie = createCookie(execute.token(), execute.expiresIn());

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
        response.setStatus(HttpServletResponse.SC_OK);
        return ResponseEntity.ok(ApiResponse.success());
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteUserInfo(@AuthenticationPrincipal String userId,
                                               @CookieValue(value = CookieConstants.ACCESS_TOKEN_NAME) String token,
                                               HttpServletResponse response) {
        userWithdrawnUseCase.execute(new UserWithdrawnCommand(userId, token));

        removeCookie(response);
        return ResponseEntity.ok().build();
    }

    private ResponseCookie createCookie(String token, long expiresIn) {
        return ResponseCookie.from(CookieConstants.ACCESS_TOKEN_NAME, token)
            .httpOnly(CookieConstants.HTTP_ONLY)
            .secure(false)
            .path(CookieConstants.DEFAULT_PATH)
            .maxAge(expiresIn)
            .sameSite("Lax")
            .build();
    }

    private void removeCookie(HttpServletResponse response) {
        ResponseCookie cookie = ResponseCookie.from(CookieConstants.ACCESS_TOKEN_NAME, "")
            .httpOnly(CookieConstants.HTTP_ONLY)
            .secure(false)
            .path(CookieConstants.DEFAULT_PATH)
            .maxAge(0)
            .sameSite("Lax")
            .build();
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    }

}

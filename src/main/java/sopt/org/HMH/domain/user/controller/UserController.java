package sopt.org.HMH.domain.user.controller;

import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sopt.org.HMH.domain.user.domain.exception.UserSuccess;
import sopt.org.HMH.domain.user.dto.request.SocialPlatformRequest;
import sopt.org.HMH.domain.user.dto.request.SocialSignUpRequest;
import sopt.org.HMH.domain.user.dto.response.LoginResponse;
import sopt.org.HMH.domain.user.dto.response.UserInfoResponse;
import sopt.org.HMH.domain.user.service.UserService;
import sopt.org.HMH.global.auth.jwt.TokenResponse;
import sopt.org.HMH.global.common.response.ApiResponse;
import sopt.org.HMH.global.common.response.EmptyJsonResponse;
import sopt.org.HMH.global.util.IdConverter;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> orderLogin(
            @RequestHeader("Authorization") final String socialAccessToken,
            @RequestBody final SocialPlatformRequest request
    ) {
        return ResponseEntity
                .status(UserSuccess.LOGIN_SUCCESS.getHttpStatus())
                .body(ApiResponse.success(UserSuccess.LOGIN_SUCCESS, userService.login(socialAccessToken, request)));
    }

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<LoginResponse>> orderSignup(
            @RequestHeader("Authorization") final String socialAccessToken,
            @RequestHeader("OS") final String os,
            @RequestBody final SocialSignUpRequest request
    ) {
        return ResponseEntity
                .status(UserSuccess.SIGNUP_SUCCESS.getHttpStatus())
                .body(ApiResponse.success(UserSuccess.SIGNUP_SUCCESS, userService.signup(socialAccessToken, request, os)));
    }

    @GetMapping("/reissue")
    public ResponseEntity<ApiResponse<TokenResponse>> orderReissue(
            @RequestHeader("Authorization") final String refreshToken
    ) {
        return ResponseEntity
                .status(UserSuccess.REISSUE_SUCCESS.getHttpStatus())
                .body(ApiResponse.success(UserSuccess.REISSUE_SUCCESS, userService.reissueToken(refreshToken)));
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<?>> orderLogout(Principal principal) {
        userService.logout(IdConverter.getUserId(principal));
        return ResponseEntity
                .status(UserSuccess.LOGOUT_SUCCESS.getHttpStatus())
                .body(ApiResponse.success(UserSuccess.LOGOUT_SUCCESS, new EmptyJsonResponse()));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<UserInfoResponse>> orderGetUserInfo(Principal principal) {
        return ResponseEntity
                .status(UserSuccess.GET_USER_INFO_SUCCESS.getHttpStatus())
                .body(ApiResponse.success(UserSuccess.GET_USER_INFO_SUCCESS, userService.getUserInfo(IdConverter.getUserId(principal))));
    }

    @DeleteMapping
    public ResponseEntity<ApiResponse<?>> orderWithdraw(Principal principal) {
        userService.withdraw(IdConverter.getUserId(principal));
        return ResponseEntity
                .status(UserSuccess.WITHDRAW_SUCCESS.getHttpStatus())
                .body(ApiResponse.success(UserSuccess.WITHDRAW_SUCCESS, new EmptyJsonResponse()));
    }
}
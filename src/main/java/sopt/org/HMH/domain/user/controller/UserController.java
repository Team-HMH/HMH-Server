package sopt.org.HMH.domain.user.controller;

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
import sopt.org.HMH.domain.user.dto.response.ReissueResponse;
import sopt.org.HMH.domain.user.dto.response.UserInfoResponse;
import sopt.org.HMH.domain.user.service.UserService;
import sopt.org.HMH.global.auth.UserId;
import sopt.org.HMH.global.common.response.BaseResponse;
import sopt.org.HMH.global.common.response.EmptyJsonResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<BaseResponse<LoginResponse>> orderLogin(
            @RequestHeader("Authorization") final String socialAccessToken,
            @RequestBody final SocialPlatformRequest request
    ) {
        return ResponseEntity
                .status(UserSuccess.LOGIN_SUCCESS.getHttpStatus())
                .body(BaseResponse.success(UserSuccess.LOGIN_SUCCESS, userService.login(socialAccessToken, request)));
    }

    @PostMapping("/signup")
    public ResponseEntity<BaseResponse<LoginResponse>> orderSignup(
            @RequestHeader("Authorization") final String socialAccessToken,
            @RequestHeader("OS") final String os,
            @RequestBody final SocialSignUpRequest request
    ) {
        return ResponseEntity
                .status(UserSuccess.SIGNUP_SUCCESS.getHttpStatus())
                .body(BaseResponse.success(UserSuccess.SIGNUP_SUCCESS, userService.signup(socialAccessToken, request, os)));
    }

    @PostMapping("/reissue")
    public ResponseEntity<BaseResponse<ReissueResponse>> orderReissue(
            @RequestHeader("Authorization") final String refreshToken
    ) {
        return ResponseEntity
                .status(UserSuccess.REISSUE_SUCCESS.getHttpStatus())
                .body(BaseResponse.success(UserSuccess.REISSUE_SUCCESS, userService.reissueToken(refreshToken)));
    }

    @PostMapping("/logout")
    public ResponseEntity<BaseResponse<?>> orderLogout(@UserId final Long userId) {
        userService.logout(userId);
        return ResponseEntity
                .status(UserSuccess.LOGOUT_SUCCESS.getHttpStatus())
                .body(BaseResponse.success(UserSuccess.LOGOUT_SUCCESS, new EmptyJsonResponse()));
    }

    @GetMapping
    public ResponseEntity<BaseResponse<UserInfoResponse>> orderGetUserInfo(@UserId final Long userId) {
        return ResponseEntity
                .status(UserSuccess.GET_USER_INFO_SUCCESS.getHttpStatus())
                .body(BaseResponse.success(UserSuccess.GET_USER_INFO_SUCCESS, userService.getUserInfo(userId)));
    }

    @DeleteMapping
    public ResponseEntity<BaseResponse<?>> orderWithdraw(@UserId final Long userId) {
        userService.withdraw(userId);
        return ResponseEntity
                .status(UserSuccess.WITHDRAW_SUCCESS.getHttpStatus())
                .body(BaseResponse.success(UserSuccess.WITHDRAW_SUCCESS, new EmptyJsonResponse()));
    }
}
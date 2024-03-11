package sopt.org.hmh.domain.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sopt.org.hmh.domain.user.domain.exception.UserSuccess;
import sopt.org.hmh.domain.user.dto.request.SocialPlatformRequest;
import sopt.org.hmh.domain.user.dto.request.SocialSignUpRequest;
import sopt.org.hmh.domain.user.service.UserService;
import sopt.org.hmh.global.auth.UserId;
import sopt.org.hmh.global.auth.social.SocialAccessTokenResponse;
import sopt.org.hmh.global.common.response.BaseResponse;
import sopt.org.hmh.global.common.response.EmptyJsonResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController implements UserApi{

    private final UserService userService;

    @PostMapping("/login")
    @Override
    public ResponseEntity<BaseResponse<?>> orderLogin(
            @RequestHeader("Authorization") final String socialAccessToken,
            @RequestBody final SocialPlatformRequest request
    ) {
        return ResponseEntity
                .status(UserSuccess.LOGIN_SUCCESS.getHttpStatus())
                .body(BaseResponse.success(UserSuccess.LOGIN_SUCCESS, userService.login(socialAccessToken, request)));
    }

    @PostMapping("/signup")
    @Override
    public ResponseEntity<BaseResponse<?>> orderSignup(
            @RequestHeader("Authorization") final String socialAccessToken,
            @RequestHeader("OS") final String os,
            @RequestBody final SocialSignUpRequest request
    ) {
        return ResponseEntity
                .status(UserSuccess.SIGNUP_SUCCESS.getHttpStatus())
                .body(BaseResponse.success(UserSuccess.SIGNUP_SUCCESS, userService.signup(socialAccessToken, request, os)));
    }

    @PostMapping("/reissue")
    @Override
    public ResponseEntity<BaseResponse<?>> orderReissue(
            @RequestHeader("Authorization") final String refreshToken
    ) {
        return ResponseEntity
                .status(UserSuccess.REISSUE_SUCCESS.getHttpStatus())
                .body(BaseResponse.success(UserSuccess.REISSUE_SUCCESS, userService.reissueToken(refreshToken)));
    }

    @PostMapping("/logout")
    @Override
    public ResponseEntity<BaseResponse<?>> orderLogout(@UserId final Long userId) {
        userService.logout(userId);
        return ResponseEntity
                .status(UserSuccess.LOGOUT_SUCCESS.getHttpStatus())
                .body(BaseResponse.success(UserSuccess.LOGOUT_SUCCESS, new EmptyJsonResponse()));
    }

    @GetMapping
    @Override
    public ResponseEntity<BaseResponse<?>> orderGetUserInfo(@UserId final Long userId) {
        return ResponseEntity
                .status(UserSuccess.GET_USER_INFO_SUCCESS.getHttpStatus())
                .body(BaseResponse.success(UserSuccess.GET_USER_INFO_SUCCESS, userService.getUserInfo(userId)));
    }

    @GetMapping("/point")
    @Override
    public ResponseEntity<BaseResponse<?>> orderGetUserPoint(@UserId final Long userId) {
        return ResponseEntity
                .status(UserSuccess.GET_USER_POINT_SUCCESS.getHttpStatus())
                .body(BaseResponse.success(UserSuccess.GET_USER_POINT_SUCCESS, userService.getUserPoint(userId)));
    }

    @DeleteMapping
    @Override
    public ResponseEntity<BaseResponse<?>> orderWithdraw(@UserId final Long userId) {
        userService.withdraw(userId);
        return ResponseEntity
                .status(UserSuccess.WITHDRAW_SUCCESS.getHttpStatus())
                .body(BaseResponse.success(UserSuccess.WITHDRAW_SUCCESS, new EmptyJsonResponse()));
    }

    @GetMapping("/social/token/kakao")
    public ResponseEntity<BaseResponse<SocialAccessTokenResponse>> orderGetKakaoAccessToken(
            @RequestParam("code") final String code
            ) {
        return ResponseEntity
                .status(UserSuccess.GET_SOCIAL_ACCESS_TOKEN_SUCCESS.getHttpStatus())
                .body(BaseResponse.success(UserSuccess.GET_SOCIAL_ACCESS_TOKEN_SUCCESS, userService.getSocialAccessTokenByAuthorizationCode(code)));
    }
}
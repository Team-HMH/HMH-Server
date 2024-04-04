package sopt.org.hmh.domain.auth.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sopt.org.hmh.domain.auth.exception.AuthSuccess;
import sopt.org.hmh.domain.auth.dto.request.SocialPlatformRequest;
import sopt.org.hmh.domain.auth.dto.request.SocialSignUpRequest;
import sopt.org.hmh.domain.auth.service.AuthService;
import sopt.org.hmh.global.auth.social.SocialAccessTokenResponse;
import sopt.org.hmh.global.common.response.BaseResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class AuthController implements AuthApi {

    private final AuthService authService;

    @PostMapping("/login")
    @Override
    public ResponseEntity<BaseResponse<?>> orderLogin(
            @RequestHeader("Authorization") final String socialAccessToken,
            @RequestBody final SocialPlatformRequest request
    ) {
        return ResponseEntity
                .status(AuthSuccess.LOGIN_SUCCESS.getHttpStatus())
                .body(BaseResponse.success(AuthSuccess.LOGIN_SUCCESS, authService.login(socialAccessToken, request)));
    }

    @PostMapping("/signup")
    @Override
    public ResponseEntity<BaseResponse<?>> orderSignup(
            @RequestHeader("Authorization") final String socialAccessToken,
            @RequestHeader("OS") final String os,
            @RequestBody final SocialSignUpRequest request
    ) {
        return ResponseEntity
                .status(AuthSuccess.SIGNUP_SUCCESS.getHttpStatus())
                .body(BaseResponse.success(AuthSuccess.SIGNUP_SUCCESS, authService.signup(socialAccessToken, request, os)));
    }

    @PostMapping("/reissue")
    @Override
    public ResponseEntity<BaseResponse<?>> orderReissue(
            @RequestHeader("Authorization") final String refreshToken
    ) {
        return ResponseEntity
                .status(AuthSuccess.REISSUE_SUCCESS.getHttpStatus())
                .body(BaseResponse.success(AuthSuccess.REISSUE_SUCCESS, authService.reissueToken(refreshToken)));
    }

    @GetMapping("/social/token/kakao")
    public ResponseEntity<BaseResponse<SocialAccessTokenResponse>> orderGetKakaoAccessToken(
            @RequestParam("code") final String code
            ) {
        return ResponseEntity
                .status(AuthSuccess.GET_SOCIAL_ACCESS_TOKEN_SUCCESS.getHttpStatus())
                .body(BaseResponse.success(AuthSuccess.GET_SOCIAL_ACCESS_TOKEN_SUCCESS, authService.getSocialAccessTokenByAuthorizationCode(code)));
    }
}
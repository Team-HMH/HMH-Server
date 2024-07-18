package sopt.org.hmh.domain.auth.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sopt.org.hmh.domain.auth.dto.response.LoginResponse;
import sopt.org.hmh.domain.auth.dto.response.ReissueResponse;
import sopt.org.hmh.domain.auth.exception.AuthSuccess;
import sopt.org.hmh.domain.auth.dto.request.SocialPlatformRequest;
import sopt.org.hmh.domain.auth.dto.request.SocialSignUpRequest;
import sopt.org.hmh.domain.auth.service.AuthFacade;
import sopt.org.hmh.global.auth.jwt.JwtConstants;
import sopt.org.hmh.global.auth.social.SocialAccessTokenResponse;
import sopt.org.hmh.global.common.constant.CustomHeaderType;
import sopt.org.hmh.global.common.response.BaseResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AuthController implements AuthApi {

    private final AuthFacade authFacade;

    @PostMapping("/v1/user/login")
    @Override
    public ResponseEntity<BaseResponse<LoginResponse>> orderLogin(
            @RequestHeader(JwtConstants.AUTHORIZATION) final String socialAccessToken,
            @RequestBody final SocialPlatformRequest request
    ) {
        return ResponseEntity
                .status(AuthSuccess.LOGIN_SUCCESS.getHttpStatus())
                .body(BaseResponse.success(
                        AuthSuccess.LOGIN_SUCCESS,
                        authFacade.login(socialAccessToken, request.socialPlatform())
                ));
    }

    @Override
    @Deprecated
    @PostMapping("/v1/user/signup")
    public ResponseEntity<BaseResponse<LoginResponse>> orderSignupDeprecated(
            @RequestHeader(JwtConstants.AUTHORIZATION) final String socialAccessToken,
            @RequestHeader(CustomHeaderType.OS) final String os,
            @RequestBody @Valid final SocialSignUpRequest request
    ) {
        return ResponseEntity
                .status(AuthSuccess.SIGNUP_SUCCESS.getHttpStatus())
                .body(BaseResponse.success(
                        AuthSuccess.SIGNUP_SUCCESS,
                        authFacade.signup(request, socialAccessToken, os, "Asia/Seoul")
                ));
    }

    @Override
    @PostMapping("/v2/user/signup")
    public ResponseEntity<BaseResponse<LoginResponse>> orderSignup(
            @RequestHeader(JwtConstants.AUTHORIZATION) final String socialAccessToken,
            @RequestHeader(CustomHeaderType.OS) final String os,
            @RequestHeader(CustomHeaderType.TIME_ZONE) final String timeZone,
            @RequestBody @Valid final SocialSignUpRequest request
    ) {
        return ResponseEntity
                .status(AuthSuccess.SIGNUP_SUCCESS.getHttpStatus())
                .body(BaseResponse.success(
                        AuthSuccess.SIGNUP_SUCCESS,
                        authFacade.signup(request, socialAccessToken, os, timeZone)
                ));
    }

    @Override
    @PostMapping("/v1/user/reissue")
    public ResponseEntity<BaseResponse<ReissueResponse>> orderReissue(
            @RequestHeader(JwtConstants.AUTHORIZATION) final String refreshToken
    ) {
        return ResponseEntity
                .status(AuthSuccess.REISSUE_SUCCESS.getHttpStatus())
                .body(BaseResponse.success(
                        AuthSuccess.REISSUE_SUCCESS,
                        authFacade.reissueToken(refreshToken)
                ));
    }

    @GetMapping("/v1/user/social/token/kakao")
    public ResponseEntity<BaseResponse<SocialAccessTokenResponse>> orderGetKakaoAccessToken(
            @RequestParam("code") final String code
            ) {
        return ResponseEntity
                .status(AuthSuccess.GET_SOCIAL_ACCESS_TOKEN_SUCCESS.getHttpStatus())
                .body(BaseResponse.success(
                        AuthSuccess.GET_SOCIAL_ACCESS_TOKEN_SUCCESS,
                        authFacade.getSocialAccessTokenByAuthorizationCode(code)
                ));
    }
}
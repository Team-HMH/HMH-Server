package sopt.org.HMH.domain.user.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import sopt.org.HMH.domain.user.domain.exception.UserSuccess;
import sopt.org.HMH.domain.user.dto.request.SocialLoginRequestDto;
import sopt.org.HMH.domain.user.dto.response.LoginResponseDto;
import sopt.org.HMH.domain.user.service.UserService;
import sopt.org.HMH.global.auth.jwt.JwtProvider;
import sopt.org.HMH.global.auth.jwt.TokenDto;
import sopt.org.HMH.global.auth.social.kakao.fegin.KakaoLoginService;
import sopt.org.HMH.global.common.response.ApiResponse;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final KakaoLoginService kakaoLoginService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponseDto>> login(
            @RequestHeader("Authorization") String socialAccessToken,
            @RequestBody SocialLoginRequestDto request
    ){
        return ResponseEntity
                .status(UserSuccess.LOGIN_SUCCESS.getHttpStatus())
                .body(ApiResponse.success(UserSuccess.LOGIN_SUCCESS, userService.login(socialAccessToken, request)));
    }

    @GetMapping("/reissue")
    public ResponseEntity<ApiResponse<TokenDto>> reissue(
            @RequestHeader("Authorization") String refreshToken
    ){
        return ResponseEntity
                .status(UserSuccess.REISSUE_SUCCESS.getHttpStatus())
                .body(ApiResponse.success(UserSuccess.REISSUE_SUCCESS, userService.reissueToken(refreshToken)));
    }

    @PostMapping("/log-out")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<?> logout(Principal principal) {

        userService.logout(JwtProvider.getUserFromPrincipal(principal));
        return ApiResponse.success(UserSuccess.LOGOUT_SUCCESS);
    }

}
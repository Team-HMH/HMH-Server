package sopt.org.HMH.domain.user.controller;

import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
import sopt.org.HMH.domain.user.service.UserService;
import sopt.org.HMH.global.auth.jwt.JwtProvider;
import sopt.org.HMH.global.auth.jwt.TokenDto;
import sopt.org.HMH.global.common.response.ApiResponse;
import sopt.org.HMH.global.common.response.EmptyJsonResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(
            @RequestHeader("Authorization") String socialAccessToken,
            @RequestBody SocialPlatformRequest request
    ) {
        return ResponseEntity
                .status(UserSuccess.LOGIN_SUCCESS.getHttpStatus())
                .body(ApiResponse.success(UserSuccess.LOGIN_SUCCESS, userService.login(socialAccessToken, request)));
    }

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<LoginResponse>> signup(
            @RequestHeader("Authorization") String socialAccessToken,
            @RequestBody SocialSignUpRequest request
    ) {
        return ResponseEntity
                .status(UserSuccess.LOGIN_SUCCESS.getHttpStatus())
                .body(ApiResponse.success(UserSuccess.LOGIN_SUCCESS, userService.signup(socialAccessToken, request)));
    }

    @GetMapping("/reissue")
    public ResponseEntity<ApiResponse<TokenDto>> reissue(
            @RequestHeader("Authorization") String refreshToken
    ) {
        return ResponseEntity
                .status(UserSuccess.REISSUE_SUCCESS.getHttpStatus())
                .body(ApiResponse.success(UserSuccess.REISSUE_SUCCESS, userService.reissueToken(refreshToken)));
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<?>> logout(Principal principal) {
        userService.logout(JwtProvider.getUserFromPrincipal(principal));
        return ResponseEntity
                .status(UserSuccess.LOGOUT_SUCCESS.getHttpStatus())
                .body(ApiResponse.success(UserSuccess.LOGOUT_SUCCESS,new EmptyJsonResponse()));
    }
}
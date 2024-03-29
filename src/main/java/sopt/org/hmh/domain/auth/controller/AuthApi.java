package sopt.org.hmh.domain.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import sopt.org.hmh.domain.auth.dto.request.SocialPlatformRequest;
import sopt.org.hmh.domain.auth.dto.request.SocialSignUpRequest;
import sopt.org.hmh.global.auth.UserId;
import sopt.org.hmh.global.auth.jwt.JwtConstants;
import sopt.org.hmh.global.common.response.BaseResponse;

@Tag(name = "회원 관련 API")
@SecurityRequirement(name = JwtConstants.AUTHORIZATION)
public interface AuthApi {

    @Operation(summary = "소셜 로그인")
    ResponseEntity<BaseResponse<?>> orderLogin(
            @Parameter(hidden = true) @RequestHeader(JwtConstants.AUTHORIZATION) final String socialAccessToken,
            @RequestBody final SocialPlatformRequest request
    );

    @Operation(summary = "회원 가입")
    ResponseEntity<BaseResponse<?>> orderSignup(
            @Parameter(hidden = true) @RequestHeader(JwtConstants.AUTHORIZATION) final String socialAccessToken,
            @RequestHeader("OS") final String os,
            @RequestBody final SocialSignUpRequest request
    );

    @Operation(summary = "토큰 재발급")
    ResponseEntity<BaseResponse<?>> orderReissue(
            @Parameter(hidden = true) @RequestHeader(JwtConstants.AUTHORIZATION) final String refreshToken
    );
}

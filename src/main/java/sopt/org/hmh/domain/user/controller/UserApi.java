package sopt.org.hmh.domain.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.ResponseEntity;
import sopt.org.hmh.global.auth.UserId;
import sopt.org.hmh.global.common.response.BaseResponse;

public interface UserApi {

    @Operation(summary = "로그아웃")
    ResponseEntity<BaseResponse<?>> orderLogout();

    @Operation(summary = "유저 정보 불러오기")
    ResponseEntity<BaseResponse<?>> orderGetUserInfo(@UserId @Parameter(hidden = true) final Long userId);

    @Operation(summary = "유저 포인트 정보 불러오기")
    public ResponseEntity<BaseResponse<?>> orderGetUserPoint(@UserId final Long userId);

    @Operation(
            summary = "회원 탈퇴")
    ResponseEntity<BaseResponse<?>> orderWithdraw(@UserId @Parameter(hidden = true) final Long userId);

}

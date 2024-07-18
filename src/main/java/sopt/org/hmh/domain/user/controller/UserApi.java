package sopt.org.hmh.domain.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import java.time.LocalDate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import sopt.org.hmh.domain.user.dto.request.UserRequest.LockDateRequest;
import sopt.org.hmh.domain.user.dto.response.UserResponse.IsLockTodayResponse;
import sopt.org.hmh.domain.user.dto.response.UserResponse.UserInfoResponse;
import sopt.org.hmh.global.common.constant.CustomHeaderType;
import sopt.org.hmh.global.common.response.BaseResponse;
import sopt.org.hmh.global.common.response.EmptyJsonResponse;

public interface UserApi {

    @Operation(summary = "로그아웃")
    ResponseEntity<BaseResponse<EmptyJsonResponse>> orderLogout();

    @Operation(summary = "유저 정보 불러오기")
    ResponseEntity<BaseResponse<UserInfoResponse>> orderGetUserInfo(@Parameter(hidden = true) final Long userId);

    @Operation(summary = "유저 포인트 정보 불러오기")
    ResponseEntity<BaseResponse<Integer>> orderGetUserPoint(@Parameter(hidden = true) final Long userId);

    @Operation(summary = "회원 탈퇴")
    ResponseEntity<BaseResponse<EmptyJsonResponse>> orderWithdraw(@Parameter(hidden = true) final Long userId);

    @Operation(summary = "당일 잠금 여부 전송")
    ResponseEntity<BaseResponse<EmptyJsonResponse>> orderChangeRecentLockDate(
            @Parameter(hidden = true) final Long userId,
            @RequestHeader(CustomHeaderType.TIME_ZONE) final String timeZone);

    @Operation(summary = "당일 잠금 여부 전송")
    ResponseEntity<BaseResponse<EmptyJsonResponse>> orderChangeRecentLockDateDeprecated(
            @Parameter(hidden = true) final Long userId,
            final LockDateRequest request);

    @Operation(summary = "당일 잠금 여부 확인")
    ResponseEntity<BaseResponse<IsLockTodayResponse>> orderGetRecentLockDate(
            @Parameter(hidden = true) final Long userId,
            @RequestHeader(CustomHeaderType.TIME_ZONE) final String timeZone);

    @Operation(summary = "당일 잠금 여부 확인")
    ResponseEntity<BaseResponse<IsLockTodayResponse>> orderGetRecentLockDateDeprecated(
            @Parameter(hidden = true) final Long userId,
            final LocalDate lockCheckDate);
}

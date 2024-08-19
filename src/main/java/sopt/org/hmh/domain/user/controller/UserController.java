package sopt.org.hmh.domain.user.controller;

import jakarta.validation.Valid;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sopt.org.hmh.domain.user.domain.exception.UserSuccess;
import sopt.org.hmh.domain.user.dto.request.UserRequest.LockDateRequest;
import sopt.org.hmh.domain.user.dto.response.UserResponse.IsLockTodayResponse;
import sopt.org.hmh.domain.user.dto.response.UserResponse.UserInfoResponse;
import sopt.org.hmh.domain.user.service.UserService;
import sopt.org.hmh.global.auth.UserId;
import sopt.org.hmh.global.common.constant.CustomHeaderType;
import sopt.org.hmh.global.common.response.BaseResponse;
import sopt.org.hmh.global.common.response.EmptyJsonResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController implements UserApi {

    private final UserService userService;

    @PostMapping("/v1/user/logout")
    @Override
    public ResponseEntity<BaseResponse<EmptyJsonResponse>> orderLogout() {
        return ResponseEntity
                .status(UserSuccess.LOGOUT_SUCCESS.getHttpStatus())
                .body(BaseResponse.success(UserSuccess.LOGOUT_SUCCESS, new EmptyJsonResponse()));
    }

    @GetMapping("/v1/user")
    @Override
    public ResponseEntity<BaseResponse<UserInfoResponse>> orderGetUserInfo(@UserId final Long userId) {
        return ResponseEntity
                .status(UserSuccess.GET_USER_INFO_SUCCESS.getHttpStatus())
                .body(BaseResponse.success(UserSuccess.GET_USER_INFO_SUCCESS, userService.getUserInfo(userId)));
    }

    @GetMapping("/v1/user/point")
    @Override
    public ResponseEntity<BaseResponse<Integer>> orderGetUserPoint(@UserId final Long userId) {
        return ResponseEntity
                .status(UserSuccess.GET_USER_POINT_SUCCESS.getHttpStatus())
                .body(BaseResponse.success(UserSuccess.GET_USER_POINT_SUCCESS,
                        userService.getUserInfo(userId).point()));
    }

    @DeleteMapping("/v1/user")
    public ResponseEntity<BaseResponse<EmptyJsonResponse>> orderWithdraw(@UserId final Long userId) {
        userService.withdraw(userId);
        return ResponseEntity
                .status(UserSuccess.WITHDRAW_SUCCESS.getHttpStatus())
                .body(BaseResponse.success(UserSuccess.WITHDRAW_SUCCESS, new EmptyJsonResponse()));
    }

    @Override
    @Deprecated
    @PostMapping("/v1/user/daily/lock")
    public ResponseEntity<BaseResponse<EmptyJsonResponse>> orderChangeRecentLockDateDeprecated(
            @UserId final Long userId,
            @Valid @RequestBody final LockDateRequest request) {
        userService.changeRecentLockDateDeprecated(userId, request.lockDate());
        return ResponseEntity
                .status(UserSuccess.CHANGE_RECENT_LOCK_DATE_SUCCESS.getHttpStatus())
                .body(BaseResponse.success(UserSuccess.CHANGE_RECENT_LOCK_DATE_SUCCESS, new EmptyJsonResponse()));
    }

    @Override
    @PostMapping("/v2/user/daily/lock")
    public ResponseEntity<BaseResponse<EmptyJsonResponse>> orderChangeRecentLockDate(
            @UserId final Long userId,
            @RequestHeader(CustomHeaderType.TIME_ZONE) final String timeZone) {
        userService.changeRecentLockDateToToday(userId, timeZone);
        return ResponseEntity
                .status(UserSuccess.CHANGE_RECENT_LOCK_DATE_SUCCESS.getHttpStatus())
                .body(BaseResponse.success(UserSuccess.CHANGE_RECENT_LOCK_DATE_SUCCESS, new EmptyJsonResponse()));
    }

    @Override
    @Deprecated
    @GetMapping("/v1/user/daily/lock")
    public ResponseEntity<BaseResponse<IsLockTodayResponse>> orderGetRecentLockDateDeprecated(
            @UserId final Long userId,
            @RequestParam(name = "lockCheckDate") @DateTimeFormat(pattern = "yyyy-MM-dd") final LocalDate lockCheckDate) {
        return ResponseEntity
                .status(UserSuccess.GET_RECENT_LOCK_DATE_SUCCESS.getHttpStatus())
                .body(BaseResponse.success(UserSuccess.GET_RECENT_LOCK_DATE_SUCCESS,
                        userService.checkIsTodayLockDeprecated(userId, lockCheckDate)));
    }

    @Override
    @GetMapping("/v2/user/daily/lock")
    public ResponseEntity<BaseResponse<IsLockTodayResponse>> orderGetRecentLockDate(
            @UserId final Long userId,
            @RequestHeader(CustomHeaderType.TIME_ZONE) final String timeZone) {
        return ResponseEntity
                .status(UserSuccess.GET_RECENT_LOCK_DATE_SUCCESS.getHttpStatus())
                .body(BaseResponse.success(UserSuccess.GET_RECENT_LOCK_DATE_SUCCESS,
                        userService.checkIsTodayLock(userId, timeZone)));
    }
}

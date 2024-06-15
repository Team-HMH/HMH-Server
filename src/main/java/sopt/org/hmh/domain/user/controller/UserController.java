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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sopt.org.hmh.domain.user.domain.exception.UserSuccess;
import sopt.org.hmh.domain.user.dto.request.UserRequest.LockDateRequest;
import sopt.org.hmh.domain.user.dto.response.UserResponse.IsLockTodayResponse;
import sopt.org.hmh.domain.user.dto.response.UserResponse.UserInfoResponse;
import sopt.org.hmh.domain.user.service.UserService;
import sopt.org.hmh.global.auth.UserId;
import sopt.org.hmh.global.common.response.BaseResponse;
import sopt.org.hmh.global.common.response.EmptyJsonResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController implements UserApi {

    private final UserService userService;

    @PostMapping("/logout")
    @Override
    public ResponseEntity<BaseResponse<EmptyJsonResponse>> orderLogout() {
        return ResponseEntity
                .status(UserSuccess.LOGOUT_SUCCESS.getHttpStatus())
                .body(BaseResponse.success(UserSuccess.LOGOUT_SUCCESS, new EmptyJsonResponse()));
    }

    @GetMapping
    @Override
    public ResponseEntity<BaseResponse<UserInfoResponse>> orderGetUserInfo(@UserId final Long userId) {
        return ResponseEntity
                .status(UserSuccess.GET_USER_INFO_SUCCESS.getHttpStatus())
                .body(BaseResponse.success(UserSuccess.GET_USER_INFO_SUCCESS, userService.getUserInfo(userId)));
    }

    @GetMapping("/point")
    @Override
    public ResponseEntity<BaseResponse<Integer>> orderGetUserPoint(@UserId final Long userId) {
        return ResponseEntity
                .status(UserSuccess.GET_USER_POINT_SUCCESS.getHttpStatus())
                .body(BaseResponse.success(UserSuccess.GET_USER_POINT_SUCCESS,
                        userService.getUserInfo(userId).point()));
    }

    @DeleteMapping
    public ResponseEntity<BaseResponse<EmptyJsonResponse>> orderWithdraw(@UserId final Long userId) {
        userService.withdraw(userId);
        return ResponseEntity
                .status(UserSuccess.WITHDRAW_SUCCESS.getHttpStatus())
                .body(BaseResponse.success(UserSuccess.WITHDRAW_SUCCESS, new EmptyJsonResponse()));
    }

    @PostMapping("/daily/lock")
    @Override
    public ResponseEntity<BaseResponse<EmptyJsonResponse>> orderChangeRecentLockDate(
            @UserId final Long userId, @Valid @RequestBody final LockDateRequest request) {
        userService.changeRecentLockDate(userId, request.lockDate());
        return ResponseEntity
                .status(UserSuccess.CHANGE_RECENT_LOCK_DATE_SUCCESS.getHttpStatus())
                .body(BaseResponse.success(UserSuccess.CHANGE_RECENT_LOCK_DATE_SUCCESS, new EmptyJsonResponse()));
    }

    @GetMapping("/daily/lock")
    @Override
    public ResponseEntity<BaseResponse<IsLockTodayResponse>> orderGetRecentLockDate(
            @UserId final Long userId,
            @RequestParam(name = "lockCheckDate") @DateTimeFormat(pattern = "yyyy-MM-dd") final LocalDate lockCheckDate) {
        return ResponseEntity
                .status(UserSuccess.GET_RECENT_LOCK_DATE_SUCCESS.getHttpStatus())
                .body(BaseResponse.success(UserSuccess.GET_RECENT_LOCK_DATE_SUCCESS,
                        userService.checkIsTodayLock(userId, lockCheckDate)));
    }

}

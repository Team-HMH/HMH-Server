package sopt.org.hmh.domain.users.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sopt.org.hmh.domain.auth.exception.AuthSuccess;
import sopt.org.hmh.domain.users.service.UserService;
import sopt.org.hmh.global.auth.UserId;
import sopt.org.hmh.global.common.response.BaseResponse;
import sopt.org.hmh.global.common.response.EmptyJsonResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController implements UserApi{

    private final UserService userService;

    @PostMapping("/logout")
    @Override
    public ResponseEntity<BaseResponse<?>> orderLogout(@UserId final Long userId) {
        userService.logout(userId);
        return ResponseEntity
                .status(AuthSuccess.LOGOUT_SUCCESS.getHttpStatus())
                .body(BaseResponse.success(AuthSuccess.LOGOUT_SUCCESS, new EmptyJsonResponse()));
    }

    @GetMapping
    @Override
    public ResponseEntity<BaseResponse<?>> orderGetUserInfo(@UserId final Long userId) {
        return ResponseEntity
                .status(AuthSuccess.GET_USER_INFO_SUCCESS.getHttpStatus())
                .body(BaseResponse.success(AuthSuccess.GET_USER_INFO_SUCCESS, userService.getUserInfo(userId)));
    }

    @GetMapping("/point")
    @Override
    public ResponseEntity<BaseResponse<?>> orderGetUserPoint(@UserId final Long userId) {
        return ResponseEntity
                .status(AuthSuccess.GET_USER_POINT_SUCCESS.getHttpStatus())
                .body(BaseResponse.success(AuthSuccess.GET_USER_POINT_SUCCESS, userService.getUserInfo(userId).point()));
    }

    @DeleteMapping
    public ResponseEntity<BaseResponse<?>> orderWithdraw(@UserId final Long userId) {
        userService.withdraw(userId);
        return ResponseEntity
                .status(AuthSuccess.WITHDRAW_SUCCESS.getHttpStatus())
                .body(BaseResponse.success(AuthSuccess.WITHDRAW_SUCCESS, new EmptyJsonResponse()));
    }

}

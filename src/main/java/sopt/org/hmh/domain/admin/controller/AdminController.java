package sopt.org.hmh.domain.admin.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sopt.org.hmh.domain.admin.dto.request.AdminLoginRequest;
import sopt.org.hmh.domain.admin.dto.request.AdminUserIdRequest;
import sopt.org.hmh.domain.admin.dto.response.AdminTokenResponse;
import sopt.org.hmh.domain.admin.exception.AdminSuccess;
import sopt.org.hmh.domain.admin.service.AdminFacade;
import sopt.org.hmh.global.common.response.BaseResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin")
public class AdminController implements AdminApi {

    private final AdminFacade adminFacade;

    @Override
    @PostMapping("/login")
    public ResponseEntity<BaseResponse<AdminTokenResponse>> orderAdminLogin(
            @RequestBody final AdminLoginRequest request) {
        return ResponseEntity
                .status(AdminSuccess.ADMIN_LOGIN_SUCCESS.getHttpStatus())
                .body(BaseResponse.success(AdminSuccess.ADMIN_LOGIN_SUCCESS,
                        adminFacade.adminLogin(request.authCode())));
    }

    @Override
    @DeleteMapping("/user")
    public ResponseEntity<Void> orderAdminWithdrawImmediately(
            @RequestBody @Valid final AdminUserIdRequest request) {
        adminFacade.withdrawImmediately(request.userId());
        return ResponseEntity
                .noContent()
                .build();
    }
}

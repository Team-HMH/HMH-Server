package sopt.org.hmh.domain.admin.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import sopt.org.hmh.domain.admin.dto.request.AdminLoginRequest;
import sopt.org.hmh.domain.admin.dto.request.AdminUserIdRequest;
import sopt.org.hmh.domain.admin.dto.request.AdminUserInfoRequest;
import sopt.org.hmh.domain.admin.dto.response.AdminTokenResponse;
import sopt.org.hmh.global.common.response.BaseResponse;

public interface AdminApi {
    @Operation(summary = "관리자 로그인")
    ResponseEntity<BaseResponse<AdminTokenResponse>> orderAdminLogin(AdminLoginRequest request);

    @Operation(summary = "관리자 권한으로 유저 즉시 삭제")
    ResponseEntity<Void> orderAdminWithdrawImmediately(AdminUserIdRequest request);

    @Operation(summary = "관리자 권한으로 유저 정보 변경")
    ResponseEntity<Void> orderAdminChangeUserInfo(AdminUserInfoRequest request);
}

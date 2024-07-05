package sopt.org.hmh.domain.admin.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import sopt.org.hmh.domain.admin.dto.request.AdminLoginRequest;
import sopt.org.hmh.domain.admin.dto.response.AdminTokenResponse;
import sopt.org.hmh.global.common.response.BaseResponse;

public interface AdminApi {
    @Operation(summary = "소셜 로그인")
    ResponseEntity<BaseResponse<AdminTokenResponse>> orderAdminLogin(AdminLoginRequest request);
}

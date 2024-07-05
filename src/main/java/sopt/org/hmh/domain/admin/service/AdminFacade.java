package sopt.org.hmh.domain.admin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sopt.org.hmh.domain.admin.dto.response.AdminTokenResponse;
import sopt.org.hmh.domain.admin.exception.AdminError;
import sopt.org.hmh.domain.admin.exception.AdminException;
import sopt.org.hmh.domain.user.service.UserService;
import sopt.org.hmh.global.auth.jwt.TokenService;

@Service
@RequiredArgsConstructor
public class AdminFacade {

    @Value("${jwt.admin-auth-code}")
    private String adminAuthCode;

    private final UserService userService;
    private final TokenService tokenService;

    public AdminTokenResponse adminLogin(String authCode) {
        validateAdminAuthCode(authCode);
        return new AdminTokenResponse(tokenService.issueAdminToken());
    }

    private void validateAdminAuthCode(String authCode) {
        if (!adminAuthCode.equals(authCode)) {
            throw new AdminException(AdminError.INVALID_ADMIN_AUTH_CODE);
        }
    }

    @Transactional
    public void withdrawImmediately(Long userId) {
        userService.checkIsExistUserId(userId);
        userService.withdrawImmediately(userId);
    }
}

package sopt.org.hmh.global.auth.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import sopt.org.hmh.global.auth.jwt.JwtConstants;
import sopt.org.hmh.global.auth.jwt.TokenService;

@Component
@RequiredArgsConstructor
public class ValidateAdminInterceptor implements HandlerInterceptor {

    private final TokenService tokenService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String accessToken = request.getHeader(JwtConstants.AUTHORIZATION);
        tokenService.validateAdminToken(accessToken);
        return true;
    }
}

package sopt.org.HMH.global.auth.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import sopt.org.HMH.global.auth.jwt.JwtProvider;
import sopt.org.HMH.global.auth.jwt.JwtValidationType;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter { // 모든 요청에 있어 한 번만 실행되는 필터

    private final JwtProvider jwtProvider;

    /**
     * JWT 토큰을 추출하고 검증하여 Spring Security의 SecurityContextHolder에 사용자 정보 저장
     */
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        try {

            final String token = getJwtFromRequest(request); // JWT 토큰 추출

            // 추출한 토큰이 존재하고, 토큰이 유효한 경우
            if (StringUtils.hasText(token) && jwtProvider.validateAccessToken(token) == JwtValidationType.VALID_JWT) {
                // JWT 토큰에서 사용자 정보 추출
                Long userId = jwtProvider.getUserFromJwt(token);
                // 사용자 정보로 Spring Security의 사용자 인증 객체 생성
                UserAuthentication authentication = new UserAuthentication(userId, null, null);
                // 사용자 인증 객체에 요청의 세부 정보 추가
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                // Spring Security의 SecurityContextHolder에 사용자 인증 객체 설정
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception exception) {
            log.error("Error Occured: ", exception);
        }

        // 다음 필터로 제어를 넘김
        filterChain.doFilter(request, response);
    }

    /**
     * HTTP 요청에서 JWT 토큰을 추출하는 메서드
     */
    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");

        //Authorization 헤더에서 Bearer 토큰을 가져옴
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring("Bearer ".length());
        }
        return null;
    }
}

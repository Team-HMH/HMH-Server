package sopt.org.hmh.global.auth.security;

import static sopt.org.hmh.global.auth.security.UserAuthentication.createUserAuthentication;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import sopt.org.hmh.global.auth.jwt.JwtConstants;
import sopt.org.hmh.global.auth.jwt.JwtProvider;
import sopt.org.hmh.global.auth.jwt.JwtValidator;
import sopt.org.hmh.global.auth.jwt.exception.JwtError;
import sopt.org.hmh.global.auth.jwt.exception.JwtException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtValidator jwtValidator;
    private final JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String accessToken = getAccessToken(request);
        jwtValidator.validateAccessToken(accessToken);
        doAuthentication(request, jwtProvider.getSubject(accessToken));
        filterChain.doFilter(request, response);
    }

    private String getAccessToken(HttpServletRequest request) {
        String accessToken = request.getHeader(JwtConstants.AUTHORIZATION);
        if (StringUtils.hasText(accessToken) && accessToken.startsWith(JwtConstants.BEARER)) {
            return accessToken.substring(JwtConstants.BEARER.length());
        }
        throw new JwtException(JwtError.INVALID_ACCESS_TOKEN);
    }

    private void doAuthentication(HttpServletRequest request, Long userId) {
        UserAuthentication authentication = createUserAuthentication(userId);
        createAndSetWebAuthenticationDetails(request, authentication);
        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authentication);
    }

    private void createAndSetWebAuthenticationDetails(HttpServletRequest request, UserAuthentication authentication) {
        WebAuthenticationDetailsSource webAuthenticationDetailsSource = new WebAuthenticationDetailsSource();
        WebAuthenticationDetails webAuthenticationDetails = webAuthenticationDetailsSource.buildDetails(request);
        authentication.setDetails(webAuthenticationDetails);
    }
}
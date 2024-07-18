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
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import sopt.org.hmh.global.auth.jwt.JwtConstants;
import sopt.org.hmh.global.auth.jwt.service.JwtProvider;
import sopt.org.hmh.global.auth.jwt.service.JwtValidator;

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
        return request.getHeader(JwtConstants.AUTHORIZATION);
    }

    private void doAuthentication(HttpServletRequest request, String subjectId) {
        UserAuthentication authentication = createUserAuthentication(subjectId);
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
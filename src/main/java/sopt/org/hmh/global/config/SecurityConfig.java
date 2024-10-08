package sopt.org.hmh.global.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import sopt.org.hmh.domain.slack.builder.ErrorSlackMessageBuilder;
import sopt.org.hmh.global.auth.jwt.service.JwtProvider;
import sopt.org.hmh.global.auth.jwt.service.JwtValidator;
import sopt.org.hmh.global.auth.security.JwtAuthenticationEntryPoint;
import sopt.org.hmh.global.auth.security.JwtAuthenticationFilter;
import sopt.org.hmh.global.auth.security.exception.ExceptionHandlerFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtValidator jwtValidator;
    private final JwtProvider jwtProvider;
    private final JwtAuthenticationEntryPoint customJwtAuthenticationEntryPoint;
    private final ErrorSlackMessageBuilder errorSlackMessageBuilder;

    private static final String[] AUTH_WHITELIST = {
            // Global
            "/health",
            "/error",

            // Swagger
            "/swagger-ui/**",
            "/swagger-resources/**",
            "/api-docs/**",
            "/v3/api-docs/**",

            // Authentication
            "/api/v1/user/login",
            "/api/v1/admin/login",
            "/api/v1/user/reissue",
            "/api/v1/user/signup",
            "/api/v2/user/signup",
            "/api/v1/user/social/token/kakao",
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .formLogin(AbstractHttpConfigurer::disable) // Form Login 사용 X
                .httpBasic(AbstractHttpConfigurer::disable) // HTTP Basic 사용 X
                .csrf(AbstractHttpConfigurer::disable) // 쿠키 기반이 아닌 JWT 기반이므로 사용 X
                .sessionManagement(sessionManagementConfigurer ->
                        sessionManagementConfigurer.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS)) // Spring Security 세션 정책 : 세션 생성 및 사용하지 않음
                .exceptionHandling(exceptionHandlingConfigurer ->
                        exceptionHandlingConfigurer.authenticationEntryPoint(
                                customJwtAuthenticationEntryPoint)) // 인증되지 않은 사용자의 요청에 대한 응답을 처리
                .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry -> // HTTP 요청에 대한 권한 설정
                        authorizationManagerRequestMatcherRegistry
                                .anyRequest().authenticated())
                .addFilterBefore(new JwtAuthenticationFilter(jwtValidator, jwtProvider), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new ExceptionHandlerFilter(errorSlackMessageBuilder), JwtAuthenticationFilter.class)
                .build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().requestMatchers(AUTH_WHITELIST);
    }
}
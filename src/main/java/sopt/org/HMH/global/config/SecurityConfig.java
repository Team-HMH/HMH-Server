package sopt.org.HMH.global.config;

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
import sopt.org.HMH.global.auth.security.CustomJwtAuthenticationEntryPoint;
import sopt.org.HMH.global.auth.security.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    // JWT 인증을 처리하는 필터
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    // JWT에 대한 인증 예외 처리를 담당하는 인증 진입점
    private final CustomJwtAuthenticationEntryPoint customJwtAuthenticationEntryPoint;

    private static final String[] AUTH_WHITELIST = {
            "/", "/error", "/health",

            // Swagger
            "/swagger-ui/**",
            "/swagger-resources/**",
            "/api-docs/**",
            "/v3/api-docs/**",

            // Authentication
            "/api/v1/user/login",
            "/api/v1/user/reissue",
            "/api/v1/user/signup",
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
                                .requestMatchers(AUTH_WHITELIST).permitAll() // AUTH_WHITELIST에 속하는 URL 패턴을 모든 사용자에게 허용
                                .anyRequest().authenticated())
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers( "/","/swagger-ui/**", "/v3/api-docs/**");
    }
}
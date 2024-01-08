package sopt.org.HMH.global.auth.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import sopt.org.HMH.global.auth.jwt.exception.JwtError;
import sopt.org.HMH.global.common.response.ApiResponse;

/**
 * 인증되지 않은 사용자의 요청에 대한 응답을 정의할 때 사용되는 클래스
 */
@Component
public class CustomJwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    // JSON 형식의 응답을 생성하기 위한 인스턴스
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        setResponse(response);
    }

    private void setResponse(HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 응답의 상태코드를 401으로 정의

        // 응답 상태코드 401으로 정의
        response.getWriter().println(objectMapper.writeValueAsString(ApiResponse.error(JwtError.INVALID_ACCESS_TOKEN)));
    }
}

package sopt.org.HMH.global.auth.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import sopt.org.HMH.global.auth.jwt.JwtConstants;
import sopt.org.HMH.global.auth.jwt.exception.JwtError;
import sopt.org.HMH.global.common.response.ApiResponse;

@Slf4j
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        setResponse(response);
    }

    private void setResponse(HttpServletResponse response) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(JwtConstants.CHARACTER_ENCODING);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().println(objectMapper.writeValueAsString(ApiResponse.error(JwtError.INVALID_ACCESS_TOKEN)));
    }
}
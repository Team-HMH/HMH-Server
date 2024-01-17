package sopt.org.HMH.global.auth.security.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;
import sopt.org.HMH.global.auth.jwt.JwtConstants;
import sopt.org.HMH.global.auth.jwt.exception.JwtError;
import sopt.org.HMH.global.auth.jwt.exception.JwtException;
import sopt.org.HMH.global.common.exception.base.ErrorBase;
import sopt.org.HMH.global.common.response.ApiResponse;

@Slf4j
public class ExceptionHandlerFilter extends OncePerRequestFilter {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (JwtException error) {
            handleUnauthorizedException(response, error.getError());
        } catch (Exception error) {
            handleException(response, error);
        }
    }

    private void handleUnauthorizedException(HttpServletResponse response, ErrorBase errorBase) throws IOException {
        JwtError jwtError = (JwtError) errorBase;
        HttpStatus httpStatus = jwtError.getHttpStatus();
        setResponse(response, httpStatus, jwtError);
    }

    private void handleException(HttpServletResponse response, Exception e) throws IOException {
        log.error(">>> Exception Handler Filter : ", e);
        setResponse(response, HttpStatus.INTERNAL_SERVER_ERROR, JwtError.INTERNAL_SERVER_ERROR);
    }

    private void setResponse(HttpServletResponse response, HttpStatus httpStatus, ErrorBase errorMessage) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(JwtConstants.CHARACTER_ENCODING);
        response.setStatus(httpStatus.value());
        PrintWriter writer = response.getWriter();
        writer.write(objectMapper.writeValueAsString(ApiResponse.error(errorMessage)));
    }
}

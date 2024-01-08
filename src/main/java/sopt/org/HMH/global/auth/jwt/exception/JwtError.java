package sopt.org.HMH.global.auth.jwt.exception;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import sopt.org.HMH.global.common.exception.base.ErrorBase;

@AllArgsConstructor
public enum JwtError implements ErrorBase {

    // 400 BAD REQUEST
    EMPTY_PRINCIPLE_EXCEPTION(HttpStatus.BAD_REQUEST, "Principle 객체가 없습니다."),
    INVALID_TOKEN_HEADER(HttpStatus.BAD_REQUEST, "토큰 헤더 값의 형식이 잘못되었습니다."),

    // 401 UNAUTHORIZED
    INVALID_SOCIAL_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 소셜 엑세스 토큰입니다."),
    INVALID_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 액세스 토큰입니다. 액세스 토큰을 재발급 받아주세요."),
    INVALID_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 리프레시 토큰입니다. 다시 로그인 해주세요."),

    // 403 FORBIDDEN

    // 404 NOT FOUND
    NOT_FOUND_REFRESH_TOKEN_ERROR(HttpStatus.NOT_FOUND, "존재하지 않는 리프레시 토큰입니다.")

    ;

    private final HttpStatus status;
    private final String errorMessage;
    @Override
    public int getHttpStatusCode() {
        return this.status.value();
    }

    @Override
    public HttpStatus getHttpStatus() {
        return this.status;
    }

    @Override
    public String getErrorMessage() {
        return this.errorMessage;
    }
}
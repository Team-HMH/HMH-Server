package sopt.org.hmh.global.auth.jwt.exception;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import sopt.org.hmh.global.common.exception.base.ErrorBase;

@AllArgsConstructor
public enum JwtError implements ErrorBase {

    // 400 BAD REQUEST
    EMPTY_PRINCIPLE_EXCEPTION(HttpStatus.BAD_REQUEST, "Principle 객체가 없습니다."),
    INVALID_TOKEN_HEADER(HttpStatus.BAD_REQUEST, "토큰 헤더 값의 형식이 잘못되었습니다."),

    // 401 UNAUTHORIZED
    INVALID_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 액세스 토큰입니다. 액세스 토큰을 재발급 받아주세요."),
    EXPIRED_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED,  "액세스 토큰이 만료되었습니다. 액세스 토큰을 재발급 받아주세요."),

    INVALID_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 리프레시 토큰입니다. 다시 로그인 해주세요."),
    EXPIRED_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED,  "리프레시 토큰이 만료되었습니다. 다시 로그인해 주세요."),

    INVALID_SOCIAL_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 소셜 엑세스 토큰입니다."),
    INVALID_SOCIAL_ACCESS_TOKEN_FORMAT(HttpStatus.UNAUTHORIZED, "유효하지 않은 소셜 엑세스 토큰 형식입니다."),

    INVALID_IDENTITY_TOKEN(HttpStatus.UNAUTHORIZED, "애플 아이덴티티 토큰의 형식이 올바르지 않습니다."),
    EXPIRED_IDENTITY_TOKEN(HttpStatus.UNAUTHORIZED, "만료된 애플 아이덴티티 토큰입니다."),
    INVALID_IDENTITY_TOKEN_CLAIMS(HttpStatus.UNAUTHORIZED, "유효하지 않은 애플 아이덴티티 토큰 클레임입니다."),
    UNABLE_TO_CREATE_APPLE_PUBLIC_KEY(HttpStatus.UNAUTHORIZED, "애플 로그인 중 퍼블릭 키 생성에 문제가 발생했습니다."),

    INVALID_ADMIN_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 관리자 액세스 토큰입니다."),

    // 404 NOT FOUND
    NOT_FOUND_REFRESH_TOKEN_ERROR(HttpStatus.NOT_FOUND, "존재하지 않는 리프레시 토큰입니다."),

    // 500 INTERNAL ERROR
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류입니다.");

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
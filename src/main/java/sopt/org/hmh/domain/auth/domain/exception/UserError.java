package sopt.org.hmh.domain.auth.domain.exception;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import sopt.org.hmh.global.common.exception.base.ErrorBase;

@AllArgsConstructor
public enum UserError implements ErrorBase {

    // 400 BAD REQUEST
    INVALID_USER(HttpStatus.BAD_REQUEST, "Principle 객체가 없습니다."),
    DUPLICATE_USER(HttpStatus.BAD_REQUEST, "이미 회원가입된 유저입니다."),

    // 403 FORBIDDEN
    NOT_SIGNUP_USER(HttpStatus.FORBIDDEN, "회원가입된 유저가 아닙니다. 회원가입을 진행해주세요."),

    // 404 NOT FOUND
    NOT_FOUND_USER(HttpStatus.NOT_FOUND, "유저를 찾을 수 없습니다.");

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
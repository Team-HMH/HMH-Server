package sopt.org.HMH.domain.user.domain.exception;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import sopt.org.HMH.global.common.exception.base.ErrorBase;

@AllArgsConstructor
public enum UserError implements ErrorBase {

    // 400 BAD REQUEST
    INVALID_USER(HttpStatus.BAD_REQUEST, "Principle 객체가 없습니다."),

    // 401 UNAUTHORIZED

    // 403 FORBIDDEN

    // 404 NOT FOUND
    NOT_FOUND_USER(HttpStatus.NOT_FOUND, "User를 찾을 수 없습니다."),
    NOT_SIGNUP_USER(HttpStatus.NOT_FOUND, "회원가입된 유저가 아닙니다. 회원가입을 진행해주세요.");

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
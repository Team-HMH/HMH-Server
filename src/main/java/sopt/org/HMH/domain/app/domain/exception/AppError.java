package sopt.org.HMH.domain.app.domain.exception;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import sopt.org.HMH.global.common.exception.base.ErrorBase;

@AllArgsConstructor
public enum AppError implements ErrorBase {

    APP_NOT_FOUND(HttpStatus.NOT_FOUND, "앱을 찾을 수 없습니다."),
    APP_EXIST_ALREADY(HttpStatus.CONFLICT, "이미 추가된 앱입니다."),
    INVALID_APP_CODE_NULL(HttpStatus.BAD_REQUEST, "앱 코드 값이 비어있습니다"),
    INVALID_TIME_RANGE(HttpStatus.BAD_REQUEST, "앱 시간의 범위가 유효한지 확인해주세요"),
    INVALID_TIME_NULL(HttpStatus.BAD_REQUEST, "앱 시간을 입력해주세요"),
    ;

    private final HttpStatus status;
    private final String errorMessage;

    @Override
    public int getHttpStatusCode() {
        return status.value();
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
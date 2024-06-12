package sopt.org.hmh.domain.app.domain.exception;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import sopt.org.hmh.global.common.exception.base.ErrorBase;

@AllArgsConstructor
public enum AppError implements ErrorBase {

    APP_NOT_FOUND(HttpStatus.NOT_FOUND, "앱을 찾을 수 없습니다."),
    APP_EXIST_ALREADY(HttpStatus.CONFLICT, "이미 추가된 앱입니다."),
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
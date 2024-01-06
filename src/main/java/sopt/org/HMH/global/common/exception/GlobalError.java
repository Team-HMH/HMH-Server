package sopt.org.HMH.global.common.exception;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import sopt.org.HMH.global.common.exception.base.ErrorBase;

@AllArgsConstructor
public enum GlobalError implements ErrorBase {
    CHALLENGE_NOT_FOUND(HttpStatus.NOT_FOUND, "Can not found challenge."),
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
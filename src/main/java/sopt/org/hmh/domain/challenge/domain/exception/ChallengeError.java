package sopt.org.hmh.domain.challenge.domain.exception;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import sopt.org.hmh.global.common.exception.base.ErrorBase;

@AllArgsConstructor
public enum ChallengeError implements ErrorBase {

    CHALLENGE_NOT_FOUND(HttpStatus.NOT_FOUND, "챌린지를 찾을 수 없습니다."),
    INVALID_PERIOD_NUMERIC(HttpStatus.BAD_REQUEST, "유효한 챌린지 기간이 아닙니다."),

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
package sopt.org.hmh.domain.dailychallenge.domain.exception;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import sopt.org.hmh.global.common.exception.base.ErrorBase;

@AllArgsConstructor
public enum DailyChallengeError implements ErrorBase {

    DAILY_CHALLENGE_NOT_FOUND(HttpStatus.NOT_FOUND, "일별 챌린지를 찾을 수 없습니다."),
    DAILY_CHALLENGE_YESTERDAY_NOT_FOUND(HttpStatus.NOT_FOUND, "어제의 일별 챌린지를 찾을 수 없습니다."),
    DAILY_CHALLENGE_ALREADY_HANDLED(HttpStatus.BAD_REQUEST, "이미 처리된 일별 챌린지입니다.")
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